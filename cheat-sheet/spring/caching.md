### Caching
* `org.springframework.boot:spring-boot-starter-cache` - стартер для подключения кэша
* `@EnableCaching` - говорит `spring` включить кэширование, должна быть над конфигурационным классом (`@Configuration`)
* `@CacheConfig(cacheManager = "fooCacheManager")` - над классом `@Service` указывает, что нужно использовать
  been `CacheManager` с именем `fooCacheManager`
* `@Cacheable` - включает кэш метода `@Service`
    - `@Cacheable(value = "fooCache")` - задаёт имя кэша
    - `@Cacheable(key = "#id")` - задаёт значения ключа, по которому в кэше будет храниться кэшируемое значение, в данном
      случае значением будет значение аргумента `id`, переданным в метод (например в метод `findById(UUID id)`).
    - `@Cacheable(value = "myCache", key = "#field1 + #field2")` - задаёт значение ключа как комбинацию значений `field1`
      и `field2`
* `@CacheEvict` - очищает кэш при использовании метода `@Service`
    - `@CacheEvict(value = "fooCache")` - имя очищаемого кэша
    - `@CacheEvict(allEntries = true)` - говорит, что из кэша нужно очистить все записи, а не одну, соответствующую
      параметру вызываемого метода
    - `@CacheEvict(key = "#id")` - задаёт значения ключа, по которому нужно очистить закэшированное значение, в данном
      случае значением будет значение аргумента `id`, переданным в метод (например в метод `updateById(UUID id)`)
    - `@CacheEvict(beforeInvocation = true)` - означает, что закэшированное значение будет удаляться до вызова метода
* `@Caching` - используется, когда над методом нужно разместить несколько аннотаций `@Cacheable`, `@CacheEvict`
  или `@CachePut`

#### ConcurrentMapCacheManager
* `ConcurrentMapCacheManager` - Вид in-memory cache, основанный на `ConcurrentHashMap<>`.

#### Redis
* `Redis` - высокопроизводительная nosql-система управления базами данных (данные в виде key-value значений),
  использующаяся для кэширования данных, хранения сессий пользователей, реализации очередей сообщений, реализации
  паттернов Publish-Subscribe и других сценариев.
* Преимущества `Redis`:
    - Высокая производительность
    - Хранение данных в памяти
    - Поддержка различных структур данных: строки, списки, сэты, мапы и др.
* Использование `Redis`:
    - Кэширование данных
    - Управление сессиями пользователей
    - Реализация очередей сообщений
    - Хранение и обработка геоданных (поиск по координатам)
* `Redis` - сторонняя система, которая доступна через порты соединений. Сам сервер `Redis` запускается через `docker`
* `spring-boot-starter-data-redis` - стартер для подключения `Redis`
* `io.lettuce:lettuce-core` - клиент для работы с `Redis`, который будет использоваться для фабрики соединений с `Redis`
* `RedisProperties.class` - свойства `Redis` в `spring`
* `LettuceConnectionFactory.class` - connection factory для Redis
* `RedisStandaloneConfiguration.class` - задаётся hostName, port и передаётся в `LettuceConnectionFactory.class`,
  использование `RedisStandaloneConfiguration.class` означает, что будет использован режим одного сервера `Redis`. Также
  может быть использован кластерный режим использования `Redis`
* `RedisTemplate.class` - в него передаётся `LettuceConnectionFactory.class` и key
  serializer (`StringRedisSerializer.class`)
* `RadisCacheManager` - реализация `CacheManager` для `Redis`
* Все элементы, которые мы хотим кэшировать в `Redis` используя `StringRedisSerializer` должны быть `Serializable`, есть возможность использовать `Jackson`

### Примеры

#### ConcurrentMapCacheManager
Пример реализации `ConcurrentMapCacheManager`

```java
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

public class MyConcurrentMapCacheManager() extends ConcurrentMapCacheManager {
  @Override
  protected Cache createConcurrentMapCache(String name) {
    return new ConcurrentMapCache(
            name,
            CacheBuilder.newBuilder()
                    .expireAfterWrite(Duration.of(100, ChronoUnit.SECONDS))
                    .build()
                    .asMap(),
            true // allowNllValues
    );
  }

  public static MyConcurrentMapCacheManager create() {
    var cacheNames = List.of("firstCache", "secondCache");
    var cacheManager = new MyConcurrentMapCacheManager();
    cacheManager.setCacheNames(cacheNames);
    return cacheManager;
  }
}
```

#### Redis
* Пример настройки `Redis`

```yaml
# docker-compose.yaml
version: "3.9"
services:
  redis:
    image: redis:7.0.12
    ports:
      - "6379:6379"
```

```yaml
# application.yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```
* Пример создания `RedisCacheManager`

```java
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;

public class RedisCacheManagerApp() {

  public static RedisCacheManager create(LettuceConnectionFactory lettuceConnectionFactory) {
    var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
    Map<String, RedisCacheConfiguration> redisCacheConfiguration = new HashMap<>();
    var cacheNames = List.of("firstCache", "secondCache");
    cacheNames.forEach(cacheName -> {
      redisCacheConfiguration.put(cacheName, RedisCacheConfiguration.defaultCacheConfig().entryTtl(
              Duration.of(100, ChronoUnit.SECONDS)
      ));
    });

    return RedisCacheManager.builder(lettuceConnectionFactory)
            .cacheDefaults(defaultConfig)
            .withInitialCacheConfigurations(redisCacheConfiguration)
            .build();
  }
}
```
* Пример `@Configuration` для `Redis`

```java
@Configuration
public class RedisConfiguration {
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties) {
        var configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
```
* Пример cache `@Configuration` для `Redis`
```java
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
```
