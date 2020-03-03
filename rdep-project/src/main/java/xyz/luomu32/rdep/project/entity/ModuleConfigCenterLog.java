package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "module_config_center_logs")
public class ModuleConfigCenterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long moduleId;

    private String key;

    private String value;

    private String newValue;

    private ModuleConfigCenterOperateType type;

    private Long operatorId;

    private String operatorName;

    private LocalDateTime operateDatetime;
}
