package xyz.luomu32.rdep.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private Long moduleId;

    private String title;

    private IssueStatus status;

    private Integer level;

    private String tags;

    private Long processingPersonId;

    private String processingPersonName;

    private LocalDateTime processingDate;

    private Integer processingDuration;

    private String content;


}
