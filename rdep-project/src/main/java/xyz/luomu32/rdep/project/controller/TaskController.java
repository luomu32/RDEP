package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import xyz.luomu32.rdep.common.web.DateRange;
import xyz.luomu32.rdep.common.web.DateRangeCustomEditor;
import xyz.luomu32.rdep.project.pojo.task.TaskCreateRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskQueryRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskResponse;
import xyz.luomu32.rdep.project.service.TaskService;

import java.util.List;

//PathVariable like projectId、moduleId will auto inject
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
    public void create(@Validated TaskCreateRequest task) {
        taskService.create(task);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping
    public Page<TaskResponse> fetch(TaskQueryRequest query, Pageable pageable) {
        return taskService.fetch(query, pageable);
    }

    @GetMapping(params = "all")
    public List<TaskResponse> fetch(TaskQueryRequest query) {
        return taskService.fetch(query);
    }
}
