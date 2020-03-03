package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scm_credentials")
public class ScmCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private Long moduleId;

    private ScmCredentialType type;

    private String username;

    private String password;
}
