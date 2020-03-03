package xyz.luomu32.rdep.middleware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.middleware.dao.MiddlewareDao;
import xyz.luomu32.rdep.middleware.entity.Middleware;
import xyz.luomu32.rdep.middleware.pojo.MiddlewareRequest;
import xyz.luomu32.rdep.middleware.pojo.MiddlewareResponse;
import xyz.luomu32.rdep.middleware.service.MiddlewareService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiddlewareServiceImpl implements MiddlewareService {
    @Autowired
    private MiddlewareDao middlewareDao;

    @Override
    public void create(MiddlewareRequest middlewareRequest) {
        Middleware middleware = new Middleware();
        middleware.setName(middlewareRequest.getName());
        middleware.setVersion(middlewareRequest.getVersion());
        middleware.setCategoryId(middlewareRequest.getCategory());
        middlewareDao.save(middleware);
    }

    @Override
    public List<MiddlewareResponse> fetch(Optional<Long> categoryId) {
        return middlewareDao.findAll((Specification<Middleware>) (root, query, criteriaBuilder) -> {
            if (categoryId.isPresent())
                return criteriaBuilder.equal(root.get("categoryId"), categoryId.get());
            else return null;
        }).stream().map(m -> {
            MiddlewareResponse middlewareResponse = new MiddlewareResponse();
            middlewareResponse.setId(m.getId());
            middlewareResponse.setName(m.getName());
            middlewareResponse.setVersion(m.getVersion());
            middlewareResponse.setCategoryId(m.getCategoryId());
            return middlewareResponse;
        }).collect(Collectors.toList());
//        return middlewareDao.findByCategoryId(categoryId).stream().map(m -> {
//            MiddlewareResponse middlewareResponse = new MiddlewareResponse();
//            middlewareResponse.setId(m.getId());
//            middlewareResponse.setName(m.getName());
//            middlewareResponse.setVersion(m.getVersion());
//            middlewareResponse.setCategoryId(m.getCategoryId());
//            return middlewareResponse;
//        }).collect(Collectors.toList());
    }
}
