package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class IssueTagRequest {

    private Long moduleId;
    private String name;

    private String color;
}
