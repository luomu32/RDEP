package xyz.luomu32.rdep.project.pojo.task;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.Task;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TaskResponse {

    private Long id;
    private String title;
    private Long projectId;
    private Long moduleId;
    private String moduleName;
    private LocalDate start;
    private LocalDate end;
    private Long chargerId;
    private String chargerName;
    private String state;
    private Integer progress;
    private Integer priority;


    public static TaskResponse from(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setState(task.getState().getName());
        response.setProjectId(task.getProjectId());
        response.setModuleId(task.getModuleId());
        response.setModuleName(task.getModuleName());
        response.setChargerId(task.getChargerId());
        response.setChargerName(task.getChargerName());
        response.setStart(task.getStart());
        response.setEnd(task.getEnd());
        response.setProgress(task.getProgress());
        response.setPriority(task.getPriority());
        return response;
    }
}
