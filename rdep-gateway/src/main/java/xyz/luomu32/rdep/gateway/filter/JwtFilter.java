package xyz.luomu32.rdep.gateway.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.javafx.collections.MappingChange;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtFilter implements GlobalFilter, Ordered {

    private final String authPath;
    private final SecretKey key;

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public JwtFilter(String authPath, SecretKey key) {
        this.authPath = authPath;
        this.key = key;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();

        if (exchange.getRequest().getMethodValue().equalsIgnoreCase("GET") &&
                (path.endsWith(".png") || path.endsWith(".gif")) || path.endsWith("jpg")) {
            return chain.filter(exchange);
        }

        if (path.equals(authPath)) {
            //对下游服务返回的User对象，生成Token返回给客户端
            return chain.filter(exchange.mutate().response(decorate(exchange)).build());
        }

        if (!exchange.getRequest().getHeaders().containsKey("x-jwt-token")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = exchange.getRequest().getHeaders().getFirst("x-jwt-token");

        if (StringUtils.isEmpty(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            Map<String, Object> user = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            String username = user.get("username").toString();
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("x-user-id", user.get("id").toString())
                    .header("x-user-username", username)
                    .header("x-user-real-name", user.get("realName").toString())
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        } catch (JwtException e) {
//            log.info("Login token not valid or expired,{}", e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private ServerHttpResponse decorate(ServerWebExchange exchange) {
        return new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                String originalResponseContentType = exchange
//                        .getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
//                HttpHeaders httpHeaders = new HttpHeaders();
//                // explicitly add it in this way instead of
//                // 'httpHeaders.setContentType(originalResponseContentType)'
//                // this will prevent exception in case of using non-standard media
//                // types like "Content-Type: image"
//                httpHeaders.add(HttpHeaders.CONTENT_TYPE,
//                        originalResponseContentType);
//
//                ClientResponse clientResponse = ClientResponse
//                        .create(exchange.getResponse().getStatusCode())
//                        .headers(headers -> headers.putAll(httpHeaders))
//                        .body(Flux.from(body)).build();
//
//                // TODO: flux or mono
//                Mono modifiedBody = clientResponse.bodyToMono(User.class);
//
//                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody,
//                        String.class);
//                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
//                        exchange, exchange.getResponse().getHeaders());
//                return bodyInserter.insert(outputMessage, new BodyInserterContext())
//                        .then(Mono.defer(() -> {
//                            Flux<DataBuffer> messageBody = outputMessage.getBody();
//                            HttpHeaders headers = getDelegate().getHeaders();
//                            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
//                                messageBody = messageBody.doOnNext(data -> headers
//                                        .setContentLength(data.readableByteCount()));
//                            }
//                            // TODO: fail if isStreamingMediaType?
//                            return getDelegate().writeWith(messageBody);
//                        }));

                if (exchange.getResponse().getStatusCode().is5xxServerError())
                    return super.writeWith(body);

                return super.writeWith(Flux.from(body).map(dataBuffer -> {
                    byte[] content = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(content);
                    try {
                        Object result;

                        JsonNode node = MAPPER.readTree(content);
                        if (node.has("message")) {
                            ErrorResponse response = new ErrorResponse();
                            response.setCode(node.get("code").getTextValue());
                            response.setMessage(node.get("message").getTextValue());
                            result = response;
                        } else {
                            User user = MAPPER.readValue(content, User.class);
                            String token = generatorToken(user.getId(), user.getUsername(), user.getRealName());
                            user.setToken(token);
                            result = user;
                        }

                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);

                        return exchange.getResponse().bufferFactory().wrap(MAPPER.writeValueAsBytes(result));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return dataBuffer;
                    }
                }));
            }
        };
    }

    private String generatorToken(Long id, String username, String realName) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("username", username);
        userInfo.put("realName", realName);
        return Jwts.builder()
                .setClaims(userInfo)
                .setExpiration(Date.from(OffsetDateTime.now().plusHours(12).toInstant()))
                .signWith(key)
                .compact();
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private Long id;
        private String username;
        private String realName;

        private String email;

        private String token;

        private Role role;
    }

    @Data
    public static class Role {
        private Long id;
        private String name;
    }

    @Data
    public static class ErrorResponse {
        private String code;

        private String message;
    }
}
