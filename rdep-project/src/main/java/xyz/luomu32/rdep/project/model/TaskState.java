package xyz.luomu32.rdep.project.model;

public enum TaskState {

    /**
     * 新建
     */
    NEW("新建"),

    /**
     * 处理中
     */
    PROCESSING("处理中"),

    /**
     * 已完成
     */
    FINISHED("已完成"),

    UNKNOWN("未知");

    private String name;

    public String getName() {
        return this.name;
    }

    TaskState(String name) {
        this.name = name;
    }

    public static TaskState fromName(String name) {
        switch (name) {
            case "新建":
                return NEW;
            case "处理中":
                return PROCESSING;
            case "已完成":
                return FINISHED;
            default:
                return UNKNOWN;
        }
    }
}
