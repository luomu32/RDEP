package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ModuleRequest {
    private Long projectId;

    @NotNull(message = "{module.name.null}")
    @Size(max = 45,message = "{module.name.size.max}")
    private String name;

    private String serviceId;

    private String configCenterUrl;

    private String scmUrl;

    private Long principalId;

    private String principalName;
}
