package xyz.luomu32.rdep.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import xyz.luomu32.rdep.common.exception.DefaultExceptionHandler;
import xyz.luomu32.rdep.common.web.SpringMvcExceptionHandler;
import xyz.luomu32.rdep.project.service.Analyzer;
import xyz.luomu32.rdep.project.service.impl.CombinationAnalyzer;
import xyz.luomu32.rdep.project.service.impl.JavaProjectAnalyzer;

import javax.servlet.MultipartConfigElement;

@Configuration
@EnableScheduling
@EnableAsync
@EnableJpaAuditing
@EnableFeignClients
@EnableCircuitBreaker
public class ProjectConfig {

    @Bean
    public Analyzer analyzer() {
        CombinationAnalyzer combinationAnalyzer = new CombinationAnalyzer();
        combinationAnalyzer.registerAnalyzer("java", new JavaProjectAnalyzer());
        return combinationAnalyzer;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(20));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(200));
        return factory.createMultipartConfig();
    }

    @Bean
    public DefaultExceptionHandler defaultExceptionHandler(@Autowired MessageSource messageSource) {
        return new DefaultExceptionHandler(messageSource);
    }

    @Bean
    public SpringMvcExceptionHandler springMvcExceptionHandler(@Autowired MessageSource messageSource) {
        return new SpringMvcExceptionHandler(messageSource);
    }

}
