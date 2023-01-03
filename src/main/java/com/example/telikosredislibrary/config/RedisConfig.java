package com.example.telikosredislibrary.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConfigurationProperties(prefix = "redison-cache")
@ConditionalOnProperty(name = "enableCache", havingValue = "true")
public class RedisConfig {

    @Autowired
    Environment env;

    @Bean()
    RedissonClient redisson() {
        Config config = new Config();
        String redisProtocol = Boolean.parseBoolean(env.getProperty("redis.ssl")) ? "redis://" : "rediss://";
        config.useSingleServer().setAddress(redisProtocol + env.getProperty("redis.host")  +  ":" +   Integer.parseInt(env.getProperty("redis.port")));
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
