package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.ProjectPeopleCreateCmd;
import xyz.luomu32.rdep.project.pojo.ProjectPeopleResponse;
import xyz.luomu32.rdep.project.service.ProjectPeopleService;

import java.util.List;

@RestController
@RequestMapping("projects/{projectId}/people")
public class ProjectPeopleController {

    @Autowired
    private ProjectPeopleService projectPeopleService;

    @GetMapping
    public List<ProjectPeopleResponse> fetch(@PathVariable Long projectId) {
        return projectPeopleService.fetch(projectId);
    }

    @PostMapping
    public void create(
            @PathVariable Long projectId,
            @Validated @RequestBody ProjectPeopleCreateCmd createCmd) {
        createCmd.setProjectId(projectId);
        projectPeopleService.create(createCmd);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        //maybe 需要check，当前登录用户的ID是否是project的负责人id，不然无法随便删除
        //或许需要再复杂一点，基于角色的管理。project的manager角色才能删除project的参与人
    }
}
