package xyz.luomu32.rdep.common.jwt;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import xyz.luomu32.rdep.common.Principal;

import java.util.HashMap;
import java.util.Map;

public class JwtPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();

        return methodParameter.hasParameterAnnotation(JwtPrincipal.class)
                && (parameterType.isAssignableFrom(Map.class)
                || parameterType.equals(Principal.class));
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        Class<?> parameterType = methodParameter.getParameterType();

        String id = nativeWebRequest.getHeader("x-user-id");
        String username = nativeWebRequest.getHeader("x-user-username");
        String realName = nativeWebRequest.getHeader("x-user-real-name");

        if (parameterType.isAssignableFrom(Map.class)) {
            Map<String, Object> principal = new HashMap<>();
            principal.put("id", id);
            principal.put("username", username);
            principal.put("realName", realName);
            return principal;
        } else if (parameterType.equals(Principal.class)) {
            Principal principal = new Principal();
            principal.setId(Long.valueOf(id));
            principal.setUsername(username);
            principal.setRealName(realName);
            return principal;
        }

        throw new UnsupportedOperationException();
    }
}
