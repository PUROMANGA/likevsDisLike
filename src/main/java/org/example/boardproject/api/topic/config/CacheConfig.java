package org.example.boardproject.api.topic.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.example.boardproject.api.topic.entity.Topic;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCacheManager  caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("topicRanking");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(62, TimeUnit.MINUTES)
                .maximumSize(2);
    }
}
