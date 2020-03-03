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

    private String label;

    @Column(name = "\"desc\"")
    private String desc;

    private Long principalId;

    private String principalName;

    private String scmUrl;

    private String language;

    private String frameworks;

    private String configCenterUrl;

    private String info;

}
