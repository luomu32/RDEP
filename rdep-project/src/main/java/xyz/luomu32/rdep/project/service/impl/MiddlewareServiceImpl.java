package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.project.model.Middleware;
import xyz.luomu32.rdep.project.pojo.MiddlewareRequest;
import xyz.luomu32.rdep.project.repo.MiddlewareRepo;
import xyz.luomu32.rdep.project.service.DockerService;
import xyz.luomu32.rdep.project.service.MiddlewareService;

@Service
public class MiddlewareServiceImpl implements MiddlewareService {
    @Autowired
    private MiddlewareRepo middlewareRepo;

    @Autowired
    private DockerService dockerService;

    @Override
    public void create(MiddlewareRequest middlewareRequest) {
        //TODO check docker registry中是否有指定的镜像

        //构造run命令，比如Spring Cloud Config镜像，根据middleRequest配置，运行设置不同的参数，比如是否开启加密等

        //如果没有需要从远程下载
        //从基础服务中拉起指定实例个数的机器，
        //通知各个机器上的代理机器人，使其从镜像仓库中获取指定的镜像，并运行起来。


        //TODO 配置中心，Spring Cloud Config，可以设置是否启用加密，外部GIT URI地址（不指定在本地创建git目录）
        Middleware middleware = new Middleware();
        middleware.setName(middlewareRequest.getName());
        middleware.setVersion(middlewareRequest.getVersion());
        middleware.setProjectId(middlewareRequest.getProjectId());
        middleware.setModuleId(middlewareRequest.getModuleId());


        middlewareRepo.save(middleware);
    }
}
