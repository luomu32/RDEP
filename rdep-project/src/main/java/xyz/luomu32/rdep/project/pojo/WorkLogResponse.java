package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.entity.WorkLog;

import java.time.LocalDateTime;

@Data
public class WorkLogResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createDatetime;

    private LocalDateTime lastUpdateDatetime;


    public static WorkLogResponse from(WorkLog workLog) {
        WorkLogResponse response = new WorkLogResponse();
        response.setId(workLog.getId());
        response.setTitle(workLog.getTitle());
        response.setContent(workLog.getContent());
        response.setCreateDatetime(workLog.getCreateDatetime());
        response.setLastUpdateDatetime(workLog.getLastUpdateDatetime());
        return response;
    }
}
