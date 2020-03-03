package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.DeleteFlag;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.entity.Project;
import xyz.luomu32.rdep.project.entity.ProjectStatus;
import xyz.luomu32.rdep.project.pojo.ProjectRequest;
import xyz.luomu32.rdep.project.pojo.ProjectResponse;
import xyz.luomu32.rdep.project.repo.ProjectRepo;
import xyz.luomu32.rdep.project.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public void create(ProjectRequest projectRequest) {

        projectRepo.save(Project.builder()
                .name(projectRequest.getName())
                .gitUrl(projectRequest.getScmURL())
                .status(ProjectStatus.NEW)
                .deleted(DeleteFlag.UN_DELETED)
                .build());
    }

    @Override
    public void delete(Long id) {
        Project project = projectRepo.findById(id).orElseThrow(() -> new ServiceException("project.not.found"));
        if (project.getStatus() != ProjectStatus.NEW
                && project.getStatus() != ProjectStatus.REVIEW_REJECTION) {
            throw new ServiceException("project.status.not.allow.delete");
        }

        project.setDeleted(DeleteFlag.DELETED);
        projectRepo.save(project);
    }

    @Override
    public Page<ProjectResponse> fetch(ProjectRequest projectRequest, Pageable pageable) {

        Example<Project> query;
        if (null == projectRequest)
            query = Example.of(Project.builder()
                    .deleted(DeleteFlag.UN_DELETED)
                    .build());
        else query = Example.of(Project.builder()
                .name(projectRequest.getName())
                .deleted(DeleteFlag.UN_DELETED)
                .build());

        return projectRepo.findAll(query, pageable).map(ProjectResponse::from);
    }

    @Override
    public ProjectResponse fetch(Long id) {
        return ProjectResponse.from(projectRepo.findById(id).orElseThrow(() -> new ServiceException("project.not.found")));
    }
}
