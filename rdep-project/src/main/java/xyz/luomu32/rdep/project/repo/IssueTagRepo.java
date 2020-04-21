package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.IssueTag;

public interface IssueTagRepo extends JpaRepository<IssueTag, Long> {

    int countByModuleIdAndName(Long moduleId, String name);

    int countByModuleIdAndColor(Long moduleId, String color);
}
