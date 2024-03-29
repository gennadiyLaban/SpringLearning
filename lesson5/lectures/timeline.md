#### 5.1 Интеграция с внешними системами

* `01:03` - виды интеграций
* `02:49` - OkHttp и другие http-клиенты

#### 5.2 HTTP-клиенты. Часть 1

* `04:12` - создание метода `upload` для загрузки файла на сервер
* `06:46` - создание метода `download` для скачивания файла с сервера
* `13:51` - создание `EntityController.class`
* `14:35` - создание клиентской части
* `16:56` - подключение OkHttp к проекту
* `24:39` - реализация метода загрузки файла на сервер (со стороны клиента)
* `25:22` - реализация метода скачивания файла с сервера (со стороны клиента)
* `29:50` - пример реализации обработки ответа сервера и парсинга ответа в сущность
* `37:05` - создание `FileClientController.class` - апи для взаимодействия с "клиентом" через Postman

#### 5.3 HTTP-клиенты. Часть 2

* `08:01` - создание тех же методов с помощью `RestTemplate`
* `10:41` - реализация `uploadFile` с помощью `RestTemplate`
* `12:51` - реализация `downloadFile` с помощью `RestTemplate`
* `13:57` - реализация `getEntityList` с помощью `RestTemplate`
* `22:42` - подключение `WebFlux` (для получения `WebClient`) и создание `@Bean WebClient`
* `26:12` - реализация метода `uploadFile` с помощью `WebClient`
* `27:31` - реализация метода `downloadFile` с помощью `WebClient`
* `27:31` - реализация метода `downloadFile` с помощью `WebClient`
* `28:32` - реализация метода `getEntityList` с помощью `WebClient`
* `29:12` - реализация метода `getEntityByName` с помощью `WebClient`
* `24:48` - подключение `OpenFeign` клиента
* `37:28` - реализация `uploadFile` и `downloadFile`
* `37:54` - реализация `getEntityList`

#### 5.5 Кеширование :)

* `15:15` - подключение зависимости для кэша
* `15:48` - создание CacheConfiguration
* `16:35` - пример использования кэша (`@Cacheable`)
* `24:13` - настройка время жизни записей в кэше
* `24:43` - подключение `com.google.guava:guava:32.1.1-jre`
* `28:16` - создание `AppCacheProperties`
* `29:04` - пример задания значений `properties` для `AppCacheProperties`
* `30:37` - пример `@Bean` в сочетании с `@ConditionalOnExpression`
* `32:22` - пример создания `ConcurrentMapCacheManager`

#### 5.6 Redis

* `03:38` - подключение `spring-boot-starter-data-redis` и `io.lettuce:lettuce-core`
* `04:36` - добавление `Redis`-сервис в `docker-compose.yml`
* `05:00` - настройки в `application.yaml` для `Redis`
* `06:15` - создание `RedisConfig`
* `09:05` - реализация настройки `Redis`
* `10:55` - создание `RedisCacheManager`в `CacheConfiguration`
* `16:13` - подключаемся к `Redis`-контейнеру, где можно проинспектировать содержимое
* `19:40` - пример задания значения ключа к кэшируемому объекту
* `21:08` - пример очищения кэша для конкретного значения ключа

#### 5.7 Тестирование систем с помощью Testcontainers и WireMock

* `02:47` - подключение `com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6`
* `03:11` - подключение `org.testcontainers:junit-jupiter:1.17.6`
* `03:11` - подключение `org.testcontainers:postgresql:1.17.6`
* `04:02` - подключение `com.github.tomakehurst:wiremock-jre8-standalone:2.35.0`
* `07:16` - создание `AbstractTest.class` с `@Sql("classspath:db/init.sql")`
* `17:10` - пример мокирования endpoint через `WireMock`
* `25:14` - объяснение, как удаляются все ключи из `Redis` перед каждым тестом
* `26:13` - создание `EntityClientControllerTest.class`
* `26:53` - пример проверки, что в `Redis` нет ключей
* `28:54` - пример теста для `api/v1/client/enity`
