package xyz.luomu32.rdep.project;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.luomu32.rdep.common.JacksonMapperBuilderCustomizer;
import xyz.luomu32.rdep.common.LocalDateFormatter;
import xyz.luomu32.rdep.common.LocalDateTimeFormatter;
import xyz.luomu32.rdep.common.jwt.JwtPrincipalArgumentResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtPrincipalArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return new JacksonMapperBuilderCustomizer();
    }

}
