package xyz.luomu32.rdep.project.entity;

public enum IssueStatus {

    /**
     * 新建
     */
    NEW("新建"),

    /**
     * 处理中
     */
    PROCESSING("处理中"),

    /**
     * 已处理
     */
    PROCESSED("已处理"),

    /**
     * 已拒绝
     */
    REJECTED("已拒绝"),

    /**
     * 重新打开
     */
    REOPEN("重新打开"),

    /**
     * 测试中
     */
    TESTING("测试中"),

    /**
     * 已完成
     */
    FINISH("已完成");


    private String name;

    IssueStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
