package xyz.luomu32.common.test;

import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import xyz.luomu32.rdep.common.jpa.SpecificationBuilder;

import java.time.LocalDate;
import java.util.Optional;

public class SpecificationBuilderTest {

    @Test
    public void testBuildEmpty() {
        Optional<Specification<Query>> spec = SpecificationBuilder.build(new Query());
        Assertions.assertThat(spec).isNotPresent();
    }

    @Test
    public void testBuild() {
        Query query = new Query();
        query.setId(1L);
        query.setLevel(2);
        query.setName("zhange");
        query.setDate(LocalDate.now());
        Optional<Specification<Query>> spec = SpecificationBuilder.build(query);
        Assertions.assertThat(spec).isPresent();
    }

    @Getter
    @Setter
    static class Query {
        private Long id;

        private String name;

        private Integer level;

        private LocalDate date;
    }
}
