package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.IssueAttachment;

@Data
public class IssueAttachmentResponse {
    private Long id;

    private String filename;

    private String url;

    private Integer height;

    private Integer width;

//    private byte[] content;

    public static IssueAttachmentResponse from(IssueAttachment issueAttachment) {
        IssueAttachmentResponse response = new IssueAttachmentResponse();
        response.setId(issueAttachment.getId());
        response.setFilename(issueAttachment.getFilename());
        response.setUrl(issueAttachment.getUrl());
        response.setWidth(issueAttachment.getWidth());
        response.setHeight(issueAttachment.getHeight());
        return response;
    }
}
