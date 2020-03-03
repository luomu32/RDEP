package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import org.springframework.util.StringUtils;

public class SpringCloudPathDeterminer implements PathDeterminer {

    private String profile;
    private String projectName;
    private String moduleName;
    private String prefix;

    public SpringCloudPathDeterminer(String profile, String projectName, String moduleName, String prefix) {
        this.profile = profile;
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.prefix = prefix;
    }

    @Override
    public String getPath() {
        boolean hasProfile = null != profile && !profile.equalsIgnoreCase("all");

        String path;
        if (!StringUtils.isEmpty(prefix)) {
            path = prefix + "/";
        } else {
            path = "/";
        }
        path += projectName + "/" + moduleName;

        if (hasProfile) {
            path += "," + profile;
        }
        return path;
    }
}
