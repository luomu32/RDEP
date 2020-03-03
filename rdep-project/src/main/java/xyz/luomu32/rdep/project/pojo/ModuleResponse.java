package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class ModuleResponse {
    private Long id;

    private String name;

    private String desc;

    private String scmUrl;

    private Long principalId;

    private String principalName;

    private String language;

    private String frameworks;

    private String info;
}
