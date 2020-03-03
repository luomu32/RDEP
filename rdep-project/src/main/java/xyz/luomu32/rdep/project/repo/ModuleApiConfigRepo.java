package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.entity.ModuleApiConfig;

public interface ModuleApiConfigRepo extends JpaRepository<ModuleApiConfig, Long> {

    ModuleApiConfig findByModuleId(Long moduleId);
}
