package xyz.luomu32.rdep.middleware.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "middlewares")
public class Middleware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String version;

    private Long categoryId;
}
