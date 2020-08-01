package xyz.luomu32.rdep.common.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EqualsSpecification<T> implements Specification {

    private String field;

    private Object value;

    public EqualsSpecification(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(field), value);
    }
}
