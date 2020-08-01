package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class IssueRequest {
    private String title;

    private Integer level;
    private String content;
    private String tags;

    private Long projectId;
    private Long moduleId;
}
