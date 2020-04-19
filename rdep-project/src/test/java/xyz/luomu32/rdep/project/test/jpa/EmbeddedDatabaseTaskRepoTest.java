package xyz.luomu32.rdep.project.test.jpa;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.luomu32.rdep.project.entity.Task;
import xyz.luomu32.rdep.project.repo.TaskRepo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;


/**
 * 扫描@Entity，@Component、@Service、@Controller不会扫描进容器
 * JdbcTemplate、TestEntityManager会被注册到容器内
 * 如果classpath有Embedded Database依赖，自动配置嵌入式数据库
 * 默认情况，每个测试用例执行完回滚
 * 不需要在test/resources下新建schema.sql，建库语句。hibernate会自动create-drop
 * <p>
 * 对于Junit4，需要配合@RunWith使用，Junit5，可以直接使用@DataJpaTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmbeddedDatabaseTaskRepoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TaskRepo taskRepo;

    @Test
    public void testSetUp() {
        assertThat(jdbcTemplate).isNotNull();
        assertThat(taskRepo).isNotNull();
    }

    @Test
    public void testTaskDateRangeQueryWithSpecification() {
        Task task = new Task();
        task.setProjectId(2L);
        task.setModuleId(3L);
        task.setTitle("test");
        task.setStart(LocalDate.of(2020, 04, 11));
        taskRepo.save(task);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Task> noTasks = taskRepo.findAll(new TaskStartDateRangeSpec(
                LocalDate.of(2020, 04, 8),
                LocalDate.of(2020, 04, 10)
        ), pageable);
        assertThat(noTasks.getTotalElements()).isEqualTo(0L);


        Page<Task> oneTask = taskRepo.findAll(new TaskStartDateRangeSpec(
                LocalDate.of(2020, 04, 8),
                LocalDate.of(2020, 04, 11)
        ), pageable);
        assertThat(oneTask).hasSize(1);

        Page<Task> afterTask = taskRepo.findAll(new TaskStartDateRangeSpec(
                LocalDate.of(2020, 04, 12),
                LocalDate.of(2020, 04, 13)
        ), pageable);
        assertThat(afterTask).hasSize(0).withFailMessage("超过");
    }
}
