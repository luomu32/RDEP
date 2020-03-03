package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class ModuleRequest {
    private Long projectId;

    private String name;

    private String label;

    private String configCenterUrl;

    private String scmUrl;

    private Long principalId;

    private String principalName;
}
