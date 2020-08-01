package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.model.ModuleConfigCenter;

@Data
public class ConfigCenterConfigResponse {
    private Long id;

    private Long moduleId;

    private String url;

    private String prefix;

    private String type;

    public static ConfigCenterConfigResponse from(ModuleConfigCenter moduleConfigCenter) {
        if (null == moduleConfigCenter) {
            return null;
        }

        ConfigCenterConfigResponse response = new ConfigCenterConfigResponse();
        response.setId(moduleConfigCenter.getId());
        response.setModuleId(moduleConfigCenter.getModuleId());
        response.setUrl(moduleConfigCenter.getUrl());
        response.setPrefix(moduleConfigCenter.getPrefix());
        response.setType(moduleConfigCenter.getType().name());
        return response;
    }

}
