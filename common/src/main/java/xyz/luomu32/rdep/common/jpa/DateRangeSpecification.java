package xyz.luomu32.rdep.common.jpa;

import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.common.web.DateRange;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DateRangeSpecification<T> implements Specification {

    private final DateRange dateRange;
    private final String field;

    public DateRangeSpecification(String field, DateRange dateRange) {
        if (null == dateRange)
            throw new IllegalArgumentException("date range can not be null");
        if (null == field)
            throw new IllegalArgumentException("field can not be null");
        this.field = field;
        this.dateRange = dateRange;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        if (dateRange.isEmpty())
            return null;

        if (dateRange.isSameDate()) {
            return criteriaBuilder.equal(root.get(field), dateRange.getStart());
        }

        List<Predicate> predicates = new ArrayList<>(2);
        if (null != dateRange.getStart())
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(field), dateRange.getStart()));
        if (null != dateRange.getEnd())
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(field), dateRange.getEnd()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
