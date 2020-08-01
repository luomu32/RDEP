package xyz.luomu32.rdep.middleware.service;

import xyz.luomu32.rdep.middleware.pojo.MiddlewareRequest;
import xyz.luomu32.rdep.middleware.pojo.MiddlewareResponse;

import java.util.List;
import java.util.Optional;

public interface MiddlewareService {

    void create(MiddlewareRequest middlewareRequest);

    List<MiddlewareResponse> fetch(Optional<Long> categoryId);
}
