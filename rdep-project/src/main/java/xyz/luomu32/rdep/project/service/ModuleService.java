package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.pojo.*;

import java.util.List;

public interface ModuleService {

    void create(ModuleRequest moduleRequest);

    void analyze(Long projectId, Long moduleId);

    List<ModuleResponse> fetch(Long projectId);

    ModuleResponse fetch(Long projectId, Long moduleId);

    List<ModuleDubboServiceProviderResponse> fetchDubboServiceProvider(Long projectId, Long moduleId, String serviceId);

    List<ModuleDubboServiceConsumerResponse> fetchDubboServiceConsumer(Long projectId, Long moduleId, String serviceId);

    List<ModuleDubboServiceResponse> fetchDubboServices(Long projectId, Long moduleId);

    /**
     * @see BuildService。Build
     */
    @Deprecated
    void build(Long projectId, Long moduleId);
}
