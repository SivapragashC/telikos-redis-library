package com.example.telikosredislibrary;

import lombok.Data;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConfigurationProperties(prefix = "redison-cache")
@Data
@ConditionalOnProperty(name = "enableCache", havingValue = "true")
@Getter
public class RedisConfig {

    @Value("${redison-cache.url}")
    private String url;

    @Bean()
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress(url);
        return Redisson.create(config);
    }

    @Bean("reactiveRedisConnectionFactory")
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new RedissonConnectionFactory(redisson());
    }

    @Bean(name = "reactiveRedisTemplate")
    public ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext.RedisSerializationContextBuilder<Object, Object> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<Object, Object> context = builder.value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }



}
