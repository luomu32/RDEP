package xyz.luomu32.rdep.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "module_api_configs")
public class ModuleApiConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long moduleId;

    private String serviceProvider;

    private String url;
}
