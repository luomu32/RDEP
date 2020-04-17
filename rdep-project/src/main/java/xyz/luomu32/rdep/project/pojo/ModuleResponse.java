package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.Module;

@Data
public class ModuleResponse {
    private Long id;

    private String name;

    private String serviceId;

    private String scmUrl;

    private Long principalId;

    private String principalName;

    private String language;

    private String frameworks;

    private String info;

    public static ModuleResponse from(Module module) {
        ModuleResponse moduleResponse = new ModuleResponse();
        moduleResponse.setId(module.getId());
        moduleResponse.setName(module.getName());
        moduleResponse.setScmUrl(module.getScmUrl());
        moduleResponse.setServiceId(module.getServiceId());
        moduleResponse.setPrincipalId(module.getPrincipalId());
        moduleResponse.setPrincipalName(module.getPrincipalName());
        moduleResponse.setLanguage(module.getLanguage());
        moduleResponse.setFrameworks(module.getFrameworks());
        moduleResponse.setInfo(module.getInfo());
        return moduleResponse;
    }

}
