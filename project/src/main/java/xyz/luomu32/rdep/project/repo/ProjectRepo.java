package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.Project;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
