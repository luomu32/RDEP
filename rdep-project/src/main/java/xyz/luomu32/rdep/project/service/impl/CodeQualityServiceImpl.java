package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.Module;
import xyz.luomu32.rdep.project.model.Project;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.repo.ProjectRepo;
import xyz.luomu32.rdep.project.service.CodeQualityService;

import java.io.File;

@Service
public class CodeQualityServiceImpl implements CodeQualityService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ModuleRepo moduleRepo;

    @Override
    public void findBugsCheck(Long projectId, Long moduleId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        //get work dir
        String dir = System.getProperty("user.home") + "/rdep workdir/";
        File moduleDir = new File(dir + project.getName() + "/" + module.getName());

        
    }
}
