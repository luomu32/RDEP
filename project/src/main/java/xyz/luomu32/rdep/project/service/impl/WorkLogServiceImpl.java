package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.Principal;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.WorkLog;
import xyz.luomu32.rdep.project.pojo.WorkLogRequest;
import xyz.luomu32.rdep.project.pojo.WorkLogResponse;
import xyz.luomu32.rdep.project.repo.WorkLogRepo;
import xyz.luomu32.rdep.project.service.WorkLogService;

@Service
public class WorkLogServiceImpl implements WorkLogService {

    @Autowired
    private WorkLogRepo workLogRepo;

    @Override
    public void create(WorkLogRequest request) {
        WorkLog workLog = new WorkLog();
        workLog.setTitle(request.getTitle());
        workLog.setContent(request.getContent());
        workLogRepo.save(workLog);
    }

    @Override
    public void update(Long id, WorkLogRequest request, Principal principal) {
        WorkLog workLog = workLogRepo.findById(id).orElseThrow(() -> new ServiceException("work.log.not.found"));

        if (!workLog.getCreateBy().equals(principal.getId())) {
            throw new ServiceException("");
        }

        workLog.setTitle(request.getTitle());
        workLog.setContent(request.getContent());
        workLogRepo.save(workLog);
    }

    @Override
    public void delete(Long id, Principal principal) {
        WorkLog workLog = workLogRepo.findById(id).orElseThrow(() -> new ServiceException("work.log.not.found"));

        if (!workLog.getCreateBy().equals(principal.getId())) {
            throw new ServiceException("");
        }

        workLogRepo.delete(workLog);
    }

    @Override
    public WorkLogResponse fetch(Long id, Principal principal) {
        WorkLog workLog = workLogRepo.findById(id).orElseThrow(() -> new ServiceException("work.log.not.found"));

        if (!workLog.getCreateBy().equals(principal.getId())) {
            throw new ServiceException("");
        }

        return WorkLogResponse.from(workLog);
    }

    @Override
    public Page<WorkLogResponse> fetch(Principal principal, Pageable pageable) {
        return workLogRepo.findByCreateBy(principal.getId(), pageable)
                .map(WorkLogResponse::from);
    }
}
