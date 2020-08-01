package xyz.luomu32.rdep.middleware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.middleware.pojo.MiddlewareRequest;
import xyz.luomu32.rdep.middleware.pojo.MiddlewareResponse;
import xyz.luomu32.rdep.middleware.service.MiddlewareService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("middleware")
public class MiddlewareController {

    @Autowired
    private MiddlewareService middlewareService;

    @PostMapping
    public void create(MiddlewareRequest middlewareRequest) {
        middlewareService.create(middlewareRequest);
    }

    @GetMapping
    public List<MiddlewareResponse> fetch(@RequestParam Optional<Long> categoryId) {
        return middlewareService.fetch(categoryId);
    }
}
