package xyz.luomu32.rdep.project.test.jpa;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.luomu32.rdep.project.model.Module;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.test.category.DevCategory;
import xyz.luomu32.rdep.project.test.category.TestCategory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Category({DevCategory.class, TestCategory.class})
public class ModuleRepoTest {

    @Autowired
    private ModuleRepo moduleRepo;

    /**
     * 测试创建模块时，Service ID重名的场景
     */
    @Test
    public void testCreateWithExistService() {
        Module module = new Module();
        module.setProjectId(1L);
        module.setName("用户");
        module.setScmUrl("git://github.com");
        module.setServiceId("user");
        moduleRepo.save(module);

        boolean exist = moduleRepo.existsByProjectIdAndServiceId(1L, "user");
        Assertions.assertThat(exist).isTrue();
    }
}
