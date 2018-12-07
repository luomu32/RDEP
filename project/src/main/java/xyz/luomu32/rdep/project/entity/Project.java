package xyz.luomu32.rdep.project.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luomu32.rdep.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_project")
public class Project extends BaseEntity {

    private Long id;

    private String name;

    private String ciUrl;

    private String gitUrl;
}
