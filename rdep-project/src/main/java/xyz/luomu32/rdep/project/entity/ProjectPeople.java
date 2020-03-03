package xyz.luomu32.rdep.project.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project_people")
public class ProjectPeople {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private Long peopleId;

    private String peopleName;

    private ProjectPeopleRole role;
}
