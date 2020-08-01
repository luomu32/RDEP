package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.model.Requirement;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RequirementResponse {

    private Long id;

    private String title;

    private String content;

    private Long owenId;

    private String owenName;

    private Integer level;

    private String status;

    private LocalDate deadline;

    private LocalDateTime createDatetime;

    private Long createBy;

    private LocalDateTime lastUpdateDatetime;

    private Long lastUpdateBy;

    public static RequirementResponse from(Requirement requirement) {
        RequirementResponse response = new RequirementResponse();
        response.setId(requirement.getId());
        response.setTitle(requirement.getTitle());
        response.setOwenId(requirement.getOwenId());
        response.setOwenName(requirement.getOwenName());
        response.setContent(requirement.getContent());
        response.setLevel(requirement.getLevel());
        response.setStatus(requirement.getStatus().getName());
        response.setDeadline(requirement.getDeadline());
        response.setCreateBy(requirement.getCreateBy());
        response.setCreateDatetime(requirement.getCreateDatetime());
        response.setLastUpdateBy(requirement.getLastUpdateBy());
        response.setLastUpdateDatetime(requirement.getLastUpdateDatetime());
        return response;
    }
}
