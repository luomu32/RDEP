package xyz.luomu32.rdep.project;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import xyz.luomu32.rdep.project.service.JenkinsService;

public class JenkinsClientConfig {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("root", "123456");
    }

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor cookieInterceptor() {
        JenkinsCrumbInterceptor interceptor = new JenkinsCrumbInterceptor();
        return interceptor;
    }

    /**
     * Jenkins默认开启CSRF过滤，请求头需要带上crumb参数
     * 请求crumb时，除了返回crumb内容，还会返回一个cookie，crumb-cookie绑定
     * 请求时缺少cookie或与crumb不对应，会报错
     */
    static class JenkinsCrumbInterceptor implements RequestInterceptor, ApplicationContextAware {
        private String cookie;
        private String crumb;
        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void apply(RequestTemplate template) {
            if (template.url().equals("/crumbIssuer/api/json")) {
                return;
            }
            if (null == crumb) {
                JenkinsService jenkinsService = this.applicationContext.getBean(JenkinsService.class);
                if (null == jenkinsService)
                    throw new IllegalStateException("must have Jenkins Service In Container");
                ResponseEntity<JenkinsService.CrumbResponse> crumbResponseResponseEntity = jenkinsService.fetchCrumb();
                if (crumbResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
                    this.cookie = crumbResponseResponseEntity.getHeaders().getFirst("set-cookie").split(";")[0];
                    this.crumb = crumbResponseResponseEntity.getBody().getCrumb();
                }
            }
            template.header("Cookie", cookie)
                    .header("Jenkins-Crumb", crumb);
        }
    }
}
