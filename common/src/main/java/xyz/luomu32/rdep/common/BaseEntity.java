package xyz.luomu32.rdep.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    private LocalDateTime createDateTime;

    private Long createBy;

    private LocalDateTime lastUpdateDateTime;

    private Long lastUpdateBy;
}
