package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.RequirementRequest;
import xyz.luomu32.rdep.project.pojo.RequirementResponse;
import xyz.luomu32.rdep.project.service.RequirementService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("projects/{projectId}/requirements")
public class RequirementController {

    @Autowired
    private RequirementService requirementService;

    @GetMapping
    public Page<RequirementResponse> fetch(
            @PathVariable Long projectId,
            RequirementRequest request,
            Pageable pageable) {

        request.setProjectId(projectId);
        return requirementService.fetch(request, pageable);
    }

    @GetMapping("board")
    public List<RequirementResponse> board() {
        List<RequirementResponse> responses = new ArrayList<>();
        RequirementResponse response1 = new RequirementResponse();
        response1.setTitle("1223094823");
        responses.add(response1);
        RequirementResponse response2 = new RequirementResponse();
        response2.setTitle("1223094823");
        responses.add(response2);
        RequirementResponse response3 = new RequirementResponse();
        response3.setTitle("1223094823");
        responses.add(response3);
        RequirementResponse response4 = new RequirementResponse();
        response4.setTitle("1223094823");
        responses.add(response4);
        RequirementResponse response5 = new RequirementResponse();
        response5.setTitle("1223094823");
        responses.add(response5);

        return responses;
    }

    @PostMapping
    public void create(@PathVariable Long projectId, RequirementRequest request) {
        requirementService.create(projectId, request);
    }

    @PutMapping
    public void update() {

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        requirementService.delete(id);
    }
}
