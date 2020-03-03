package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.Principal;
import xyz.luomu32.rdep.project.entity.IssueComment;
import xyz.luomu32.rdep.project.pojo.IssueCommentRequest;
import xyz.luomu32.rdep.project.pojo.IssueCommentResponse;
import xyz.luomu32.rdep.project.repo.IssueCommentRepo;
import xyz.luomu32.rdep.project.service.IssueCommentService;
import xyz.luomu32.rdep.project.service.UserService;

import java.time.LocalDateTime;

@Service
public class IssueCommentServiceImpl implements IssueCommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private IssueCommentRepo issueCommentRepo;

    @Override
    public void create(IssueCommentRequest request, Principal principal) {
        IssueComment comment = new IssueComment();
        comment.setIssueId(request.getIssueId());
        comment.setContent(request.getContent());
        comment.setCreatorId(request.getCreatorId());
        comment.setCreatorName(principal.getUsername());
        comment.setCreatedDatetime(LocalDateTime.now());

        issueCommentRepo.save(comment);
    }

    @Override
    public Page<IssueCommentResponse> fetch(Long projectId,
                                            Long moduleId, Long issueId,
                                            Pageable pageable) {

        return issueCommentRepo.findByIssueIdOrderByCreatedDatetimeDesc(issueId, pageable)
                .map(comment -> IssueCommentResponse.from(comment));
    }
}
