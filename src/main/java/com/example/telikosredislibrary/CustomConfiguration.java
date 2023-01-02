package com.example.telikosredislibrary;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

    public CacheImpl cacheImpl(){
        return new CacheImpl();
    }
}
