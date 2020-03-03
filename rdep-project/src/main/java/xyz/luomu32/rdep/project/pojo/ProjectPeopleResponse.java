package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.ProjectPeople;

@Data
public class ProjectPeopleResponse {

    private Long id;

    private Long userId;

    private String userName;

    private String role;

    public static ProjectPeopleResponse from(ProjectPeople projectPeople) {
        ProjectPeopleResponse response = new ProjectPeopleResponse();
        response.setId(projectPeople.getId());
        response.setRole(projectPeople.getRole().name());
        response.setUserId(projectPeople.getPeopleId());
        response.setUserName(projectPeople.getPeopleName());
        return response;
    }
}
