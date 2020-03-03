package xyz.luomu32.rdep.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.project.entity.Requirement;

public interface RequirementRepo extends JpaRepository<Requirement, Long>, JpaSpecificationExecutor<Requirement> {

    Page<Requirement> findByProjectId(Long projectId, Pageable pageable);
}
