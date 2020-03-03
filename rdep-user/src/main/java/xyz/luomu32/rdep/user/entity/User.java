package xyz.luomu32.rdep.user.entity;

import lombok.*;
import xyz.luomu32.rdep.common.DeleteFlag;
import xyz.luomu32.rdep.common.jpa.BaseEntity;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String realName;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String email;

    private DeleteFlag deleted;

}
