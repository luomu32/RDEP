package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectRequest {
    @NotBlank(message = "project.name.not.blank")
    private String name;

    private String scmURL;

    private String principal;
}
