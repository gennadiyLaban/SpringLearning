package org.laban.learning.spring.lesson5.configuration;

import org.laban.learning.spring.lesson5.properties.AppCacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfiguration {
    @Bean
    public CacheManager redisCacheManager(
            AppCacheProperties cacheProperties,
            LettuceConnectionFactory lettuceConnectionFactory
    ) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheProperties.getCaches().forEach((cacheName, properties) -> {
            cacheConfigurations.put(
                    cacheName,
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(properties.getExpire())
            );
        });

        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

}
