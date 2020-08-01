package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.luomu32.rdep.project.pojo.MiddlewareRequest;
import xyz.luomu32.rdep.project.service.MiddlewareService;

@RestController
@RequestMapping("/projects/{projectId}/modules/{moduleId}/middlewares")
public class MiddlewareController {

    @Autowired
    private MiddlewareService middlewareService;

    @PostMapping
    public void create(MiddlewareRequest middlewareRequest) {
        middlewareService.create(middlewareRequest);
    }
}
