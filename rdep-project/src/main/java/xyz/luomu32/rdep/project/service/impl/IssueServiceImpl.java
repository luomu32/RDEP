package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.entity.*;
import xyz.luomu32.rdep.project.pojo.IssueAttachmentResponse;
import xyz.luomu32.rdep.project.pojo.IssueRequest;
import xyz.luomu32.rdep.project.pojo.IssueResponse;
import xyz.luomu32.rdep.project.repo.IssueAttachmentRepo;
import xyz.luomu32.rdep.project.repo.IssueRepo;
import xyz.luomu32.rdep.project.repo.IssueTagRepo;
import xyz.luomu32.rdep.project.service.IssueService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepo issueRepo;
    @Autowired
    private IssueTagRepo issueTagRepo;
    @Autowired
    private IssueAttachmentRepo issueAttachmentRepo;

    private static final String UPLOAD_DIR;

    static {
        UPLOAD_DIR = System.getProperty("user.home") + "/rdep workdir";
    }

    @Override
    public void create(IssueRequest issueRequest) {
        Issue issue = new Issue();
        issue.setProjectId(issue.getProjectId());
        issue.setModuleId(issue.getModuleId());
        issue.setTitle(issueRequest.getTitle());
        issue.setLevel(issueRequest.getLevel());
        issue.setContent(issueRequest.getContent());
        issue.setStatus(IssueStatus.NEW);
        issue.setTags(issueRequest.getTags());

        issueRepo.save(issue);
    }

    @Override
    public void changeStatus(Long id, IssueStatus status, Long userId) {
        Issue issue = issueRepo.findById(id).orElseThrow(() -> new ServiceException("issue.not.found"));

        if (status.equals(IssueStatus.FINISH) && !issue.getCreateBy().equals(userId)) {
            throw new ServiceException("issue.finish.only.by.creator");
        }

        issue.setStatus(status);
        issueRepo.save(issue);
    }

    @Override
    public Page<IssueResponse> fetch(IssueRequest issueRequest, Pageable pageable) {
        //TODO 投影，目前无法与Specification同时使用

//        Page<IssueWithoutContent> issues = issueRepo.findAll((Specification<IssueWithoutContent>)
//                (root, query, criteriaBuilder) -> criteriaBuilder.and(
//                        criteriaBuilder.equal(root.get("projectId"), issueRequest.getProjectId()),
//                        criteriaBuilder.equal(root.get("moduleId"), issueRequest.getModuleId())
//                ), pageable);
        Page<IssueWithoutContent> issues = issueRepo.findByProjectIdAndModuleId(issueRequest.getProjectId(), issueRequest.getModuleId(), pageable);
        return issues.map(issue -> {
            List<IssueTag> tags;
            if (StringUtils.isEmpty(issue.getTags())) {
                tags = Collections.emptyList();
            } else
                tags = Stream.of(issue.getTags().split(",")).map(s ->
                        issueTagRepo.findById(Long.parseLong(s)).orElse(null)
                ).collect(Collectors.toList());
            return IssueResponse.from(issue, tags);
        });
    }

    @Override
    public IssueResponse fetch(Long id) {
        Issue issue = issueRepo.findById(id).orElseThrow(() -> new ServiceException("issue.not.found"));
        return IssueResponse.from(issue, StringUtils.isEmpty(issue.getTags()) ? Collections.emptyList() : Stream.of(issue.getTags().split(",")).map(s ->
                issueTagRepo.findById(Long.parseLong(s)).orElse(null)
        ).collect(Collectors.toList()));
    }

    @Override
    public List<IssueAttachmentResponse> createAttachment(Long id,
                                                          Long projectId,
                                                          Long moduleId,
                                                          Map<String, MultipartFile> files) {

        List<IssueAttachmentResponse> response = new ArrayList<>(files.size());
        for (MultipartFile file : files.values()) {
            try {
                File dist = new File(UPLOAD_DIR + "/" + projectId + "/" + moduleId);
                dist.mkdirs();

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
                int width = image.getWidth();
                int height = image.getHeight();

                File localFile = new File(dist, file.getOriginalFilename());
                file.transferTo(localFile);
                IssueAttachment attachment = new IssueAttachment();
                attachment.setIssueId(id);
                attachment.setFilename(file.getOriginalFilename());
                attachment.setUrl(localFile.getAbsolutePath());
                attachment.setHeight(height);
                attachment.setWidth(width);
                issueAttachmentRepo.save(attachment);

                attachment.setUrl("/projects/" + projectId + "/modules/" + moduleId + "/issues/" + id + "/attachments/" + attachment.getId());
                response.add(IssueAttachmentResponse.from(attachment));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @Override
    public void deleteAttachment(Long projectId, Long moduleId, Long issueId, Long id) {
        IssueAttachment attachment = issueAttachmentRepo.findById(id)
                .orElseThrow(() -> new ServiceException("issue.attachment.not.found"));

        String url = attachment.getUrl();
        if (url.startsWith("http") || url.startsWith("https")) {
            //TODO support upload file to cloud and delete
        } else {
            try {
                Files.delete(Paths.get(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        issueAttachmentRepo.delete(attachment);
    }

    @Override
    public List<IssueAttachmentResponse> fetchAttachment(Long projectId, Long moduleId, Long issueId) {
        return issueAttachmentRepo.findByIssueId(issueId)
                .stream()
                .map(issueAttachment -> {
                    IssueAttachmentResponse response = new IssueAttachmentResponse();
                    response.setId(issueAttachment.getId());
                    response.setFilename(issueAttachment.getFilename());
                    response.setWidth(issueAttachment.getWidth());
                    response.setHeight(issueAttachment.getHeight());

                    if (!issueAttachment.getUrl().startsWith("http") && !issueAttachment.getUrl().startsWith("https")) {
                        response.setUrl("/projects/" + projectId + "/modules/" + moduleId + "/issues/" + issueId + "/attachments/" + issueAttachment.getId());
                    } else {
                        response.setUrl(issueAttachment.getUrl());
                    }
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    public byte[] fetchAttachment(Long projectId, Long moduleId, Long issueId, Long attachmentId) {
        IssueAttachment attachment = issueAttachmentRepo.findById(attachmentId)
                .orElseThrow(() -> new ServiceException("issue.attachment.not.found"));


        try {
            return Files.readAllBytes(Paths.get(attachment.getUrl()));
        } catch (IOException e) {
            return new byte[0];
        }

    }
}
