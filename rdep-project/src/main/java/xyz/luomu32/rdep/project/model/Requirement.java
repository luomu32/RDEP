package xyz.luomu32.rdep.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "requirements")
public class Requirement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private Long moduleId;

    private Long owenId;

    private String owenName;

    private String title;

    private String content;

    private LocalDate deadline;

    private RequirementStatus status;

    private Integer level;

    private Integer schedule;

    private LocalDate startDate;
}
