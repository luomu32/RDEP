package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "module_middlewares")
public class Middleware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private Long moduleId;

    private String name;

    private String version;

    private MiddlewareStatus status;


}

