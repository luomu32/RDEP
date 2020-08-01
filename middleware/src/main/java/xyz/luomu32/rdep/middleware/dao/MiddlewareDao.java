package xyz.luomu32.rdep.middleware.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.middleware.entity.Middleware;

import java.util.List;
import java.util.Optional;

public interface MiddlewareDao extends JpaRepository<Middleware, Long>, JpaSpecificationExecutor<Middleware> {

    List<Middleware> findByCategoryId(Optional<Long> categoryId);
}
