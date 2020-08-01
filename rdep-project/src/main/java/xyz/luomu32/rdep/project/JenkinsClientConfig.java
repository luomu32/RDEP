package xyz.luomu32.rdep.project;

import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.DefaultGzipDecoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import xyz.luomu32.rdep.project.service.JenkinsService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Lazy
    public RequestInterceptor cookieInterceptor() {
        JenkinsCrumbInterceptor interceptor = new JenkinsCrumbInterceptor();
        return interceptor;
    }

//    @Bean
    public Feign.Builder jenkinsBuildResponseDecoder(@Autowired Decoder decoder) {
        return Feign.builder().mapAndDecode(new ResponseMapper() {
            private final Pattern pattern = Pattern.compile("^.*/queue/item/(\\d+)/$");

            @Override
            public Response map(Response response, Type type) {
                if (type.getTypeName().equals(JenkinsService.BuildResponse.class.getTypeName())) {
                    Integer queueId = null;
                    Optional<String> location = response.headers().get("Location").stream().findFirst();
                    if (location.isPresent()) {
                        Matcher matcher = pattern.matcher(location.get());
                        if (matcher.find() && matcher.groupCount() == 1) {
                            queueId = Integer.valueOf(matcher.group(1));
                        }
                    }
                    return response.toBuilder()
                            .headers(response.headers())
                            .status(response.status())
                            .reason(response.reason())
                            .request(response.request())
                            .body(("{\"queueId\":\"" + queueId + "\"}").getBytes())
                            .build();
                }
                return response;
            }
        }, decoder);
//        return new BuildResponseDecoder(decoder);
    }

    static class BuildResponseDecoder implements Decoder {
        private static final Pattern pattern = Pattern.compile("^.*/queue/item/(\\d+)/$");

        private final Decoder decoder;

        BuildResponseDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            System.out.println(type);
            System.out.println(JenkinsService.BuildResponse.class.getTypeName().equals(type.getTypeName()));
            if (!JenkinsService.BuildResponse.class.getTypeName().equals(type.getTypeName())) {
                return decoder.decode(response, type);
            }
            JenkinsService.BuildResponse buildResponse = new JenkinsService.BuildResponse();
            Optional<String> location = response.headers().get("Location").stream().findFirst();
            if (location.isPresent()) {
                Matcher matcher = pattern.matcher(location.get());
                if (matcher.find() && matcher.groupCount() == 1) {
                    buildResponse.setQueueId(Integer.valueOf(matcher.group(1)));
                }
            }
            return buildResponse;
        }
    }

    /**
     * Jenkins默认开启CSRF过滤，请求头需要带上crumb参数
     * 请求crumb时，除了返回crumb内容，还会返回一个cookie，crumb-cookie绑定
     * 请求时缺少cookie或与crumb不对应，会报错
     */
    static class JenkinsCrumbInterceptor implements RequestInterceptor, ApplicationContextAware, InitializingBean {
        private String cookie;
        private String crumb;
        private ApplicationContext applicationContext;
        private JenkinsService jenkinsService;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            this.jenkinsService = this.applicationContext.getBean(JenkinsService.class);
            if (null == this.jenkinsService)
                throw new IllegalStateException("must have Jenkins Service In Container");
        }

        @Override
        public void apply(RequestTemplate template) {
            if (template.url().equals("/crumbIssuer/api/json")) {
                return;
            }
            if (null == crumb) {
//                if (null == this.jenkinsService)
//                    this.jenkinsService = this.applicationContext.getBean(JenkinsService.class);
                if (null == this.jenkinsService)
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
