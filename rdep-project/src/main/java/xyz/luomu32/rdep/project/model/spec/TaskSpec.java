package xyz.luomu32.rdep.project.model.spec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.project.model.Task;

public class TaskSpec {

    public Specification<Task> isBelongToProject(Long projectId) {
        if (null == projectId)
            return null;
        return (root, query, builder) -> builder.equal(root.get("projectId"), projectId);
    }

    public Specification<Task> isBelongToModule(Long moduleId) {
        if (null == moduleId)
            return null;
        return (root, query, builder) -> builder.equal(root.get("moduleId"), moduleId);
    }

    public Specification<Task> isTitleEquals(String title) {
        if (StringUtils.isBlank(title))
            return null;
        return (root, query, builder) -> builder.equal(root.get("title"), title);
    }

}
