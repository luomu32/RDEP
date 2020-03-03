package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.entity.Module;
import xyz.luomu32.rdep.project.entity.ModuleConfigCenter;
import xyz.luomu32.rdep.project.entity.ModuleConfigCenterType;
import xyz.luomu32.rdep.project.entity.Project;

public class ClientDeterminer {
    public static Client getClient(ModuleConfigCenter configCenter, Project project, Module module, String profile) {
        ModuleConfigCenterType type = configCenter.getType();

        if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_ZOOKEEPER)) {
            return ZookeeperClient
                    .getInstance(configCenter.getUrl(), new SpringCloudPathDeterminer(profile, project.getName(), module.getName(), configCenter.getPrefix()));
        } else if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONSUL)) {
            return new ConsulClient(configCenter.getUrl(), new SpringCloudPathDeterminer(profile, project.getName(), module.getName(), configCenter.getPrefix()));
        } else if (type.equals(ModuleConfigCenterType.SPRING_CLOUD_CONFIG)) {
            return new SpringCloudConfigClient(configCenter.getUrl(), new SpringCloudConfigPathDeterminer(profile, module.getName()));
        } else {
            throw new ServiceException("config.center.type.not.support");
        }
    }
}
