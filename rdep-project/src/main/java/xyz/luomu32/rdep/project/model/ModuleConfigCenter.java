package xyz.luomu32.rdep.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "module_config_centers")
public class ModuleConfigCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long moduleId;

    private ModuleConfigCenterType type;

    private String url;

    private String prefix;
}
