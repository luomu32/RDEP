package xyz.luomu32.rdep.common.jpa;

import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.common.web.DateRange;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class DateRangeSpecification<T> implements Specification {

    private final DateRange dateRange;

    public DateRangeSpecification(DateRange dateRange) {
        if (null == dateRange)
            throw new IllegalArgumentException("date range can not be null");
        this.dateRange = dateRange;
    }

    public abstract String getField();

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        if (dateRange.isEmpty())
            return null;

        if (dateRange.isSameDate()) {
            return criteriaBuilder.equal(root.get(getField()), dateRange.getStart());
        }

        List<Predicate> predicates = new ArrayList<>(2);
        if (null != dateRange.getStart())
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(getField()), dateRange.getStart()));
        if (null != dateRange.getEnd())
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(getField()), dateRange.getEnd()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
