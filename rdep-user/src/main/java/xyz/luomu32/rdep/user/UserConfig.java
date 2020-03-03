package xyz.luomu32.rdep.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.luomu32.rdep.common.exception.DefaultExceptionHandler;
import xyz.luomu32.rdep.common.web.SpringMvcExceptionHandler;

@Configuration
public class UserConfig {

    @Bean
    public SpringMvcExceptionHandler springMvcExceptionHandler(@Autowired MessageSource messageSource) {
        return new SpringMvcExceptionHandler(messageSource);
    }

    @Bean
    public DefaultExceptionHandler defaultExceptionHandler(@Autowired MessageSource messageSource) {
        return new DefaultExceptionHandler(messageSource);
    }
}
