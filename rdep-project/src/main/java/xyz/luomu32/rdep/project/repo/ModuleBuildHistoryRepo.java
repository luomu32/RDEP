package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.project.model.ModuleBuildHistory;
import xyz.luomu32.rdep.project.model.ModuleBuildHistoryStatus;

import java.util.List;
import java.util.Optional;

public interface ModuleBuildHistoryRepo extends JpaRepository<ModuleBuildHistory, Long>, JpaSpecificationExecutor<ModuleBuildHistory> {

    Optional<ModuleBuildHistory> findFirst1ByModuleIdAndStatusOrderByCreateDatetimeDesc(Long moduleId, ModuleBuildHistoryStatus status);


    List<ModuleBuildHistory> findByModuleId(Long moduleId);
}
