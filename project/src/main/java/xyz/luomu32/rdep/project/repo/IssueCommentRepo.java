package xyz.luomu32.rdep.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.IssueComment;

public interface IssueCommentRepo extends JpaRepository<IssueComment, Long> {

    Page<IssueComment> findByIssueIdOrderByCreatedDatetimeDesc(Long issueId, Pageable pageable);
}
