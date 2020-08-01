package xyz.luomu32.rdep.project.service;

import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "users")
public interface UserService {

    @GetMapping("{id}")
    User fetch(Long id);

    @Value
    public static class User {
        private Long id;
        private String username;
    }

}
