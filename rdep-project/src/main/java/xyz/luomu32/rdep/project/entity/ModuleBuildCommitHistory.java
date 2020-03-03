package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "module_build_commit_histories")
public class ModuleBuildCommitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long moduleBuildHistoryId;

    private String authorName;
    private String authorEmail;

    private String message;

    private String commitId;

    private Integer time;

}
