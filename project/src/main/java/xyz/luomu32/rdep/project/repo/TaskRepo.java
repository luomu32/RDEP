package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.luomu32.rdep.project.model.Task;

public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}
