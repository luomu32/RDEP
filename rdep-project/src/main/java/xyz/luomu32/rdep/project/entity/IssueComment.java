package xyz.luomu32.rdep.project.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "issue_comments")
public class IssueComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long issueId;

    private Long creatorId;

    private String creatorName;

    private LocalDateTime createdDatetime;

    private String content;
}
