package xyz.luomu32.rdep.project.controller.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.IssueTagRequest;
import xyz.luomu32.rdep.project.pojo.IssueTagResponse;
import xyz.luomu32.rdep.project.service.IssueTagService;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/modules/{moduleId}/issues/tags")
public class IssueTagController {

    @Autowired
    private IssueTagService issueTagService;

    @GetMapping
    public List<IssueTagResponse> fetch(@PathVariable Long moduleId) {
        return issueTagService.fetch(moduleId);
    }

    @PostMapping
    public void create(@PathVariable Long moduleId,
                       IssueTagRequest issueTagRequest) {
        issueTagRequest.setModuleId(moduleId);
        issueTagService.create(issueTagRequest);
    }
}
