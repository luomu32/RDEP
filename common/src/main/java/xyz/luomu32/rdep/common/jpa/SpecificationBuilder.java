package xyz.luomu32.rdep.common.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.common.web.DateRange;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SpecificationBuilder {

    public static <T> Optional<Specification<T>> build(Object o) {
        List<Specification<T>> specifications = new ArrayList<>();
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(o);
            } catch (IllegalAccessException e) {
                log.warn("can not access field:{} value", field);
                continue;
            }
            if (null != value) {
                Class fieldType = field.getType();
                if (fieldType.isAssignableFrom(Long.class) || fieldType.isAssignableFrom(String.class)
                        || fieldType.isAssignableFrom(LocalDate.class) || fieldType.isAssignableFrom(LocalDateTime.class))
                    specifications.add(new EqualsSpecification<T>(fieldName, value));
                else if (fieldType.isAssignableFrom(DateRange.class))
                    specifications.add(new DateRangeSpecification<T>(fieldName, (DateRange) value));
                else
                    log.info("not support for type:{} build specification", fieldType);
            }
        }

        return specifications.stream().reduce((s1, s2) -> s1.and(s2));
    }
}
