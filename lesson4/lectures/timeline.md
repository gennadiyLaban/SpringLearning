#### 4.2 REST-контроллеры в Spring
* `23:17` - создание `BeanUtils`, он через рефлексию копирует поля, которые не `null`.
* `26:50` - выставляется свойство, разрешающее циклические зависимости
* `46:35` - создание `ClientController`
* `51:55` - рассказ про `ResponseEntity`
* `58:50` - пример использования `Postman`

#### 4.3 Тестирование контроллеров. Работа с ошибками и валидация
* `00:45` - написание базового класса контроллера для тестов. Использование `@AutoConfigureMockMvc`, `@SpringBootTest`
* `07:35` - создание `StringTestUtils` - чтение ресурсов по пути.
* `10:16` - подключение библиотеки проверки json-объектов `net.javacrumbs.json-unit:json-unit:2.38.0`, тут же можно посмотреть `spring-boot-starter-test`
* `12:49` - пример создания тестового json-файла
* `19:47` - пример использования `@MockBean` + `MockMvc.class` + `Mockito` + `JsonAssert`
* `37:17` - использование `@ResponseStatus(HttpStatus.NOT_FOUND)` для обозначения кода http-ответа при выбрасывании исключения `EntityNotFoundException.class`
* `38:44` - пример использования метода с аннотацией `@ExceptionHandler` в контроллере `ClientController.class` для обработки исключения `EntityNotFoundException.class`
* `40:23` - создание кастомной модели `ErrorResponse.class`
* `42:13` - создание `ExceptionHandlerController.class` с аннотацией `@RestControllerAdvice`, в котором определена обработка ошибки `EntityNotFoundException.class`. Она (обработка) будет использоваться для обработки ошибки `EntityNotFoundException.class` во всех других контроллерах. Также находится пример использования кастомного `ErrorResponse.class` как тела ошибки.
* `44:20` - задание кодировки ответа в тестах `response.setCharacterEncoding("UTF-8")`
* `47:31` - добавление зависимости `spring-boot-starter-web`
* `48:07` - использование `@NotBlank`
* `56:34` - пример использования `@ParametrizedTest`

#### 4.4 Фильтры и перехватчики. Request Score и Session Score
* `02:37` - создание Web Filter `ClientApiFilterChecker` - фильтра проверки `header`
* `02:37` - пример использования `@ConditionalOnExpression("${app.check-client-api-header:true}")` - возможность "включать/выключать" компонент по условному выражению, в данном случае значение берётся из свойств приложения
* `07:03` - пример задания профиля приложения для тестов
* `12:12` - создание `LoggingControllerInterceptor` - перехватчик для логгирования запросов
* `14:29` - пример регистрации `Interceptor` - `LoggingControllerInterceptor`
* `16:36` - пример регистрации `Interceptor` - `ClientControllerInterceptor` строго для определённого паттерна пути
* `20:50` - пример использования `@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)` и `@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)`
* `22:41` - создание `IdLoggingFilter`, для наглядной демонстрации использования `WebApplicationContext.SCOPE_REQUEST` и `WebApplicationContext.SCOPE_SESSION`
* `25:20` - пример обнуления `cookies` сессии в `Postman`

#### 4.5 OpenAPI
* `02:15` - подключение `org.springdoc.springdoc-openapi-starter-webmvc-ui:2.0.4` 
* `04:19` - создание OpenApi конфигурации

#### 4.6 Основы Spring Data JPA
* `06:41` - application.yml с подключением jpa и datasource
* `07:12` - список зависимостей проекта, в том числе `spring-boot-starter-data-jpa`
* `09:05` - создание `@Entity public class Order {}` 
* `14:49` - создание `DatabaseClientRepository extends JpaRepository<Client, Long>` и `DatabaseOrderRepository`
* `18:34` - создание `DatabaseOrderService.java`
* `22:46` - подключение библиотеки `org.mapstruct:mapstruct:1.5.2.Final`
* `23:33` - создание маппера на основе `mapstruct`
* `28:10` - создание `OrderMapperDelegate.java`
* `30:07` - использование `@DecorateWith(OrderMapperDelegate.class)`
* `37:40` - создание `ClientControllerV2.java`


#### 4.7 Составление запросов
* `03:13` - создание `DatabseOrderRepository extends JpaRepository<>` и демонстрация, как работает сематическое составление запроса
* `04:33` - создание метода `List<Order> filterBy(OrderFilter filter)` в репозитории
* `05:49` - создание метода `/filter` `filterBy()` в `OrderControllerV2.java`
* `11:27` - добавление `pagination` через параметры в `OrderFilter.java`
* `15:20` - добавление полей для фильтрации по min(max)Cost/create(update)Before/clientId
* `18:00` - пример создания `Specification.java` в утилитном классе `OrderSpecification.java`
* `25:38` - пример применения `Specification.java`
* `31:40` - пример использования `@Query` и `JPQL`


#### 4.8 Транзакции
* `03:35` - реализация метода создания клиента с заказами
* `09:55` - пример выбрасывания исключения после сохранения клиента, но до сохранения его заказа, и его (выбрасывания) последствия.
* `11:27` - пример использования `@Transactional`
* `19:16` - пример использования `@EntityGraph`


#### 4.9 AOP
* `02:54` - пример создания аспекта
* `09:10` - пример реализации аспектов
