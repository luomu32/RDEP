package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class ConfigRequest {
    private String name;
    private String value;
    private String profile;

    private Long projectId;
    private Long moduleId;
}
