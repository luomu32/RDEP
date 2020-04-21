package xyz.luomu32.rdep.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "issue_attachments")
public class IssueAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long issueId;

    private String url;

    private String filename;

    private Integer width;

    private Integer height;
}
