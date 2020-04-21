package xyz.luomu32.rdep.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "issue_tags")
public class IssueTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long moduleId;

    private String color;

    private String name;
}
