package com.example.telikosredislibrary;

import org.springframework.stereotype.Indexed;
import reactor.core.publisher.Mono;

@Indexed
public interface ICache<K, V> {
    Mono<V> get(K key);

    Mono<Boolean> put(K key, V value);

    Mono<Boolean> put(K key, V value, int entryTtl);
}
