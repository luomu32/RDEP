package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.common.Principal;
import xyz.luomu32.rdep.common.jwt.JwtPrincipal;
import xyz.luomu32.rdep.project.pojo.WorkLogRequest;
import xyz.luomu32.rdep.project.pojo.WorkLogResponse;
import xyz.luomu32.rdep.project.service.WorkLogService;

@RestController
@RequestMapping("work-logs")
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @PostMapping
    public void create(@Validated WorkLogRequest request) {
        workLogService.create(request);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id,
                       WorkLogRequest request,
                       @JwtPrincipal Principal principal) {
        workLogService.update(id, request, principal);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id,
                       @JwtPrincipal Principal principal) {
        workLogService.delete(id, principal);
    }

    @GetMapping("{id}")
    public WorkLogResponse fetch(@PathVariable Long id,
                                 @JwtPrincipal Principal principal) {
        return workLogService.fetch(id, principal);
    }

    @GetMapping
    public Page<WorkLogResponse> fetch(@JwtPrincipal Principal principal,
                                       Pageable pageable) {
        return workLogService.fetch(principal, pageable);
    }
}
