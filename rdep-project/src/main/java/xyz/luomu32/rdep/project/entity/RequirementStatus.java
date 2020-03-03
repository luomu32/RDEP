package xyz.luomu32.rdep.project.entity;

import lombok.Getter;

public enum RequirementStatus {

    NEW("新建"),
    PROCESSING("处理中"),
    FINISH("完成");

    @Getter
    private String name;

    RequirementStatus(String name) {
        this.name = name;
    }
}
