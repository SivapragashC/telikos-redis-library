package com.example.telikosredislibrary.config;

import com.example.telikosredislibrary.service.CacheService;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

    public CacheService cacheService(){
        return new CacheService();
    }
}
