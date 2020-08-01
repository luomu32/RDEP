package xyz.luomu32.rdep.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.user.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
