package xyz.luomu32.rdep.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.project.entity.ModuleApiConfig;
import xyz.luomu32.rdep.project.repo.ModuleApiConfigRepo;
import xyz.luomu32.rdep.project.service.ModuleApiService;

@Service
public class ModuleApiServiceImpl implements ModuleApiService {

    @Autowired
    private ModuleApiConfigRepo moduleApiConfigRepo;

    @Override
    public void fetch(Long projectId, Long moduleId) {

        ModuleApiConfig apiConfig = moduleApiConfigRepo.findByModuleId(moduleId);
        if (null == apiConfig) {
            return;
        }

        if (apiConfig.getServiceProvider().equals("swagger")) {
//            OpenAPI api = new OpenAPIV3Parser().read
        }
    }
}
