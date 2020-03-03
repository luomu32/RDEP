package xyz.luomu32.rdep.middleware.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.middleware.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
