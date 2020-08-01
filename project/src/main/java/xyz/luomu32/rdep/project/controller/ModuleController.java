package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.ModuleRequest;
import xyz.luomu32.rdep.project.pojo.ModuleResponse;
import xyz.luomu32.rdep.project.service.ModuleService;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping()
    public List<ModuleResponse> fetch(@PathVariable Long projectId) {
        return moduleService.fetch(projectId);
    }

    @GetMapping("{id}")
    public ModuleResponse fetch(@PathVariable Long projectId,
                                @PathVariable Long id) {
        return moduleService.fetch(projectId, id);
    }

    @PostMapping()
    public void create(@Validated ModuleRequest moduleRequest,
                       @PathVariable Long projectId) {

        moduleService.create(moduleRequest);
    }

//    @PostMapping("{moduleId}/build")
//    public void build(@PathVariable Long projectId,
//                      @PathVariable Long moduleId) {
//        moduleService.build(projectId, moduleId);
//    }

}
