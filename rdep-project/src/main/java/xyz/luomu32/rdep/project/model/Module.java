package xyz.luomu32.rdep.project.model;

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

    @Column(nullable = false)
    private Long projectId;
    @Column(nullable = false)
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
    @Column(nullable = false)
    private String scmUrl;

    private String language;

    private String frameworks;

    private String configCenterUrl;


}
