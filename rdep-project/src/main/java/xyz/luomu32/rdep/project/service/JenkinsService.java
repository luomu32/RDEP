package xyz.luomu32.rdep.project.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.luomu32.rdep.project.JenkinsClientConfig;

import java.util.List;
import java.util.Map;

@FeignClient(name = "jenkins", url = "${jenkins.host}", configuration = JenkinsClientConfig.class)
public interface JenkinsService {

    @GetMapping("crumbIssuer/api/json")
    ResponseEntity<CrumbResponse> fetchCrumb();

    @PostMapping("job/{name}/build")
    ResponseEntity<Void> createBuild(@PathVariable String name,
                                     @RequestParam String token);

    @PostMapping("{folder}/job/{name}/build")
    ResponseEntity<Void> createBuild(@PathVariable String folder,
                                     @PathVariable String name,
                                     @RequestParam String token);

    @PostMapping("job/{name}/buildWithParameters")
    ResponseEntity<Void> createBuildWithParameters(@PathVariable String name,
                                                   @RequestParam String token,
                                                   Map<String, List<String>> properties);

    @PostMapping("{folder}/job/{name}/buildWithParameters")
    ResponseEntity<Void> createBuildWithParameters(@PathVariable String folder,
                                                   @PathVariable String name,
                                                   @RequestParam String token,
                                                   Map<String, List<String>> properties);

    @GetMapping("job/{name}/{number}/api/json")
    ResponseEntity<BuildInfo> fetchBuildInfo(@PathVariable String name,
                                             @PathVariable int number);

    @GetMapping("{folder}/job/{name}/{number}/api/json")
    ResponseEntity<BuildInfo> fetchBuildInfo(@PathVariable String folder,
                                             @PathVariable String name,
                                             @PathVariable int number);

    /**
     * 获取构建的控制台输出内容
     *
     * @param name   任务名称
     * @param number 构建次数。数字或者'lastBuild'
     * @return
     */
    @GetMapping("job/{name}/{number}/logText/progressiveText")
    ResponseEntity<String> fetchBuildConsoleText(@PathVariable String name,
                                                 @PathVariable String number);

    @Getter
    @Setter
    static class CrumbResponse {
        private String crumb;
    }

    @Deprecated
    @Getter
    @Setter
    static class BuildResponse {
        //jenkins build number
        private Integer queueId;
    }

    @Data
    class BuildInfo {
        private boolean building;
        private String id;
        private int number;
        private int queueId;
        private String result;
        private long timestamp;
        private String url;
        private long duration;
    }
}
