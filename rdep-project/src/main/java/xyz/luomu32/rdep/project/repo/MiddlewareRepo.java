package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.entity.Middleware;

public interface MiddlewareRepo extends JpaRepository<Middleware, Long> {
}
