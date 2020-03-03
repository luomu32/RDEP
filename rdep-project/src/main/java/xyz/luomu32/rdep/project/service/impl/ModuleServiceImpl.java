package xyz.luomu32.rdep.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.apache.curator.framework.CuratorFramework;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.entity.Module;
import xyz.luomu32.rdep.project.entity.ModuleDubboAdminConfig;
import xyz.luomu32.rdep.project.entity.Project;
import xyz.luomu32.rdep.project.pojo.*;
import xyz.luomu32.rdep.project.repo.ModuleDubboAdminConfigRepo;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.repo.ProjectRepo;
import xyz.luomu32.rdep.project.schedule.ModuleAnalyzer;
import xyz.luomu32.rdep.project.service.ModuleService;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ModuleServiceImpl implements ModuleService {

    private static final String MODULE_SOURCE_DIR;

    @Autowired
    private ModuleRepo moduleRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ModuleDubboAdminConfigRepo moduleDubboAdminConfigRepo;
    @Autowired
    private ZookeeperClientManager zookeeperClientManager;
    private ModuleAnalyzer moduleAnalyzer;


    static {
        MODULE_SOURCE_DIR = System.getProperty("user.home") + "/rdep workdir/";
    }

    @Override
    public void create(ModuleRequest moduleRequest) {
        moduleRepo.save(Module.builder()
                .projectId(moduleRequest.getProjectId())
                .label(moduleRequest.getLabel())
                .configCenterUrl(moduleRequest.getConfigCenterUrl())
                .name(moduleRequest.getName())
                .scmUrl(moduleRequest.getScmUrl())
                .principalId(moduleRequest.getPrincipalId())
                .principalName(moduleRequest.getPrincipalName())
                .build());

    }

    @Override
    public void analyze(Long projectId, Long moduleId) {
        Module module = moduleRepo.findById(moduleId)
                .orElseThrow(() -> new ServiceException("module.not.found"));

        String url = module.getScmUrl();
        if (url.startsWith("http")) {
            try {
                File dir = new File(MODULE_SOURCE_DIR + module.getName());
                Git.cloneRepository()
                        .setURI(url)
                        .setDirectory(dir)
                        .call();

                //递归读取所有文件？分析用了什么语言
                //用了什么框架，比如用了Spring Boot，并加了Actuator依赖，并且配置中启用了，那么可以添加一个选项卡页面可以查看监控
                moduleAnalyzer.analyze(dir, module);
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public ModuleResponse fetch(Long projectId, Long moduleId) {
        Module module = moduleRepo.findById(moduleId).orElse(new Module());
        ModuleResponse moduleResponse = new ModuleResponse();
        moduleResponse.setId(module.getId());
        moduleResponse.setName(module.getName());
        moduleResponse.setScmUrl(module.getScmUrl());
        moduleResponse.setDesc(module.getDesc());
        moduleResponse.setPrincipalId(module.getPrincipalId());
        moduleResponse.setPrincipalName(module.getPrincipalName());
        moduleResponse.setLanguage(module.getLanguage());
        moduleResponse.setFrameworks(module.getFrameworks());
        moduleResponse.setInfo(module.getInfo());
        return moduleResponse;
    }

    @Override
    public List<ModuleResponse> fetch(Long projectId) {
        return moduleRepo.findByProjectId(projectId).stream().map(module -> {
            ModuleResponse moduleResponse = new ModuleResponse();
            moduleResponse.setId(module.getId());
            moduleResponse.setName(module.getName());
            moduleResponse.setScmUrl(module.getScmUrl());
            moduleResponse.setDesc(module.getDesc());
            moduleResponse.setPrincipalId(module.getPrincipalId());
            moduleResponse.setPrincipalName(module.getPrincipalName());
            moduleResponse.setLanguage(module.getLanguage());
            moduleResponse.setFrameworks(module.getFrameworks());
            return moduleResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ModuleDubboServiceProviderResponse> fetchDubboServiceProvider(Long projectId, Long moduleId, String serviceId) {
        ModuleDubboAdminConfig config = moduleDubboAdminConfigRepo.findByModuleId(moduleId);
        if (null == config) {
            throw new ServiceException("module.dubbo.admin.config.not.set");
        }

        CuratorFramework client = zookeeperClientManager.fetchClient(config.getRegisterAddress());
        try {
            List<String> serviceNames = client.getChildren().forPath("/dubbo/" + serviceId + "/providers");
            return serviceNames.stream().map(url -> {
                ModuleDubboServiceProviderResponse response = new ModuleDubboServiceProviderResponse();
                response.setUrl(url);

                Map<String, String> info = parseDubboUrl(url);

                response.setProtocol(info.get("protocol"));
                response.setHost(info.get("host"));
                response.setPort(info.get("port"));

                response.setGroup(info.get("group"));
                response.setVersion(info.get("version"));
                response.setApplicationName(info.get("application"));
                response.setOwner(info.get("owner"));
                response.setPid(info.get("pid"));
                response.setRetries(info.get("retries"));
                response.setSerialization(info.get("serialization"));
                response.setGeneric(info.get("generic"));
                if (!info.containsKey("accepts")) {
                    response.setAccepts(info.get("default.accepts"));
                } else {
                    response.setAccepts(info.get("accepts"));
                }
                if (!info.containsKey("cluster")) {
                    response.setCluster(info.get("default.cluster"));
                } else {
                    response.setCluster(info.get("cluster"));
                }

                response.setLoadBalance(info.get("loadbalance"));

                return response;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ModuleDubboServiceConsumerResponse> fetchDubboServiceConsumer(Long projectId, Long moduleId, String serviceId) {
        ModuleDubboAdminConfig config = moduleDubboAdminConfigRepo.findByModuleId(moduleId);
        if (null == config) {
            throw new ServiceException("module.dubbo.admin.config.not.set");
        }

        CuratorFramework client = zookeeperClientManager.fetchClient(config.getRegisterAddress());

        try {
            List<String> serviceNames = client.getChildren().forPath("/dubbo/" + serviceId + "/consumers");
            return serviceNames.stream().map(url -> {
                ModuleDubboServiceConsumerResponse response = new ModuleDubboServiceConsumerResponse();
                response.setUrl(url);

                Map<String, String> info = parseDubboUrl(url);

                response.setProtocol(info.get("protocol"));
                response.setHost(info.get("host"));
                response.setPort(info.get("port"));

                response.setGroup(info.get("group"));
                response.setVersion(info.get("version"));
                response.setApplicationName(info.get("application"));
                response.setOwner(info.get("owner"));
                response.setPid(info.get("pid"));
                if (!info.containsKey("retries")) {
                    response.setRetries(info.get("default.retries"));
                } else {
                    response.setRetries(info.get("retries"));
                }
                response.setSerialization(info.get("serialization"));
                response.setGeneric(info.get("generic"));
                if (!info.containsKey("cluster")) {
                    response.setCluster(info.get("default.cluster"));
                } else {
                    response.setCluster(info.get("cluster"));
                }
                if (!info.containsKey("mock")) {
                    response.setMock(info.get("default.mock"));
                } else {
                    response.setMock(info.get("mock"));
                }

                return response;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private Map<String, String> parseDubboUrl(String url) {
        Map<String, String> info = new HashMap<>();
        try {
            String rawUrl = URLDecoder.decode(url, "utf-8");
            int paramsPos = rawUrl.indexOf("?");
            for (String param : rawUrl.substring(paramsPos + 1).split("&")) {
                String[] keyAndValue = param.split("=");
                info.put(keyAndValue[0], keyAndValue[1]);
            }

            String path = rawUrl.substring(0, paramsPos);
            path = path.substring(0, path.lastIndexOf("/"));
            String[] hostAndPort = path.split(":");

            info.put("protocol", hostAndPort[0]);
            info.put("host", hostAndPort[1].substring(2));
            if (hostAndPort.length == 3)
                info.put("port", hostAndPort[2]);
        } catch (UnsupportedEncodingException e) {

        }
        return info;
    }

    @Override
    public List<ModuleDubboServiceResponse> fetchDubboServices(Long projectId, Long moduleId) {
        ModuleDubboAdminConfig config = moduleDubboAdminConfigRepo.findByModuleId(moduleId);
        if (null == config) {
            throw new ServiceException("module.dubbo.admin.config.not.set");
        }

        CuratorFramework client = zookeeperClientManager.fetchClient(config.getRegisterAddress());

        try {
            List<String> serviceNames = client.getChildren().forPath("/dubbo");
            return serviceNames.stream().map(name -> {
                ModuleDubboServiceResponse response = new ModuleDubboServiceResponse();
                response.setName(name);
                return response;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    @Deprecated
    public void build(Long projectId, Long moduleId) {
        Module module = moduleRepo.findById(moduleId)
                .orElseThrow(() -> new ServiceException("module.not.found"));
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ServiceException("project.not.found"));

        File dir = new File(MODULE_SOURCE_DIR + project.getName() + "/" + module.getName());
        try {

            //TODO 如果存在，执行git -pull命令获取更新，不需要每次git clone。
            if (dir.exists()) {
                log.debug("dir[{}] is existed,delete it", dir.getAbsolutePath());
                FileUtils.deleteDirectory(dir);
            }

            Git.cloneRepository()
                    .setURI(module.getScmUrl())
                    .setDirectory(dir)
                    .call();

            //调用mvn命令进行打包
//            CommandLine cmdLine = CommandLine.parse(dir.getAbsolutePath() + "/mvn clean package");
//            String path = dir.getAbsolutePath().replace(" ", "\\ ");
//            System.out.println(path);
            CommandLine cmdLine = new CommandLine("mvn -f " + dir.getAbsolutePath() + "/pom.xml clean package");

            DefaultExecutor executor = new DefaultExecutor();

            log.debug("execute command:{}", cmdLine.toString());

            int exitValue = executor.execute(cmdLine);

            //调用docker命令构建镜像
            //将镜像push到私服
            //调用目标机器的监视器小程序，让其从私服下载指定镜像并运行
        } catch (GitAPIException e) {
            throw new ServiceException("module.git.clone.failed");
        } catch (ExecuteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
