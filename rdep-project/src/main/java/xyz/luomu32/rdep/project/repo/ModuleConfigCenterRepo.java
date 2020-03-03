package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.entity.ModuleConfigCenter;

public interface ModuleConfigCenterRepo extends JpaRepository<ModuleConfigCenter, Long> {

    ModuleConfigCenter findByModuleId(Long moduleId);
}
