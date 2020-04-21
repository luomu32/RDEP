package xyz.luomu32.rdep.project.model;

import lombok.*;
import xyz.luomu32.rdep.common.DeleteFlag;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long ownerId;

    private String ownerName;

    private String ciUrl;

    private String gitUrl;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private DeleteFlag deleted;


}
