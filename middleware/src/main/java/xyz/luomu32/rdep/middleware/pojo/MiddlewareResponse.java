package xyz.luomu32.rdep.middleware.pojo;

import lombok.Data;

@Data
public class MiddlewareResponse {
    private Long id;
    private String name;

    private String version;

    private Long categoryId;
}
