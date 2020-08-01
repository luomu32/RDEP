package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class ConfigCenterConfigRequest {

    private Long projectId;

    private Long moduleId;

    private String type;

    private String dataSourceType;

    private String url;

    private String prefix;
}
