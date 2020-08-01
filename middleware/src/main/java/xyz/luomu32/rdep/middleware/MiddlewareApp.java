package xyz.luomu32.rdep.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class MiddlewareApp {
    public static void main(String[] args) {
        SpringApplication.run(MiddlewareApp.class, args);
    }
}
