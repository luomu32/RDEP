package xyz.luomu32.rdep.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.project.entity.Issue;
import xyz.luomu32.rdep.project.entity.IssueWithoutContent;

public interface IssueRepo extends JpaRepository<Issue, Long>, JpaSpecificationExecutor {

    Page<IssueWithoutContent> findByProjectIdAndModuleId(Long projectId, Long moduleId, Pageable pageable);
}
