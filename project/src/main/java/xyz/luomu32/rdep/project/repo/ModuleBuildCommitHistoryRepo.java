package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.ModuleBuildCommitHistory;

import java.util.List;

public interface ModuleBuildCommitHistoryRepo extends JpaRepository<ModuleBuildCommitHistory, Long> {

    List<ModuleBuildCommitHistory> findByModuleBuildHistoryId(Long moduleBuildHistoryId);
}
