package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.pojo.ProjectPeopleCreateCmd;
import xyz.luomu32.rdep.project.pojo.ProjectPeopleResponse;

import java.util.List;

public interface ProjectPeopleService {

    void create(ProjectPeopleCreateCmd createCmd);

    List<ProjectPeopleResponse> fetch(Long projectId);
}
