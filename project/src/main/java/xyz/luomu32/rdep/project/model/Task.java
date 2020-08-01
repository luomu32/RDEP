package xyz.luomu32.rdep.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

//TODO JPA Model Generator.By Hibernate https://docs.jboss.org/hibernate/jpamodelgen/1.0/reference/en-US/html_single/#whatisit
//生成之后，在使用Specification查询时，不需要root.get("")字符串方式，可以使用类型安全的方式
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long projectId;

    private Long moduleId;
    private String moduleName;

    private Long chargerId;

    private String chargerName;

    private TaskState state;

    private LocalDate start;

    private LocalDate end;

    private Integer priority;

    private Integer progress;
}
