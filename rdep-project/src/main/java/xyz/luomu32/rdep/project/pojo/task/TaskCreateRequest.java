package xyz.luomu32.rdep.project.pojo.task;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class TaskCreateRequest {

    private Long id;

    private Long chargerId;

    private String chargerName;

    @NotNull(message = "{task.project.id.null}")
    private Long projectId;

    private Long moduleId;

    private String state;

    private LocalDate start;

    private LocalDate end;

    @NotNull(message = "{task.title.null}")
    @Size(max = 50, message = "{task.title.size}")
    private String title;

    private Integer priority;
    private Integer progress;
}
