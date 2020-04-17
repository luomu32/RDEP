package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import xyz.luomu32.rdep.project.pojo.task.TaskCreateRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskQueryRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskResponse;
import xyz.luomu32.rdep.project.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(
        {
                "projects/{projectId}/tasks",
                "projects/{projectId}/{moduleId}/tasks"
        }
)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public void create(@Validated TaskCreateRequest task,
                       @PathVariable Long projectId,
                       @PathVariable(required = false) Long moduleId) {
        task.setProjectId(projectId);
        if (null != moduleId)
            task.setModuleId(moduleId);
        taskService.create(task);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping
    public Page<TaskResponse> fetch(TaskQueryRequest query, Pageable pageable,
                                    @PathVariable Long projectId,
                                    @PathVariable(required = false) Long moduleId) {
        query.setProjectId(projectId);
        if (null != moduleId)
            query.setModuleId(moduleId);
        return taskService.fetch(query, pageable);
    }

    @GetMapping(params = "all")
    public List<TaskResponse> fetch(TaskQueryRequest query,
                                    @PathVariable Long projectId,
                                    @PathVariable Long moduleId) {
        query.setProjectId(projectId);
        query.setModuleId(moduleId);
        return taskService.fetch(query);
    }
}
