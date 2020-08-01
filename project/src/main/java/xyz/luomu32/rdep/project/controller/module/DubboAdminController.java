package xyz.luomu32.rdep.project.controller.module;

import lombok.Getter;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.ModuleDubboAdminConfig;
import xyz.luomu32.rdep.project.pojo.ModuleDubboServiceConsumerResponse;
import xyz.luomu32.rdep.project.pojo.ModuleDubboServiceProviderResponse;
import xyz.luomu32.rdep.project.pojo.ModuleDubboServiceResponse;
import xyz.luomu32.rdep.project.pojo.dubbo.RuleCreateCmd;
import xyz.luomu32.rdep.project.repo.ModuleDubboAdminConfigRepo;
import xyz.luomu32.rdep.project.service.ModuleService;
import xyz.luomu32.rdep.project.service.impl.ZookeeperClientManager;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}/dubbo/admin")
public class DubboAdminController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ZookeeperClientManager zookeeperClientManager;
    @Autowired
    ModuleDubboAdminConfigRepo moduleDubboAdminConfigRepo;

    /**
     * 查询服务
     *
     * @param projectId
     * @param moduleId
     * @return
     */
    @GetMapping("services")
    public List<ModuleDubboServiceResponse> fetch(@PathVariable Long projectId,
                                                  @PathVariable Long moduleId) {
        return moduleService.fetchDubboServices(projectId, moduleId);
    }

    @GetMapping("services/{serviceId}/providers")
    public List<ModuleDubboServiceProviderResponse> fetchServiceProvider(@PathVariable Long projectId,
                                                                         @PathVariable Long moduleId,
                                                                         @PathVariable String serviceId) {
        return moduleService.fetchDubboServiceProvider(projectId, moduleId, serviceId);
    }

    @GetMapping("services/{serviceId}/consumers")
    public List<ModuleDubboServiceConsumerResponse> fetchServiceConsumer(@PathVariable Long projectId,
                                                                         @PathVariable Long moduleId,
                                                                         @PathVariable String serviceId) {
        return moduleService.fetchDubboServiceConsumer(projectId, moduleId, serviceId);
    }

    @PostMapping("services/rules")
    public void createRule(@RequestBody RuleCreateCmd createCmd) {
//        params.forEach((key, value) -> {
//            System.out.println("key:" + key + "=" + value);
//        });
        System.out.println(createCmd.getClient());
        createCmd.getConfigs().forEach(c -> {
            System.out.println("key:" + c.getKey() + ",value:" + c.getValue());
        });
    }

    @GetMapping("zk")
    public ZkNode zkTreeView(@PathVariable Long moduleId, @RequestParam(defaultValue = "/") String root) throws Exception {
        ModuleDubboAdminConfig config = moduleDubboAdminConfigRepo.findByModuleId(moduleId);
        if (null == config) {
            throw new ServiceException("module.dubbo.admin.config.not.set");
        }
        CuratorFramework client = zookeeperClientManager.fetchClient(config.getRegisterAddress());
        ZkNode rootNode = new ZkNode();
        String rootValue = new String(client.getData().forPath(root));
        rootNode.setValue(rootValue);
        rootNode.setPath(root);
        List<String> children = client.getChildren().forPath(root);

        if (root.equals("/")) {
            rootNode.setTitle(root);
            root = "";
        } else {
            int p = root.lastIndexOf('/');
            if (p == -1)
                rootNode.setTitle(root);
            else rootNode.setTitle(root.substring(p + 1));
        }

        if (!children.isEmpty()) {
            String finalRoot = root;
            children.forEach(c -> {
                ZkNode cNode = new ZkNode();
                cNode.setTitle(c);
                cNode.setPath(finalRoot + '/' + c);
                try {
                    String v = new String(client.getData().forPath(finalRoot + '/' + c));
                    cNode.setValue(v);
                    if (client.getChildren().forPath(finalRoot + '/' + c).isEmpty())
                        cNode.setLoading(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rootNode.getChildren().add(cNode);
            });
        }
        return rootNode;
    }

    @Getter
    @Setter
    public static class ZkNode {
        private String title;
        private String path;
        private String value;
        private Boolean loading = false;
        private List<ZkNode> children = new ArrayList<>();
    }
}
