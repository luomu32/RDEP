package xyz.luomu32.rdep.project.controller.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.common.Principal;
import xyz.luomu32.rdep.common.jwt.JwtPrincipal;
import xyz.luomu32.rdep.project.pojo.IssueCommentRequest;
import xyz.luomu32.rdep.project.pojo.IssueCommentResponse;
import xyz.luomu32.rdep.project.service.IssueCommentService;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}/issues/{issueId}/comments")
public class IssueCommentController {

    @Autowired
    private IssueCommentService issueCommentService;

    @PostMapping
    public void create(@PathVariable Long projectId,
                       @PathVariable Long moduleId,
                       @PathVariable Long issueId,
                       IssueCommentRequest request,
                       @JwtPrincipal Principal principal) {
        issueCommentService.create(request, principal);
    }

    @GetMapping
    public Page<IssueCommentResponse> fetch(@PathVariable Long projectId,
                                            @PathVariable Long moduleId,
                                            @PathVariable Long issueId,
                                            Pageable pageable) {
        return issueCommentService.fetch(projectId, moduleId, issueId, pageable);
    }

}
