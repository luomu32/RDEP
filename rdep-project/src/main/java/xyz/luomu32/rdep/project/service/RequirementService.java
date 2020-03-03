package xyz.luomu32.rdep.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.luomu32.rdep.project.pojo.RequirementRequest;
import xyz.luomu32.rdep.project.pojo.RequirementResponse;

public interface RequirementService {

    Page<RequirementResponse> fetch(RequirementRequest request, Pageable pageable);

    void create(Long projectId, RequirementRequest request);

    void delete(Long id);
}
