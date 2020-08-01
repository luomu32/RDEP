package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.CheckstyleResult;
import xyz.luomu32.rdep.project.service.CodeQualityService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/modules/{moduleId}/code-quality")
public class CodeQualityController {

    @Autowired
    private CodeQualityService codeQualityService;

    @GetMapping("branch")
    public List<String> fetchGitBranch() {
        return Collections.emptyList();
    }

    @PostMapping("findbugs")
    public void findbugsCheck(@PathVariable Long projectId,
                              @PathVariable Long moduleId,
                              @RequestParam String branch) {
        codeQualityService.findBugsCheck(projectId, moduleId, branch);
    }

    @PostMapping("checkstyle")
    public CheckstyleResult checkstyleCheck(@PathVariable Long projectId,
                                            @PathVariable Long moduleId,
                                            @RequestParam String branch,
                                            @RequestParam String style) {
        return codeQualityService.checkstyleCheck(projectId, moduleId, branch, style);
    }

    @PostMapping("pmd")
    public void pmdCheck(@PathVariable Long projectId,
                         @PathVariable Long moduleId,
                         @RequestParam String branch,
                         @RequestParam String style) {
        codeQualityService.pmdCheck(projectId, moduleId, branch, style);
    }
}
