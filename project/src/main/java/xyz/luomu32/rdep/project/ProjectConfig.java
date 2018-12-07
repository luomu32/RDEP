package xyz.luomu32.rdep.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "xyz.luomu32.rdep.project.repo")
public class ProjectConfig {



}
