package xyz.luomu32.rdep.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.luomu32.rdep.common.JacksonMapperBuilderCustomizer;
import xyz.luomu32.rdep.common.LocalDateFormatter;
import xyz.luomu32.rdep.common.LocalDateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private MessageSource messageSource;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return new JacksonMapperBuilderCustomizer();
    }
}
