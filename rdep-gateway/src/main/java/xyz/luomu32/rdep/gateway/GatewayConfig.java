package xyz.luomu32.rdep.gateway;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import xyz.luomu32.rdep.gateway.filter.IpKeyResolver;
import xyz.luomu32.rdep.gateway.filter.JwtFilter;

import javax.crypto.SecretKey;
import java.io.File;

@Slf4j
@Configuration
public class GatewayConfig {

    @Bean(name = "ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return new IpKeyResolver();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public GlobalFilter jwtFilter(@Autowired SecretKey key) {
        return new JwtFilter("/users/auth", key);
    }

    @Bean
    public SecretKey jwtKey(@Autowired RedisTemplate redisTemplate) {
        SecretKey key;
        Object oriKey = redisTemplate.opsForValue().get("rdep_jwt_key");
        if (null == oriKey) {
            log.info("jwt key not found,generate new one");
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            redisTemplate.opsForValue().set("rdep_jwt_key", key.getEncoded());
        } else {
            key = Keys.hmacShaKeyFor((byte[]) oriKey);
        }
        return key;
    }
}
