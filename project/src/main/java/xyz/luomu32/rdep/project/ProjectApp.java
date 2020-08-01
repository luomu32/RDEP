package xyz.luomu32.rdep.project;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class ProjectApp {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApp.class, args);
    }
}
