package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.pojo.IssueTagRequest;
import xyz.luomu32.rdep.project.pojo.IssueTagResponse;

import java.util.List;

public interface IssueTagService {

    void create(IssueTagRequest issueTagRequest);

    List<IssueTagResponse> fetch(Long moduleId);
}
