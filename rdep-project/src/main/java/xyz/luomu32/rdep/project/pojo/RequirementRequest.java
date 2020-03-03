package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequirementRequest {

    private String title;

    private Long projectId;

    private Long moduleId;

    private String content;

    private LocalDate deadline;

    private Long ownerId;

    private String ownerName;

    private List<Long> collaboratorIds;

    private Integer level;

    private Integer schedule;

    private LocalDate startDate;

    private String status;

}
