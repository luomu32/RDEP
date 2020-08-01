package xyz.luomu32.rdep.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.luomu32.rdep.common.JacksonMapperBuilderCustomizer;
import xyz.luomu32.rdep.common.LocalDateFormatter;
import xyz.luomu32.rdep.common.LocalDateTimeFormatter;
import xyz.luomu32.rdep.common.jwt.JwtPrincipalArgumentResolver;
import xyz.luomu32.rdep.common.web.DateRange;
import xyz.luomu32.rdep.common.web.DateRangeCustomEditor;
import xyz.luomu32.rdep.common.web.DateRangeFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MessageSource messageSource;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtPrincipalArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
        registry.addFormatterForFieldType(Date.class, new DateFormatter("yyyy-MM-dd"));
        registry.addFormatterForFieldType(DateRange.class, new DateRangeFormatter());
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
