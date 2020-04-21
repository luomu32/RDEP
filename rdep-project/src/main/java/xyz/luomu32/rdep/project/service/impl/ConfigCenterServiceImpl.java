package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.Module;
import xyz.luomu32.rdep.project.model.ModuleConfigCenter;
import xyz.luomu32.rdep.project.model.ModuleConfigCenterType;
import xyz.luomu32.rdep.project.model.Project;
import xyz.luomu32.rdep.project.pojo.ConfigCenterConfigRequest;
import xyz.luomu32.rdep.project.pojo.ConfigCenterConfigResponse;
import xyz.luomu32.rdep.project.pojo.ConfigRequest;
import xyz.luomu32.rdep.project.pojo.ConfigResponse;
import xyz.luomu32.rdep.project.repo.ModuleConfigCenterRepo;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.repo.ProjectRepo;
import xyz.luomu32.rdep.project.service.ConfigCenterService;
import xyz.luomu32.rdep.project.service.impl.module.configCenter.Client;
import xyz.luomu32.rdep.project.service.impl.module.configCenter.ClientDeterminer;

import java.util.List;
import java.util.stream.Collectors;

//TODO 如何确定模块配置中心是否启用？手动？依据模块的中间件是否使用了配置中心，还是依据代码分析，是否依赖了相关的依赖。
@Service
public class ConfigCenterServiceImpl implements ConfigCenterService {

    @Autowired
    private ModuleRepo moduleRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ModuleConfigCenterRepo moduleConfigCenterRepo;

    @Autowired
    private ZookeeperClientManager zookeeperClientManager;

    @Override
    public List<ConfigResponse> fetch(Long projectId, Long moduleId, String profile) {

        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        ModuleConfigCenter configCenter = moduleConfigCenterRepo.findByModuleId(moduleId);
        if (null == configCenter) {
            throw new ServiceException("module.not.enable.config.center");
        }
//        String path;
//        ModuleConfigCenterType type = configCenter.getType();
//        if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_ZOOKEEPER)
//                || type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONSUL)) {
//            SpringCloudPathDeterminer pathDeterminer = new SpringCloudPathDeterminer(profile);
//            path = pathDeterminer.getPath(project, module, configCenter);
//        } else if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONFIG)) {
//            path = new SpringCloudConfigPathDeterminer(profile).getPath(project, module, configCenter);
//        } else {
//            throw new ServiceException("config.center.type.not.support");
//        }

        Client client = ClientDeterminer.getClient(configCenter, project, module, profile);

        return client.list()
                .entrySet()
                .parallelStream()
                .map((entry) -> {
                    ConfigResponse configResponse = new ConfigResponse();
                    configResponse.setName(entry.getKey());
                    configResponse.setValue(entry.getValue());
                    configResponse.setProfile(profile);
                    return configResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public void createOrUpdate(ConfigRequest configRequest) {
        Project project = projectRepo.findById(configRequest.getProjectId()).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(configRequest.getModuleId()).orElseThrow(() -> new ServiceException("module.not.found"));

        ModuleConfigCenter configCenter = moduleConfigCenterRepo.findByModuleId(configRequest.getModuleId());
        if (null == configCenter) {
            throw new ServiceException("module.not.enable.config.center");
        }

//        String path;
//        ModuleConfigCenterType type = configCenter.getType();
//        if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_ZOOKEEPER)
//                || type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONSUL) ||
//                type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONFIG)) {
//            SpringCloudPathDeterminer pathDeterminer = new SpringCloudPathDeterminer(configRequest.getProfile());
//            path = pathDeterminer.getPath(project, module, configCenter);
//        } else {
//            throw new ServiceException("config.center.type.not.support");
//        }
        Client client = ClientDeterminer.getClient(configCenter, project, module, configRequest.getProfile());
        client.setValue(configRequest.getName(), configRequest.getValue());
    }


    @Override
    public void delete(ConfigRequest configRequest) {
        Project project = projectRepo.findById(configRequest.getProjectId()).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(configRequest.getModuleId()).orElseThrow(() -> new ServiceException("module.not.found"));

        ModuleConfigCenter configCenter = moduleConfigCenterRepo.findByModuleId(configRequest.getModuleId());
        if (null == configCenter) {
            throw new ServiceException("module.not.enable.config.center");
        }

//        String path;
//        ModuleConfigCenterType type = configCenter.getType();
//        if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_ZOOKEEPER)
//                || type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONSUL) ||
//                type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONFIG)) {
//            SpringCloudPathDeterminer pathDeterminer = new SpringCloudPathDeterminer(configRequest.getProfile());
//            path = pathDeterminer.getPath(project, module, configCenter);
//        } else {
//            throw new ServiceException("config.center.type.not.support");
//        }
        Client client = ClientDeterminer.getClient(configCenter, project, module, configRequest.getProfile());
        client.delete(configRequest.getName());

    }

    @Override
    public void configConfigCenter(ConfigCenterConfigRequest request) {
        ModuleConfigCenter moduleConfigCenter = moduleConfigCenterRepo.findByModuleId(request.getModuleId());
        if (null == moduleConfigCenter) {
            moduleConfigCenter = new ModuleConfigCenter();
            moduleConfigCenter.setModuleId(request.getModuleId());
        }

        moduleConfigCenter.setUrl(request.getUrl());
        moduleConfigCenter.setPrefix(request.getPrefix());
        moduleConfigCenter.setType(ModuleConfigCenterType.valueOf(request.getType()));
        moduleConfigCenterRepo.save(moduleConfigCenter);
    }

    @Override
    public ConfigCenterConfigResponse fetchConfigCenterConfig(Long moduleId) {
        return ConfigCenterConfigResponse.from(moduleConfigCenterRepo.findByModuleId(moduleId));
    }
}
