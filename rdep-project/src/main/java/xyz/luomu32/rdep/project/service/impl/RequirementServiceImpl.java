package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.project.entity.Requirement;
import xyz.luomu32.rdep.project.entity.RequirementStatus;
import xyz.luomu32.rdep.project.pojo.RequirementRequest;
import xyz.luomu32.rdep.project.pojo.RequirementResponse;
import xyz.luomu32.rdep.project.repo.RequirementRepo;
import xyz.luomu32.rdep.project.service.RequirementService;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    private RequirementRepo requirementRepo;

    @Override
    public Page<RequirementResponse> fetch(RequirementRequest request, Pageable pageable) {

        return requirementRepo.findAll((
                (Specification<Requirement>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("projectId"), request.getProjectId()))
                .and((Specification<Requirement>) (root, query, criteriaBuilder) -> {
                    if (null == request.getStatus())
                        return null;

                    return criteriaBuilder.equal(root.get("status"), request.getStatus());
                })
                .and((Specification<Requirement>) (root, query, criteriaBuilder) -> {
                    if (null == request.getLevel())
                        return null;
                    return criteriaBuilder.equal(root.get("level"), request.getLevel());
                })
                .and((Specification<Requirement>) (root, query, criteriaBuilder) -> {
                    if (request.getOwnerId() == null)
                        return null;
                    return criteriaBuilder.equal(root.get("ownerId"), request.getOwnerId());
                }), pageable)
                .map(RequirementResponse::from);
    }

    @Override
    public void create(Long projectId, RequirementRequest request) {
        Requirement requirement = new Requirement();
        requirement.setTitle(request.getTitle());
        requirement.setStatus(RequirementStatus.NEW);
        requirement.setContent(request.getContent());
        requirement.setProjectId(projectId);
        requirement.setLevel(request.getLevel());
        requirement.setOwenId(request.getOwnerId());
        requirement.setOwenName(request.getOwnerName());
        requirement.setDeadline(request.getDeadline());

        requirementRepo.save(requirement);
    }

    @Override
    public void delete(Long id) {
        requirementRepo.deleteById(id);
    }
}
