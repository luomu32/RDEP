package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.common.jpa.DateRangeSpecification;
import xyz.luomu32.rdep.project.model.Task;
import xyz.luomu32.rdep.project.model.TaskState;
import xyz.luomu32.rdep.project.model.Task_;
import xyz.luomu32.rdep.project.model.spec.TaskSpec;
import xyz.luomu32.rdep.project.pojo.task.TaskCreateRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskQueryRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskResponse;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.repo.TaskRepo;
import xyz.luomu32.rdep.project.service.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ModuleRepo moduleRepo;

    @Override
    public void create(TaskCreateRequest task) {

        String moduleName = null;
        if (null != task.getModuleId())
            moduleName = moduleRepo.findById(task.getModuleId())
                    .orElseThrow(() -> new ServiceException("module.not.found"))
                    .getName();

        Task t = new Task();
        t.setTitle(task.getTitle());
        t.setState(TaskState.NEW);
        t.setProjectId(task.getProjectId());
        t.setModuleId(task.getModuleId());
        t.setModuleName(moduleName);
        t.setChargerId(task.getChargerId());
        t.setChargerName(task.getChargerName());
        t.setStart(task.getStart());
        t.setEnd(task.getEnd());
        t.setProgress(task.getProgress());
        t.setPriority(task.getPriority());
        taskRepo.save(t);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        taskRepo.deleteById(id);
    }

    //把Specification构建放在TaskQueryRequest。这样设计的话，就要求不同的查询方法，POJO就要不同
    //把Specification放在实体类上，

    @Override
    public Page<TaskResponse> fetch(TaskQueryRequest query, Pageable page) {
        Specification<Task> matchProject = (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("projectId"), query.getProjectId());
        Specification<Task> matchModule = (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            if (null == query.getModuleId())
                return null;
            return criteriaBuilder.equal(root.get("moduleId"), query.getModuleId());
        };
        Specification<Task> matchCharger = (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            if (null == query.getChargerId())
                return null;
            return criteriaBuilder.equal(root.get("chargerId"), query.getChargerId());
        };
        Specification<Task> matchPriority = (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            if (null == query.getPriority())
                return null;
            return criteriaBuilder.equal(root.get("priority"), query.getPriority());
        };
        Specification<Task> matchState = (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            if (null == query.getState())
                return null;
            return criteriaBuilder.equal(root.get("state"), TaskState.fromName(query.getState()).ordinal());
        };
        Specification<Task> startRange = new DateRangeSpecification<Task>("start", query.getStart());
        Specification<Task> endRange = new DateRangeSpecification<Task>("end", query.getEnd());
        return taskRepo
                .findAll(matchProject.and(matchModule).and(matchPriority).and(matchCharger).and(matchState).and(startRange).and(endRange), page)
                .map(TaskResponse::from);
    }

    @Override
    public List<TaskResponse> fetch(TaskQueryRequest query) {
        Optional<Specification<Task>> specification = query.buildSpec();
        Specification<Task> title = (root, cQuery, builder) -> builder.equal(root.get(Task_.title), "");
        if (!specification.isPresent())
            return taskRepo.findAll()
                    .stream()
                    .map(TaskResponse::from)
                    .collect(Collectors.toList());

        return taskRepo.findAll(specification.get())
                .stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse fetchOneById(Long id) {
        return taskRepo.findById(id).map(TaskResponse::from).orElse(null);
    }

}
