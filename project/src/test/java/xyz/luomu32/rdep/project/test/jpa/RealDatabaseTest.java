package xyz.luomu32.rdep.project.test.jpa;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.luomu32.rdep.project.model.Task;
import xyz.luomu32.rdep.project.repo.TaskRepo;
import xyz.luomu32.rdep.project.test.category.TestCategory;

import java.time.LocalDate;

/**
 * 通过application.yml配置的spring.datasource，连接实际的数据库
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//测试的都是查询，不需要事务回滚.目前还不能用，Hibernate检测没有事务会报错
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Category(TestCategory.class)
public class RealDatabaseTest {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testTaskQueryByInEnd() {
        Page<Task> inTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 21),
                LocalDate.of(2020, 04, 25)
        ), PageRequest.of(0, 20));

        Assertions.assertThat(inTaskEnd).isNotEmpty();
    }

    @Test
    public void testTaskQueryByInEndOnBoundaryAfter() {
        Page<Task> inTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 21),
                LocalDate.of(2020, 04, 24)
        ), PageRequest.of(0, 20));

        Assertions.assertThat(inTaskEnd).isNotEmpty();
    }

    @Test
    public void testTaskQueryByInEndOnBoundaryBefore() {
        Page<Task> inTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 24),
                LocalDate.of(2020, 04, 25)
        ), PageRequest.of(0, 20));

        Assertions.assertThat(inTaskEnd).isNotEmpty();
    }

    @Test
    public void testTaskQueryByInEndOnSame() {
        Page<Task> inTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 24),
                LocalDate.of(2020, 04, 24)
        ), PageRequest.of(0, 20));

        Assertions.assertThat(inTaskEnd).isNotEmpty();
    }

    @Test
    public void testTaskQueryByOutEndAfter() {
        Page<Task> outTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 25),
                LocalDate.of(2020, 04, 26)
        ), PageRequest.of(0, 20));

        Assertions.assertThat(outTaskEnd).isEmpty();
    }

    @Test
    public void testTaskQueryByOutEndBefore() {
        Page<Task> outTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 21),
                LocalDate.of(2020, 04, 22)
        ), PageRequest.of(0, 20));

        Assertions.assertThat(outTaskEnd).isEmpty();
    }

    //    @Test
    public void testTaskDateRangeQuery() {
        Pageable page = PageRequest.of(0, 10);

        Page<Task> beforeTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 21),
                LocalDate.of(2020, 04, 23)
        ), page);

        Assertions.assertThat(beforeTaskEnd).isEmpty();

        Page<Task> inTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 21),
                LocalDate.of(2020, 04, 25)
        ), page);

        Assertions.assertThat(beforeTaskEnd).isNotEmpty();

        Page<Task> afterTaskEnd = taskRepo.findAll(new TaskEndDateRangeSpec(
                LocalDate.of(2020, 04, 25),
                LocalDate.of(2020, 05, 1)
        ), page);

        Assertions.assertThat(afterTaskEnd).isEmpty();
    }


}
