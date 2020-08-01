package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.model.Project;
import xyz.luomu32.rdep.project.pojo.BuildHistoryResponse;

import java.io.File;
import java.util.List;

public interface BuildService {

    File fetchCode(Project project, xyz.luomu32.rdep.project.model.Module module);

    void unpack(File dir, xyz.luomu32.rdep.project.model.Module module);

    void build(Long projectId, Long moduleId);

    List<BuildHistoryResponse> fetchBuildHistories(Long projectId, Long moduleId);

//    void buildDockerImage();
}
