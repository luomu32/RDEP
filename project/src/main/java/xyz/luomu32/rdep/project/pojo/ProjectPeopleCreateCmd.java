package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectPeopleCreateCmd {

    private Long projectId;

    @NotBlank(message = "project.people.role.not.blank")
    private String role;

    @NotEmpty(message = "project.people.not.empty")
    @Valid
    private List<People> people;

    @Data
    public static class People {
        @NotNull(message = "project.people.id.not.null")
        private Long id;
        @NotBlank(message = "project.people.name.not.blank")
        private String name;
    }
}
