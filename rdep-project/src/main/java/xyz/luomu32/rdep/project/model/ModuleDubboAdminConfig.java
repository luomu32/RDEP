package xyz.luomu32.rdep.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "module_dubbo_admin_configs")
public class ModuleDubboAdminConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long moduleId;

    private String registerAddress;

    private String metadataAddress;

    private String configCenterAddress;


}
