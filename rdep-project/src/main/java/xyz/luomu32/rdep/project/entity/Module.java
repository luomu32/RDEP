package xyz.luomu32.rdep.project.entity;

import lombok.*;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "modules")
public class Module extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private String name;

    /**
     * 用于服务发现
     */
    private String serviceId;

    /**
     * 额外信息，用JSON
     */
    private String info;

    private Long principalId;

    private String principalName;

    private String scmUrl;

    private String language;

    private String frameworks;

    private String configCenterUrl;


}
