package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.Module;

import java.util.List;

public interface ModuleRepo extends JpaRepository<Module, Long> {

    List<Module> findByProjectId(Long projectId);
}
