package com.example.telikosredislibrary.config;

import com.example.telikosredislibrary.service.CacheImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

    public CacheImpl cacheImpl(){
        return new CacheImpl();
    }
}
