package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.entity.Module;
import xyz.luomu32.rdep.project.entity.Project;
import xyz.luomu32.rdep.project.pojo.BuildHistoryResponse;

import java.io.File;
import java.util.List;

public interface BuildService {

    File fetchCode(Project project, Module module);

    void unpack(File dir, Module module);

    void build(Long projectId, Long moduleId);

    List<BuildHistoryResponse> fetchBuildHistories(Long projectId, Long moduleId);

//    void buildDockerImage();
}
