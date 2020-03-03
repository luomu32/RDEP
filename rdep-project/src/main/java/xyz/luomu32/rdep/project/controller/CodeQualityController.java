package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.luomu32.rdep.project.service.CodeQualityService;

@RestController
@RequestMapping("/project/{projectId}/modules/{moduleId}/code-quality")
public class CodeQualityController {

    @Autowired
    private CodeQualityService codeQualityService;

    @PostMapping("findbugs")
    public void findbugsCheck(@PathVariable Long projectId,
                              @PathVariable Long moduleId) {
        codeQualityService.findBugsCheck(projectId, moduleId);
    }
}
