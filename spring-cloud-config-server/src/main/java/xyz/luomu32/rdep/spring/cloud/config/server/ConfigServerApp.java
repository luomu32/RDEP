package xyz.luomu32.rdep.spring.cloud.config.server;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApp {
    public static final String GIT_PATH;

    static {
//        GIT_PATH = "/home/.config_server";
        GIT_PATH = System.getProperty("user.home") + "/.config_server";
    }

    public static void main(String[] args) {

        //TODO 本地生成一个Git目录在分布式环境中会有问题，在不同Spring Cloud Config之间共享Git也是麻烦事
        try {
            Git.init().setDirectory(new File(GIT_PATH)).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Map<String, Object> properties = new HashMap<>();
        properties.put("spring.cloud.config.server.git.uri", GIT_PATH);


//        KeyGenerator kg = null; //获取密钥生成器
//        try {
//            kg = KeyGenerator.getInstance("DESede");
//            kg.init(168);                                        //初始化密钥生成器
//            SecretKey sk = kg.generateKey();
//            sk.getEncoded()
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        //TODO 对称的密钥怎么生成。密钥的管理，在分布式多节点环境中，密钥需要放置在一个公共的地方，可以让多个Spring Cloud Config节点访问到
        properties.put("encrypt.key", UUID.randomUUID().toString());

        new SpringApplicationBuilder()
                .sources(ConfigServerApp.class)
                .properties(properties)
                .run(args);

    }
}
