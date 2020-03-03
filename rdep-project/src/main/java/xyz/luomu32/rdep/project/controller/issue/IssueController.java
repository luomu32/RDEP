package xyz.luomu32.rdep.project.controller.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.entity.IssueStatus;
import xyz.luomu32.rdep.project.pojo.IssueRequest;
import xyz.luomu32.rdep.project.pojo.IssueResponse;
import xyz.luomu32.rdep.project.service.IssueService;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public void create(@PathVariable Long projectId,
                       @PathVariable Long moduleId,
                       IssueRequest issueRequest) {
        issueRequest.setProjectId(projectId);
        issueRequest.setModuleId(moduleId);
        issueService.create(issueRequest);
    }

    @PutMapping("{id}/status")
    public void changeStatus(@PathVariable Long id,
                             @RequestParam String status,
                             @RequestHeader("x-user-id") Long userId) {

        issueService.changeStatus(id, IssueStatus.valueOf(status), userId);
    }

    @GetMapping
    public Page<IssueResponse> fetch(@PathVariable Long projectId,
                                     @PathVariable Long moduleId,
                                     IssueRequest issueRequest, Pageable pageable) {

        return issueService.fetch(issueRequest, pageable);
    }

    @GetMapping("{id}")
    public IssueResponse fetch(@PathVariable Long id) {
        return issueService.fetch(id);
    }


}
