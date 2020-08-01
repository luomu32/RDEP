package xyz.luomu32.rdep.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.project.pojo.ConfigCenterConfigRequest;
import xyz.luomu32.rdep.project.pojo.ConfigCenterConfigResponse;
import xyz.luomu32.rdep.project.pojo.ConfigRequest;
import xyz.luomu32.rdep.project.pojo.ConfigResponse;
import xyz.luomu32.rdep.project.service.ConfigCenterService;

import java.util.List;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}/config-center")
public class ConfigCenterController {

    @Autowired
    private ConfigCenterService configCenterService;

    @GetMapping
    public List<ConfigResponse> fetch(@PathVariable Long projectId,
                                      @PathVariable Long moduleId,
                                      @RequestParam(required = false) String profile) {
        return configCenterService.fetch(projectId, moduleId, profile);
    }

    @PostMapping
    public void createOrUpdate(@PathVariable Long projectId,
                               @PathVariable Long moduleId,
                               ConfigRequest configRequest) {
        configRequest.setProjectId(projectId);
        configRequest.setModuleId(moduleId);
        configCenterService.createOrUpdate(configRequest);
    }

    @DeleteMapping
    public void delete(@PathVariable Long projectId, @PathVariable Long moduleId, ConfigRequest configRequest) {
        configRequest.setProjectId(projectId);
        configRequest.setModuleId(moduleId);
        configCenterService.delete(configRequest);
    }


    @PutMapping("config")
    public void configConfigCenter(@PathVariable Long projectId,
                                   @PathVariable Long moduleId,
                                   ConfigCenterConfigRequest request) {
        request.setProjectId(projectId);
        request.setModuleId(moduleId);
        configCenterService.configConfigCenter(request);
    }

    @GetMapping("config")
    public ConfigCenterConfigResponse fetchConfigCenterConfig(@PathVariable Long moduleId) {
        return configCenterService.fetchConfigCenterConfig(moduleId);
    }
}
