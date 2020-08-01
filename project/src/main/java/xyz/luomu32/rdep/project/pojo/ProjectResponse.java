package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.model.Project;

import java.time.LocalDateTime;

@Data
public class ProjectResponse {
    private Long id;

    private String name;

    private Long ownerId;

    private String ownerName;

    private String status;

    private String scmUrl;

    private LocalDateTime createDatetime;

    private Long createBy;

    private LocalDateTime lastUpdateDatetime;

    private Long lastUpdateBy;

    public static ProjectResponse from(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setOwnerId(project.getOwnerId());
        response.setOwnerName(project.getOwnerName());
        response.setStatus(project.getStatus().getDesc());
        response.setScmUrl(project.getGitUrl());
        response.setCreateBy(project.getCreateBy());
        response.setCreateDatetime(project.getCreateDatetime());
        response.setLastUpdateBy(project.getLastUpdateBy());
        response.setLastUpdateDatetime(project.getLastUpdateDatetime());
        return response;
    }
}
