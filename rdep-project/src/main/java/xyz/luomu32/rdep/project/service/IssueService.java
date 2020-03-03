package xyz.luomu32.rdep.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import xyz.luomu32.rdep.project.entity.IssueStatus;
import xyz.luomu32.rdep.project.pojo.IssueAttachmentResponse;
import xyz.luomu32.rdep.project.pojo.IssueRequest;
import xyz.luomu32.rdep.project.pojo.IssueResponse;

import java.util.List;
import java.util.Map;

public interface IssueService {

    void create(IssueRequest issueRequest);

    void changeStatus(Long id, IssueStatus status, Long userId);

    Page<IssueResponse> fetch(IssueRequest issueRequest, Pageable pageable);

    IssueResponse fetch(Long id);

    List<IssueAttachmentResponse> createAttachment(Long id,
                                                   Long projectId,
                                                   Long moduleId,
                                                   Map<String, MultipartFile> files);

    void deleteAttachment(Long projectId, Long moduleId, Long issueId, Long id);

    List<IssueAttachmentResponse> fetchAttachment(Long projectId, Long moduleId, Long issueId);

    byte[] fetchAttachment(Long projectId, Long moduleId, Long issueId, Long attachmentId);
}
