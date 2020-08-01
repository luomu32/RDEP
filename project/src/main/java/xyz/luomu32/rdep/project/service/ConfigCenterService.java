package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.pojo.ConfigCenterConfigRequest;
import xyz.luomu32.rdep.project.pojo.ConfigCenterConfigResponse;
import xyz.luomu32.rdep.project.pojo.ConfigRequest;
import xyz.luomu32.rdep.project.pojo.ConfigResponse;

import java.util.List;

public interface ConfigCenterService {

    List<ConfigResponse> fetch(Long projectId, Long moduleId, String profile);

    void createOrUpdate(ConfigRequest configRequest);

    void delete(ConfigRequest configRequest);

    void configConfigCenter(ConfigCenterConfigRequest request);

    ConfigCenterConfigResponse fetchConfigCenterConfig(Long moduleId);
}
