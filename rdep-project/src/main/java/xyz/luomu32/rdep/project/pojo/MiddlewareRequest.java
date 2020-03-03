package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class MiddlewareRequest {

    private Long projectId;

    private Long moduleId;

    private Long id;

    private String name;

    private String version;

    private String url;
}
