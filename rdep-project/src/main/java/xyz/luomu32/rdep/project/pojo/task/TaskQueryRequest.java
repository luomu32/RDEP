package xyz.luomu32.rdep.project.pojo.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class TaskQueryRequest {

    private Long projectId;

    private Long moduleId;

    private Long chargerId;

    private String state;

    private LocalDate startStart;

    private LocalDate startEnd;

    private LocalDate endStart;

    private LocalDate endEnd;

    private String title;

    private Integer priority;

    private Integer progress;
}
