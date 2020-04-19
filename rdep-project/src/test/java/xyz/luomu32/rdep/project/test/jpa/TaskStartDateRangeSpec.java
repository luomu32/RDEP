package xyz.luomu32.rdep.project.test.jpa;

import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.project.entity.Task;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class DateRangeSpec implements Specification<Task> {
    
    private LocalDate start;
    private LocalDate end;

    public DateRangeSpec(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("start"), start),
                criteriaBuilder.lessThanOrEqualTo(root.get("start"), end)
        );
    }
}
