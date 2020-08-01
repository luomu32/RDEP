package xyz.luomu32.rdep.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.luomu32.rdep.project.pojo.ProjectRequest;
import xyz.luomu32.rdep.project.pojo.ProjectResponse;

public interface ProjectService {

    void create(ProjectRequest project);

    void delete(Long id);

    Page<ProjectResponse> fetch(ProjectRequest project, Pageable pageable);

    ProjectResponse fetch(Long id);
}
