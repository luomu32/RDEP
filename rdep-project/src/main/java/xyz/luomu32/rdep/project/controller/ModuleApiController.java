package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.luomu32.rdep.project.service.ModuleApiService;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}/api")
public class ModuleApiController {

    @Autowired
    private ModuleApiService moduleApiService;

    @GetMapping
    public void fetch(@PathVariable Long projectId,
                      @PathVariable Long moduleId) {
        moduleApiService.fetch(projectId, moduleId);
    }

}
