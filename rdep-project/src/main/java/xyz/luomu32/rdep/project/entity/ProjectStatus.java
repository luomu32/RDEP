package xyz.luomu32.rdep.project.entity;

import lombok.Getter;

public enum ProjectStatus {

    /**
     * 新增
     */
    NEW("新增"),
    /**
     * 审核中
     */
    UNDDER_REVIEW("审核中"),
    /**
     * 审核通过
     */
    REVIEW_PASSED("审核通过"),
    /**
     * 审核驳回
     */
    REVIEW_REJECTION("审核驳回"),

    /**
     * 开发中
     */
    DEVELOPING("开发中"),

    /**
     * UAT测试中
     */
    UAT_TESTING("UAT测试中"),

    /**
     * 验收中
     */
    ACCEPTANCE("验收中"),

    /**
     * 已交付
     */
    PAID("已交付"),

    /**
     * 维护中
     */
    IN_MAINTENANCE("维护中");


    @Getter
    private String desc;

    ProjectStatus(String desc) {
        this.desc = desc;
    }

}
