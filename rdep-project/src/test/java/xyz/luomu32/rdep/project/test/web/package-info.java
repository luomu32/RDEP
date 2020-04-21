package xyz.luomu32.rdep.project.test.web;

/**
 * 只测试controller，service采用mock方式注入
 *
 * @WebMvcTest. 扫描：@Controller、@ControllerAdvice、@JsonComponent、Converter、GenericConverter、Filter
 * HandlerInterceptor、WebMvcConfigurer、HandlerMethodArgumentResolver
 * 普通的@Component不会被扫描进容器
 * 如果需要额外的组件，配合使用@Import
 * <p>
 * <p>
 * .andDo(MockMvcResultHandlers.print());可以方便将响应信息打印到控制台上
 */