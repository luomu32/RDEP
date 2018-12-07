package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机器资源
 */
@Data
@Entity
@Table(name = "t_machine_resource")
public class MachineResource {

    /**
     * 项目/产品ID
     */
    private Long projectId;

    /**
     * 机器IP地址
     */
    private Integer ipAddress;

    private String username;

    private String password;

    private String sshKeyLocation;
}
