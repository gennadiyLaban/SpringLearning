package org.laban.learning.spring.lesson5.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {
    private final Map<String, CacheProperties> caches = new HashMap<>();

    @Data
    public static class CacheProperties {
        private Duration expire = Duration.ZERO;
    }

    public List<String> cacheNames() {
        return caches.keySet().stream().toList();
    }

    public interface CacheNames {
        String FIND_ALL = "findAllCache";
    }
}
