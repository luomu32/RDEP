package xyz.luomu32.rdep.user.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String remark;
}
