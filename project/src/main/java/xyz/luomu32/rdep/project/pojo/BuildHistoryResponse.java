package xyz.luomu32.rdep.project.pojo;

import lombok.Data;
import xyz.luomu32.rdep.project.model.ModuleBuildCommitHistory;
import xyz.luomu32.rdep.project.model.ModuleBuildHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BuildHistoryResponse {
    private Long id;
    private Long moduleId;
    private String status;

    private LocalDateTime createDatetime;

    private List<CommitHistory> commitHistories;

    public static BuildHistoryResponse from(ModuleBuildHistory moduleBuildHistory,
                                            List<ModuleBuildCommitHistory> commitHistories) {
        BuildHistoryResponse response = new BuildHistoryResponse();
        response.setId(moduleBuildHistory.getId());
        response.setModuleId(moduleBuildHistory.getModuleId());
        response.setCreateDatetime(moduleBuildHistory.getCreateDatetime());
        response.setStatus(moduleBuildHistory.getStatus().name());
        response.setCommitHistories(commitHistories.parallelStream().map(c -> {
            CommitHistory history = new CommitHistory();
            history.setId(c.getCommitId());
            history.setTime(c.getTime());
            history.setMessage(c.getMessage());
            history.setAuthorName(c.getAuthorName());
            history.setAuthorEmail(c.getAuthorEmail());
            return history;
        }).collect(Collectors.toList()));
        return response;
    }

    @Data
    static class CommitHistory {
        private String id;
        private String authorName;
        private String authorEmail;
        private String message;
        private Integer time;
    }
}
