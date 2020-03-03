package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.IssueTag;

@Data
public class IssueTagResponse {

    private Long id;
    private String name;
    private String color;

    public static IssueTagResponse from(IssueTag issueTag) {
        IssueTagResponse response = new IssueTagResponse();
        response.setId(issueTag.getId());
        response.setName(issueTag.getName());
        response.setColor(issueTag.getColor());
        return response;
    }

}
