package xyz.luomu32.rdep.project.test.jpa;

import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.project.model.Task;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TaskProjectSpec implements Specification<Task> {

    private Long projectId;

    public TaskProjectSpec(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (null == projectId)
            return null;

        return criteriaBuilder.equal(root.get("projectId"), projectId);
    }
}
