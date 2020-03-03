package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.entity.IssueTag;
import xyz.luomu32.rdep.project.pojo.IssueTagRequest;
import xyz.luomu32.rdep.project.pojo.IssueTagResponse;
import xyz.luomu32.rdep.project.repo.IssueTagRepo;
import xyz.luomu32.rdep.project.service.IssueTagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueTagServiceImpl implements IssueTagService {
    @Autowired
    IssueTagRepo issueTagRepo;

    @Override
    public void create(IssueTagRequest issueTagRequest) {
        int count = issueTagRepo.countByModuleIdAndName(issueTagRequest.getModuleId(), issueTagRequest.getName());
        if (count != 0)
            throw new ServiceException("");

        count = issueTagRepo.countByModuleIdAndColor(issueTagRequest.getModuleId(), issueTagRequest.getColor());
        if (count != 0) {
            throw new ServiceException("");
        }

        IssueTag issueTag = new IssueTag();
        issueTag.setModuleId(issueTagRequest.getModuleId());
        issueTag.setName(issueTagRequest.getName());
        issueTag.setColor(issueTagRequest.getColor());
        issueTagRepo.save(issueTag);
    }

    @Override
    public List<IssueTagResponse> fetch(Long moduleId) {
        return issueTagRepo.findAll()
                .stream()
                .map(IssueTagResponse::from)
                .collect(Collectors.toList());
    }
}
