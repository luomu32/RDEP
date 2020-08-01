package xyz.luomu32.rdep.project.pojo;

import lombok.Data;

@Data
public class ModuleDubboServiceProviderResponse {
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

    /**
     * 可接受的连接数
     */
    private String accepts;

    /**
     * 集群模式
     */
    private String cluster;

    /**
     * 负载均衡
     */
    private String loadBalance;
}
