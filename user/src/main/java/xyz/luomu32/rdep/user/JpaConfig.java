package xyz.luomu32.rdep.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    @Bean
    public AuditorAware<Long> auditorProvider(WebRequest request) {
        return () -> {
            String userId = request.getHeader("x-user-id");
            return Optional.ofNullable(userId == null ? null : Long.parseLong(userId));
        };
    }

}


