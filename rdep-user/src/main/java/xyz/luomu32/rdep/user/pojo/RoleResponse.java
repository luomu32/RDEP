package xyz.luomu32.rdep.user.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleResponse {
    private Long id;

    private String name;

    private String remark;

    private LocalDateTime createDatetime;

    private Long createBy;

    private Long lastUpdateBy;

    private LocalDateTime lastUpdateDatetime;

    private Integer userCount;
}
