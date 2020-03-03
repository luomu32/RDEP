package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class IssueCommentRequest {

    private Long issueId;

    private String content;

    private Long creatorId;
}
