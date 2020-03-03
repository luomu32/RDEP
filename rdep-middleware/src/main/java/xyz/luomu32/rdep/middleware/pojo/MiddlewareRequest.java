package xyz.luomu32.rdep.middleware.pojo;

import lombok.Data;

@Data
public class MiddlewareRequest {

    private String name;

    private String version;

    private Long category;
}
