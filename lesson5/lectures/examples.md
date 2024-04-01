#### 5.1 Интеграция с внешними системами

* Виды интеграций:
  - HTTP/HTTPS: основной протокол для RESTful API и веб-сервисов
  - SOAP: простой протокол доступа к объектам для обмена сообщениями в формате XML. Используется для RPC-процедур
  - WebSockets: протокол WebSockets позволяет установить постоянное двустороннее соединение между клиентом и сервером
    для обмена данных в режиме реального времени
  - FTP/SFTP: протокол передачи файлов
  - JDBC/ODBC: протоколы взаимодействия с базами данных
  - JMS: система управления сообщениями, стандартный протокол обмена сообщениями между компонентами
* Структура связи между интеграциями:
  - Однонаправленная интеграция (от источника к получателю)
  - Взаимодействие на основе сообщений. Может быть синхронным и асинхронным
  - Взаимодействие на основе событий. Обмен информацией на основе возникновения событий или уведомлений
  - Интеграция через API. Обмен функциональностью и данными через специальное API
  - Интеграция через сервисы. Обмен данными и функциональностью через веб-сервисы или микросервисы
* OkHttp - клиентская библиотека для выполнения сетевых запросов. Обычно используется в приложениях, которым требуется
  более низкоуровневый контроль над сетевыми запросами.
* OpenFeign - декларативный HTTP-клиент, который предоставляет аннотации для определения интерфейсов, которые обозначают
  удалённые API.
* WebClient - реактивный HTTP-клиент, предоставляемый SpringFramework для выполнения асинхронных HTTP-запросов.
  WebClient хорошо интегрирован c реактивными возможностями Spring, такими как WebFlux.
* RestTemplate - классический синхронный HTTP-клиент, предоставляемый Spring Framework. Сейчас это по большей части
  legacy

#### 5.2 HTTP-клиенты. Часть 1

* `@RequestPart` - аннотация, которая показывает, что входящие данные могут содержать несколько частей (разбиты на
  несколько частей). К таким частям может принадлежать, например, `MultipartFile.class`
* `HttpHeaders.CONTENT_DESPOSITION` - используется для передачи дополнительной информации о том, как обрабатывать данные
  ответа(`response payload`), и также может быть использован для прикрепления дополнительной метаинформации, такой как
  filename, что может быть использовано для сохранения данных ответа (`response payload`) локально.
* `OkHttpClient.class` - основной класс OkHttp-клиента, при создании происходит настройка основных параметров клиента
* `Request.class` - основной класс OkHttp-клиента для создания запроса, создаётся через `Request.Builder()`
* `Response.class` - основной класс OkHttp-клиента для получения ответа от сервера, возвращается
  вызовом `OkHttpClient.newCall(Request).execute()`
* `MultipartBody` - основной класс OkHttp-клиента для создания `RequestBody` для `MultipartFile.class`. Создаётся
  через `MultipartBody.Builder()`
* `Accept: <MIME_type>/<MIME_subtype>` - заголовок в запросе означает, что клиент хочет видеть ответ в определённом
  формате `<MIME_type>/<MIME_subtype>`
  - Отсутствие заголовка означает, что клиент может принять любой формат ответа
  - `Accept: application/octet-stream` - означает, что клиент хочет ответ в виде неструктурированного потока байт
* `@EnableFeignClients` - аннотация ставится над `@SpringBootApplication` классе, чтобы разрешить
  использование `OpenFeign`
* `@FeignClient` - ставится над интерфейсом и говорит компилятору, что нужно создать реализацию этого интерфейса
  - `@FeignClient(name = "openFeignClient")` - задание имени данному клиенту
  - `@FeignClient(url = "${app.webclient.base-url}")` - задание базового url для этого клиента
* `@PostMapping` - аннотация позволяет указывать `OpenFeign` тип и path запроса
  - `@PostMapping(value = "/api/foo/foo1)` - задаёт path запроса
  - `@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)` - задаёт MIME type отправляемых значений
* `@RequestPart` - указывает, что аргумент метода - часть `multipart/form-data` запроса
* `@GetMapping` - аннотация позволяет указывать `OpenFeign` тип и path запроса
  - `@GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)` - задаёт MIME type принимаемых значений
* `@PathVariable` - позволяет указать, что аргумент содержит значение для части path, по которому делается запрос

#### 5.5 Кеширование :)

* Виды кэширования:
  - Кэширование веб-страниц
  - Кэширование баз данных
  - Кэширование в памяти
  - Кэширование DNS
* `Example<>` - класс, с помощью которого можно запрашивать данные в `JpaRepository<>`.

```java

@Service
@RequiredArgConstructor
public class Service {
  private final FooRepository repository;

  public FooEntity findByName(String name) {
    FooEntity probe = new Entity();
    probe.setName(name);

    ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withIgnorePaths("id", "date");
    Example<FooEntity> example = Example.of(probe, matcher);
    return repository.findOne(probe);
  }

}
```

* `org.springframework.boot:spring-boot-starter-cache` - стартер для подключения кэша
* `@EnableCaching` - говорит `spring` включить кэширование, должна быть над конфигурационным классом (`@Configuration`)
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
* `com.google.guava:guava:32.1.1-jre` -
* `@ConfigurationProperties` - показывает, что класс является репрезентацией и хранилищем пропертей с каким-либо
  префиксом
  - `@ConfigurationProperties(prefix = "app.cache")` - показывает, что данный класс хранит property
    префиксом `app.cache`
* `@EnableConfigurationProperties(FooProperties.class)` - разрешает использование `FooProperties.class`, регистрирует
  его, автоматически парсит и создаёт компонент для inject. Аннотация должна размещаться над `@Configuration`
* `@ConditionalOnExpression` - позволяет задать условное выражение, которое будет разрешать/запрещать
  создание/использование `@Bean` или `@Component`
* `ConcurrentMapCacheManager` - задаёт настройки кэшей проекта.

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

#### 5.6 Redis

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
* `spring-boot-starter-data-redis` - стартер для подключения `Redis`
* `io.lettuce:lettuce-core` - клиент для работы с `Redis`, который будет использоваться для фабрики соединений с `Redis`
* `Redis` - сторонняя система, которая доступна через порты соединений. Сам сервер `Redis` запускается через `docker`

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

* `RedisProperties.class` - свойства `Redis` в `spring`
* `LettuceConnectionFactory.class` - connection factory для Redis
* `RedisStandaloneConfiguration.class` - задаётся hostName, port и передаётся в `LettuceConnectionFactory.class`,
  использование `RedisStandaloneConfiguration.class` означает, что будет использован режим одного сервера `Redis`. Также
  может быть использован кластерный режим использования `Redis`
* `RedisTemplate.class` - в него передаётся `LettuceConnectionFactory.class` и key
  serializer (`StringRedisSerializer.class`)
* `RadisCacheManager` - реализация `CacheManager` для `Redis`

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

* `@CacheConfig(cacheManager = "fooCacheManager")` - над классом `@Service` указывает, что нужно использовать
  been `CacheManager` с именем `fooCacheManager`
* Все элементы, которые мы хотим кэшировать в `Redis` должны быть `Serializable` (возможно, есть другие варианты)

#### 5.7 Тестирование систем с помощью Testcontainers и WireMock

* `Testcontainers` - библиотека, которая предоставляет удобный способ для запуска контейнеров Docker во время выполнения
  тестов. Позволяет создавать временные контейнеры для различных сервисов, таких как базы данных, брокеры сообщений,
  кеши и многих других.
  - Позволяет создавать изолированные тестовые окружения с использованием контейнеров Docker
  - Упрощает процесс настройки и развёртывания тестовых окружений, особенно если приложение взаимодействует с внешними
    сервисами.
  - Предоставляет удобный API для управления контейнерами и взаимодействия с ними внутри тестов.
* `WireMock` - это HTTP-сервер, который используется для создания фейковых или моковых серверов для тестирования
  HTTP-клиентов. Он позволяет создавать и настраивать фейковые endpoint с заданными ответами, чтобы эмулировать внешние
  сервисы и тестировать взаимодействие с их API.
  - Позволяет создавать моковые endpoints
  - Обеспечивает возможность проверки запросов
  - Упрощает тестирование взаимодействия с внешними сервисами без необходимости использовать реальные ресурсы во время
    тестирования
* `com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6` - библиотека тестирования контейнеров для `Redis`
* `org.testcontainers:junit-jupiter:1.4.6` -
* `org.testcontainers:postgresql:1.17.6` -
* `com.github.tomakehurst:wiremock-jre8-standalone:2.35.0` -
* `@Sql("classpath:db/init.sql)` - аннотация над тестовым классом говорит, что перед запуском тестов (или после) нужно
  выполнить sql-скрипт по `classpath`
* `@Transactional` - над тестовым методом означает, что `spring` создаст транзакцию вокруг тестового метода и по
  завершении теста транзакция будет откатываться, восстанавливая исходное состояние базы данных.
* `@Testcontainers` - если класс теста помечен этой аннотацией, то перед запуском тестов осуществляется запуск
  контейнеров, а после - остановка
* `@RegisterExtension` - регистрация расширений тестов (похоже, речь идёт о junit-5 extensions)
* `WireMockExtension.class` -
* `PostgreSQLContainer.class` -
* `DockerImageName.class` -
* `RedisContainer.class` -
* `@DynamicPropertySource` - используется в spring-тестах для динамического задания значений свойств
