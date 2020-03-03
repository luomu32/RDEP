package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import org.springframework.util.StringUtils;

public class SpringCloudConfigPathDeterminer implements PathDeterminer {

    private String profile;
    private String moduleName;

    public SpringCloudConfigPathDeterminer(String profile, String moduleName) {
        this.profile = profile;
        this.moduleName = moduleName;
    }

    @Override
    public String getPath() {
        return "/" + moduleName + "-" + (StringUtils.isEmpty(this.profile) ? "all" : this.profile);
    }
}
