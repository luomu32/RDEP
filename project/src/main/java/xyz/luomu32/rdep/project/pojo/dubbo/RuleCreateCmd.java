package xyz.luomu32.rdep.project.pojo.dubbo;

import lombok.Data;

import java.util.List;

@Data
public class RuleCreateCmd {

    private String client;

    private String scope;

    private String key;

    private List<Config> configs;

    //如果没有static修饰类，会报错。Spring MVC反射根据构造方法实例化时，提示找不到空的构造方法
    @Data
    public static class Config {
        private String key;
        private String value;
    }
}
