package xyz.luomu32.rdep.project.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.luomu32.rdep.common.Principal;
import xyz.luomu32.rdep.project.pojo.WorkLogRequest;
import xyz.luomu32.rdep.project.pojo.WorkLogResponse;


public interface WorkLogService {

    void create(WorkLogRequest request);

    void update(Long id, WorkLogRequest request, Principal principal);

    void delete(Long id, Principal principal);

    WorkLogResponse fetch(Long id, Principal principal);

    Page<WorkLogResponse> fetch(Principal principal, Pageable pageable);

}
