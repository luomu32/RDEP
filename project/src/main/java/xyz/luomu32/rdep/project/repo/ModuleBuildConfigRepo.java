package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.ModuleBuildConfig;

import java.util.Optional;

public interface ModuleBuildConfigRepo extends JpaRepository<ModuleBuildConfig, Long> {

    public Optional<ModuleBuildConfig> findByModuleId(long moduleId);
}
