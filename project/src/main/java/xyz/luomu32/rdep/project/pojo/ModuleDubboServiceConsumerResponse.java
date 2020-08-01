package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class ModuleDubboServiceConsumerResponse {
    private String url;

    private String protocol;

    private String host;

    private String port;

    private String version;

    private String group;

    private String applicationName;

    private String owner;

    private String pid;

    private String retries;

    private String serialization;

    private String generic;
    private String mock;
    private String cluster;
}
