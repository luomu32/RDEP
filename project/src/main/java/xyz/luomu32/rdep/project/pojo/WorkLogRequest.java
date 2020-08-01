package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WorkLogRequest {

    @NotBlank(message = "work.log.title.not.blank")
    private String title;

    @NotBlank(message = "work.log.content.not.blank")
    private String content;
}
