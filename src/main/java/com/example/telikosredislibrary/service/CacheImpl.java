package com.example.telikosredislibrary.service;

import com.example.telikosredislibrary.config.YamlPropertySourceFactory;
import com.example.telikosredislibrary.constant.Constants;
import com.example.telikosredislibrary.exception.CacheException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Service
@Slf4j
@ConfigurationProperties("cache")
@Setter
@PropertySource(value="classpath:redis.yml",factory = YamlPropertySourceFactory.class)
public class CacheImpl<K, V> implements ICache<K, V> {

    @Autowired
    ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;

    private int ttl;

    @Override
    public Mono<V> get(K key) {
        log.info("Trying to get from cache for this key {}", key);
        return (Mono<V>) reactiveRedisTemplate.opsForValue().get(key).doOnError(e -> {
            log.error("In library {}", e.getMessage());
            throw new CacheException(Constants.GET_CACHE_EXCEPTION);
        });
    }

    @Override
    public Mono<Boolean> put(K key, V value) {
        log.info("Writing to cache with key {}, value {}, default_ttl {}s", key, value, ttl);
        return reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl)).doOnError(e -> {
            log.error("In library {}", e.getMessage());
            throw new CacheException(Constants.PUT_CACHE_EXCEPTION);
        });
    }

    public Mono<Boolean> put(K key, V value, int entryTtl) {
        log.info("Writing to cache with key {}, value {}, entry level ttl {}s", key, value, entryTtl);
        return reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(entryTtl)).doOnError(e -> {
            log.error("In library {}", e.getMessage());
            throw new CacheException(Constants.PUT_CACHE_EXCEPTION);
        });
    }


}
