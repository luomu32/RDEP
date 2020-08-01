package xyz.luomu32.rdep.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.project.model.ProjectPeople;
import xyz.luomu32.rdep.project.model.ProjectPeopleRole;
import xyz.luomu32.rdep.project.pojo.ProjectPeopleCreateCmd;
import xyz.luomu32.rdep.project.pojo.ProjectPeopleResponse;
import xyz.luomu32.rdep.project.repo.ProjectPeopleRepo;
import xyz.luomu32.rdep.project.service.ProjectPeopleService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectPeopleServiceImpl implements ProjectPeopleService {
    @Autowired
    private ProjectPeopleRepo projectPeopleRepo;

    @Override
    public void create(ProjectPeopleCreateCmd createCmd) {

        projectPeopleRepo.saveAll(createCmd.getPeople().stream().map(c -> {
            ProjectPeople projectPeople = new ProjectPeople();
            projectPeople.setProjectId(createCmd.getProjectId());
            projectPeople.setRole(ProjectPeopleRole.valueOf(createCmd.getRole()));
            projectPeople.setPeopleId(c.getId());
            projectPeople.setPeopleName(c.getName());
            return projectPeople;
        }).collect(Collectors.toList()));
    }

    @Override
    public List<ProjectPeopleResponse> fetch(Long projectId) {
        return projectPeopleRepo.findByProjectId(projectId)
                .stream()
                .map(ProjectPeopleResponse::from)
                .collect(Collectors.toList());
    }
}
