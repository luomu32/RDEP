package xyz.luomu32.rdep.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "xyz.luomu32.rdep.project.repo")
public class ProjectApp {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApp.class, args);
    }
}
