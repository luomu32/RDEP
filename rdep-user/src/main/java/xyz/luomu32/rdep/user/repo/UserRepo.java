package xyz.luomu32.rdep.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.user.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    int countByRoleId(Long roleId);
}
