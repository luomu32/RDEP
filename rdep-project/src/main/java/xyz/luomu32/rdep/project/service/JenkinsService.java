package xyz.luomu32.rdep.project.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.luomu32.rdep.project.JenkinsClientConfig;

@FeignClient(name = "jenkins", url = "${jenkins.host}", configuration = JenkinsClientConfig.class)
public interface JenkinsService {

    @GetMapping("crumbIssuer/api/json")
    ResponseEntity<CrumbResponse> fetchCrumb();

    @PostMapping(value = "job/{name}/build")
    ResponseEntity<Void> createBuild(@PathVariable String name,
                                 @RequestParam String token);


    @Getter
    @Setter
    static class CrumbResponse {
        private String crumb;
    }
}
