package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.project.model.ModuleBuild;

import java.util.Optional;

public interface ModuleBuildRepo extends JpaRepository<ModuleBuild, Long>, JpaSpecificationExecutor<ModuleBuild> {

    Optional<ModuleBuild> findByModuleId(Long moduleId);
}
