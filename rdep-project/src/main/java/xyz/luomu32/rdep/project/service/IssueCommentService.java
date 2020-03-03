package xyz.luomu32.rdep.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.luomu32.rdep.common.Principal;
import xyz.luomu32.rdep.project.pojo.IssueCommentRequest;
import xyz.luomu32.rdep.project.pojo.IssueCommentResponse;

public interface IssueCommentService {

    void create(IssueCommentRequest request, Principal principal);

    Page<IssueCommentResponse> fetch(Long projectId,
                                     Long moduleId,
                                     Long issueId,
                                     Pageable pageable);
}
