package xyz.luomu32.rdep.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "module_build_configs")
public class ModuleBuildConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private Long moduleId;
    private ModuleBuildType type;
}
