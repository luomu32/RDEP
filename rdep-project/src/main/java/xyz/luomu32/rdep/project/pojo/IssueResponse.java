package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.Issue;
import xyz.luomu32.rdep.project.entity.IssueTag;
import xyz.luomu32.rdep.project.entity.IssueWithoutContent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class IssueResponse {

    private Long id;

    private String title;

    private String status;

    private List<IssueTagResponse> tags;

    private Integer level;

    private LocalDateTime createDatetime;

    private Long createBy;

    private String content;

    public static IssueResponse from(Issue issue, List<IssueTag> tags) {
        IssueResponse response = new IssueResponse();
        response.setId(issue.getId());
        response.setStatus(issue.getStatus().getName());
        response.setTitle(issue.getTitle());
        response.setCreateBy(issue.getCreateBy());
        response.setCreateDatetime(issue.getCreateDatetime());
        response.setLevel(issue.getLevel());
        response.setTags(tags.stream().map(IssueTagResponse::from).collect(Collectors.toList()));
        response.setContent(issue.getContent());
        return response;
    }
    public static IssueResponse from(IssueWithoutContent issue, List<IssueTag> tags) {
        IssueResponse response = new IssueResponse();
        response.setId(issue.getId());
        response.setStatus(issue.getStatus().getName());
        response.setTitle(issue.getTitle());
        response.setCreateBy(issue.getCreateBy());
        response.setCreateDatetime(issue.getCreateDatetime());
        response.setLevel(issue.getLevel());
        response.setTags(tags.stream().map(IssueTagResponse::from).collect(Collectors.toList()));
        return response;
    }
}
