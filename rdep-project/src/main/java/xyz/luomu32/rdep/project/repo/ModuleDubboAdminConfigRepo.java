package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.entity.ModuleDubboAdminConfig;

public interface ModuleDubboAdminConfigRepo extends JpaRepository<ModuleDubboAdminConfig, Long> {

    ModuleDubboAdminConfig findByModuleId(Long moduleId);
}
