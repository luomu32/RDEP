package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.ProjectRequest;
import xyz.luomu32.rdep.project.pojo.ProjectResponse;
import xyz.luomu32.rdep.project.service.ProjectService;

@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    Page<ProjectResponse> fetch(ProjectRequest projectRequest, Pageable pageable) {
        return projectService.fetch(projectRequest, pageable);
    }

    @GetMapping("{id}")
    ProjectResponse fetch(@PathVariable Long id) {
        return projectService.fetch(id);
    }

    @PostMapping
    void create(ProjectRequest projectRequest) {
        projectService.create(projectRequest);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        projectService.delete(id);
    }

}
