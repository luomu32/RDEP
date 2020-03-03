package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.IssueComment;

import java.time.LocalDateTime;

@Data
public class IssueCommentResponse {

    private Long id;

    private String content;

    private Long creatorId;

    private String creatorName;

    private LocalDateTime createdDatetime;

    public static IssueCommentResponse from(IssueComment comment) {
        IssueCommentResponse response = new IssueCommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setCreatedDatetime(comment.getCreatedDatetime());
        response.setCreatorId(comment.getCreatorId());
        response.setCreatorName(comment.getCreatorName());
        return response;
    }

}
