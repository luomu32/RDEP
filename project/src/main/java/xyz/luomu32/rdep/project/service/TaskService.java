package xyz.luomu32.rdep.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import xyz.luomu32.rdep.project.pojo.task.TaskCreateRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskQueryRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskResponse;

import java.util.List;

public interface TaskService {

    public void create(TaskCreateRequest task);

    public void update();

    public void delete(Long id);

    public Page<TaskResponse> fetch(TaskQueryRequest query, Pageable page);

    public List<TaskResponse> fetch(TaskQueryRequest query);

    public TaskResponse fetchOneById(Long id);

}
