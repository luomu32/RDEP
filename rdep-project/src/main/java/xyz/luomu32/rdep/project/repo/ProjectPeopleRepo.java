package xyz.luomu32.rdep.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.luomu32.rdep.project.model.ProjectPeople;

import java.util.List;

public interface ProjectPeopleRepo extends JpaRepository<ProjectPeople, Long> {

    List<ProjectPeople> findByProjectId(Long projectId);
}
