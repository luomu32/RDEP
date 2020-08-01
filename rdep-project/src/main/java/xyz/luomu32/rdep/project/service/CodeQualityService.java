package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.pojo.CheckstyleResult;

public interface CodeQualityService {

    void findBugsCheck(Long projectId, Long moduleId,String branch);

    CheckstyleResult checkstyleCheck(Long projectId, Long moduleId, String branch, String style);

    void pmdCheck(Long projectId, Long moduleId, String branch, String style);
}
