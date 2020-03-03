package xyz.luomu32.rdep.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.entity.WorkLog;

public interface WorkLogRepo extends JpaRepository<WorkLog, Long> {

    Page<WorkLog> findByCreateBy(Long createBy, Pageable pageable);
}
