#### 4.2 REST-контроллеры в Spring
* `@Controller` vs `@RestController` - основная разница в том, что `@RestController` - это, по сути, комбинация `@Controller` и `@ResponseBody`. Где `@ResponseBody` говорит spring, что ответ нужно сериализовать напрямую в JSON или XML.
* `ResponseEntity.class` - класс-обёртка для http-ответа, он позволяет гибко отдать ответ, включая тело ответа, код ответа и заголовки ответа.
* `@PathVariable("id") Long clientId` - указывает, что переменная `clientId` берётся из переменной пути запроса, указанной в `@*Mapping` - аннотации. Например: `@PostMapping("/{id}")`
* `@RequestBody Foo foo` - означает, что переменная класса `Foo.class` `foo` должна быть десериализована из тела запроса.


#### 4.3 Тестирование контроллеров. Работа с ошибками и валидация
* `@SpringBootTest` - указывает, что тест является интеграционным, при его выполнении нужно загружать весь контекст приложения.
* `@AutoConfigureMockMvc` - автоматическое создание и внедрение экземпляра `MockMvc.class`
* `MockMvc` - эмулирует отправку http-запроса и позволяет проверить ответ контроллера.
* `ObjectMapper` - основной класс библиотеки `Jackson` - библиотека преобразования JSON (и других протоколов) в Java-объекты и обратно.
* `@MockBean` - создание заглушки-bean в выполняемом тесте
* `@ResponseStatus(HttpStatus.*)` - при обозначении над `*Exception.class` генерирует соответствующий `HttpStatus.*` код http-ответа, если происходит выбрасывание соответствующего исключения
* `@ExceptionHandler(*Exception.class)` - метод объявленный в контроллере и аннотированный этой аннотацией будет использоваться для обработки (отправки соответствующего ответа) не перехваченного исключения типа `*Exception.class`
* `@RestControllerAdvice` - помечает класс как контроллер, который может содержать методы обработки ошибок (аннотация `@ExceptionHandler(*Exception.class)`). И эти методы будут использоваться по-умолчанию во всех остальных контроллерах.
* `@Valid` - аннотация в методе контроллера обозначает, что тело запроса `@RequestBody @Valid Foo foo` должно быть провалидировано, т.е. при поступлении запроса валидации, связанные и объявленные в `Foo.class` выполняться, а при несоответствии полей условием будет выброшено исключение `MethodArgumentNotValidException.class`.


#### 4.4 Фильтры и перехватчики. Request Score и Session Score
* `Interceptors` - перехватчики - это механизм Spring, который позволяет перехватывать и обрабатывать HTTP-запросы и ответы перед и после их обработки контроллерами.
  - Работают на уровне Spring MVC
  - Применяются только к обработке запросов внутри Spring MVC контроллеров
  - Имеют доступ к высокоуровневым Spring-специфичным объектам (например ModelAndView)
* `Web Filter` - это механизм Java Servlet API, который позволяет перехватывать и модифицировать HTTP-запросы и ответы до и после их обработки сервлетами.
  - Работают на уровне Java Servlet API
  - Может быть применён ко всему приложению, включая статические ресурсы и другие сервлеты
  - Работает с (относительно) низкоуровневыми объектами `Request` и `Response`
* `@ConditionalOnExpression("${app.check-client-api-header:true}")` - возможность "включать/выключать" компонент по условному выражению, в данном случае значение берётся из свойств приложения
* `OncePerRequestFilter` - базовый класс для WebFilter (одного из типов), который должен быть выполнен один раз для каждого запроса
* Чтение значения поля сразу в поле класса:
  - ```java
    @Value("${app.client-api-key")
    private String clientApiKey;
    ``` 
* Пример реализации фильтра проверки header:
  - ```java
    @Component
    public class ClientApiFilterChecker extends OncePerRequestFilter {
        private static final String HTTP_CLIENT_HEADER = "X-Client-Api-Key";
    
        @Value("${app.client-api-key}")
        private String clientApiKey;
    
        @Override
        protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
        ) throws ServletException {
            String headerValue = request.getHeader(HTTP_CLIENT_HEADER);
    
            if (headerValue == null || !headerValue.equals(clientApiKey)) {
                response.setHeader(HTTP_CLIENT_HEADER, "Invalid");
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Заголовок X-Client-Api-Key отсутствует или указан неверно!");
                return;
            }
    
            filterChain.doFilter(request, response);
        }
    }
    ```
* `HandlerInterceptor` - интерфейс перехватчика
* Пример реализации `HandlerInterceptor` - `LoggingControllerInterceptor`
  - ```java
      @Slf4j
      public class LoggingControllerInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            log.info("LoggingControllerInterceptor -> Запрос готовиться к отправке в контроллер");
            return HandlerInterceptor.super.preHandler(request, response, handler);
        }
        
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            log.info("LoggingControllerInterceptor -> Запрос обработан. Подготовка к отправке клиенту");
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }
    
        @Override
        public boolean afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            log.info("LoggingControllerInterceptor -> Ответ клиенту отправлен");
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        }   
      }
    ```
* `@Configuration` - аннотация показывает, что данный класс предоставляет beans для приложения
* `WebMvcConfigurer` - базовый интерфейс, который предоставляет методы для конфигурации Spring Web MVC, при нахождении реализации с пометкой `@Configuration`, методы данного класса будут вызваны для конфигурирования автоматически при старте приложения.
* `WebMvcConfigurer` предоставляет возможность регистрации `Interceptors`, как общий (для всех путей), так и типизированный для путей
   - ```java
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingControllerInterceptor()); // для всех запросов
        registry.addInterceptor(loggingControllerInterceptor())
                .addPathPatterns("/api/v1/client/**"); // для запросов на определённый паттерн пути
     }
     ```
* `@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)` - указывает скоуп, т.е. к чему относится bean. `value = WebApplicationContext.SCOPE_REQUEST` - говорит о том, что на каждый запрос будет один экземпляр bean. `proxyMode = ScopedProxyMode.TARGET_CLASS` говорит spring использовать proxy-объект на основе класса целевого bean (класс, над которым висит аннотация `@Scope`), объект, который будет создан будет иметь тот же класс, что и целевой bean, но будет обёрнут в proxy для обеспечения правильной обработки запросов.
* `@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)` - то же, что и выше, только будет создан один объект на одну сессию работы с пользователем, которая может включать в себя несколько запросов.
* `@Scope` ???судя по всему??? создаёт на основе класса один proxy-объект, который под капотом маршрутизирует обращение к конкретному объекту данной session (request) 


#### 4.5 OpenAPI
* `OpenAPI` - набор спецификаций для описания и документирования веб-сервисов RESTful API. Обычно используется формат `JSON` или `YAML`
* подключение `org.springdoc.springdoc-openapi-starter-webmvc-ui:2.0.4` позволяет автоматически генерировать страничку с описанием api по пути `/swagger-ui/index.html`
* `Server.class` - хранит информацию о сервере (url, description), серверов может быть несколько, например тестовый и продовый
* `Contact.class` - хранит информацию о контактах, к кому можно обратиться в случае вопросов.
* `License.class` - хранит информацию о лицензии
* `Info.class` - хранит такую информацию, как title, version, contact (`Contact.class`), description, termsOfService, licence (`License.class`)
* `OpenAPI.class` - итоговый объект-контейнер для описания api, принимает в себя `Info.class`, `Server.class`. Его нужно сконструировать в классе конфигурации (`@Configuration`) и представить, как bean (`@Bean`)
* `@Tag(name="Client v1", description="Client API version V1")` - аннотация ставиться над контроллером для того, чтобы определить дополнительную информацию о контроллере, который содержит методы api. Методы api группируются по контроллерам.
* `@Operation(summary="some summary", description="some description" tags = {"tag1", "tag2"}) - `добавляет описание отдельного метода контроллера, в частности:
- `summary` - краткое описание того, что делает метод
- `description` - развёрнутое описание того, что делает метод
- `tags` - тэги для добавления групп-тегов, где будут те же методы, что и в группировке по контроллерам, но они будут объединены под одним из тегов. 
* `@ApiResponses(@ApirResponse[] responses)` - добавляет описание возможных ответов метода api
     - `@ApirResponse[] responses` - возможные ответы api
     - ```java
       @ApiResponses({
          @ApiResponse(
            responseCode = "200", // код ответа 
            content = { // возможные ответы
               @Content(
                  schema = @Schema( // описание схемы (структуры ответа)
                    implementation = SomeResponse.class // реализация схемы (структуры) ответа в коде
                  ),
                  mediaType = "application/json"
               ) 
            }
          ),
          @ApiResponse(
            responseCode = "404", // код ответа 
            content = { // возможные ответы
               @Content(
                  schema = @Schema( // описание схемы (структуры ответа)
                    implementation = NotFoundResponse.class // реализация схемы (структуры) ответа в коде
                  ),
                  mediaType = "application/json"
               ) 
            }
          )
       })
       public ResponseEntity<SomeResponse> foo() {} 
       ```

#### 4.6 Основы Spring Data JPA
* `@Entity(name = "orders)` - jpa-аннотация показывает что данный класс является моделью, которая представляет собой таблицу в базе данных
* `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` - данная комбинация над полем класса показывает, что поле является идентификатором таблицы (`@Id`), а также, что это поле должно генерироваться самой базой данных со стратегией `GenerationType.IDENTITY` (`@GeneratedValue(strategy = GenerationType.IDENTITY)`)
* `@ManyToOne` - над полем типа `@Entity B` внутри `@Entity A` показывает, что множество сущностей `@Entity A` имеют ссылку (иметь связь) с одним экземпляром (одной записью в таблице) `@Entity B`.
* `@JoinColumn(name = "foo_id")` - над полем типа `@Entity B` внутри `@Entity A` показывает, что связь между `@Entity A` и `@Entity B` осуществляется посредством записи значения идентификатора (поля с аннотацией `@Id`) `@Entity B` в колонку `foo_id` `@Entity A`.
* `@OneToMany` - над полем типа(коллекции) `@Entity B` внутри `@Entity A` показывает, что одна сущность `@Entity A` имеет ссылку на множество сущностей `@Entity B`. Это может достигаться за счёт того, что `@Entity B` имеет столбец с указанием на `@Entity A`, в этом случае выделяется поле по которому можно определить `@Entity A` через `@Entity B`, это делается за счёт указания `@OneToMany(mappedBy = "someA")`, где `someA` - это поле в `@Entity B`. Может ли это достигаться за счёт создания отдельной таблицы?
* `@OneToMany(cascade = CascadeType.ALL)` - `cascade` - определяет, должны ли удаляться/сохраняться/обновляться связанные сущности при удалении текущей.
* `@CreatedTimestamp` - показывает, что поле должно содержать время создания сущности.
* `@UpdateTimestamp` - показывает, что поле должно содержать время обновления записи сущности.
* `JpsRepository<EntityType, KeyType>` - базовый интерфейс для репозитория, его основная реализация генерируется фреймворком jpa, имеет несколько встроенных методов.
* `Mapstruct` - библиотека преобразования одних сущностей в другие без лишнего шаблонного кода.
* `@Mapper(componentModel = "spring", unmappedTargetPlicy = ReportingPlicy.IGNORE)`
   - `@Mapper` - обозначает, что `interface` является маппером и должен быть использован для генерации кода маппинга исходного типа в целевой
   - `componentModel = "spring"` - означает, что сгенерированный маппер должен быть автоматически зарегистрирован, как Spring Bean
   - `unmappedTargetPlicy = ReportingPlicy.IGNORE` - определят, что делать (игнорировать или выбрасывать исключения) если обнаружились немаппингованые целевые свойства, т.е. когда после маппинга у целевого объекта есть незаполненные поля
* `@Mapping(source = "orderId", target = "id")` - используется для указания маппинга между исходным объектом и целевым
* `@DecorateWith(OrderMapperDelegate.class)` - указывает, что `OrderMapperDelegate.class` должен использоваться для делегирования части функционала при маппинге
* `@Mapper(uses = { OrderMapperV2.class })` - `uses` - показывает, что нужно использовать уже созданный маппер `OrderMapperV2.class` для генерации текущего маппера


#### 4.7 Составление запросов
* `@Builder.Defaults` - `lombok` - позволяет задать дефолтное значение для билдера. В этом случае код задания значения будет восприниматься как дефолтное значение.
   - ```java 
import java.util.ArrayList;      

@Builder
public class FooData {
   @Builder.Default
   private final List<String> someStrings = new ArrayList<>(); // В случае, если при создании объекта поле `someStrings`
                                                         // не будет задано, в поле проставится значение `new ArrayList<>()`
}
```
* `JpaRepository<>` - в репозиториях отнаследованных от `JpaRepository<>` можно создавать методы, через название которых можно задать семантическое составление запроса, например:
   - ```java
     public interface FooRepository extends JpaRepository<Foo, Long> {
        List<Foo> findAllByProduct(String product);
        List<Foo> findAllByClientId(Long clientId);
        List<Foo> findAllByClientName(String clientName);
     
        @Data
        @Builder
        @Entity
        class Foo {
            @Id
            private Long id; 
            private String product;
            private Client client;
        }
     
        @Data
        @Builder
        @Entity
        class Client {
            @Id
            private Long id;
            private String name;
        }
     } 
     ```
* `Pageable` - объект, содержащий информацию о limit информации в запросе + номер `page`
   - `PageRequest.of()` - утилитный класс для создания объекта `Pageable`
* `Page<Foo>` - объект, содержащий пачку запрошенных объектов с помощью Pageable + доп. информацию о кол-ве суммарной информации, количестве страниц и т.д.
   - `Page<Foo>.getContent()` - метод, который возвращает `List<Foo>` объектов
* `JpaSpecificationExecutor<Foo>` - 
* `Specification<Foo>` - класс, содержащий спецификацию (критерии) фильтрации (выборки) данных из базы
   - `Specification.where(Specification<Foo>)` - статический метод позволяет начать цепочку спецификаций
   - `Specification<Foo>.and()` - позволяет добавить в цепочку условий новую спецификацию (предикат) со значением логического `и`
   - ```java
        @Data
        @Builder
        @Entity
        class Foo {
            @Id
            private Long id; 
            private String product;
            private Client client;
            private BigDecimal cost;
        }
     
        @Data
        @Builder
        @Entity
        class Client {
            @Id
            private Long id;
            private String name;
        }
     ```
   - ```java
     // фильтрация по имени продукта
     @RequireArgConstructor 
     class MySpecification implements Specification<Foo> {
        private final String productName;
        
        @Override
        public Predicate toPredicate(
            Root<Foo> root,                 // 
            CriteriaQuery<?> query,         // 
            CriteriaBuilder criteriaBuilder // 
        )  {
                if (productName == null) {
                    return null;
                }
     
                return cb.equal(root.get("product"), productName);
        }
     }
     ```
   - ```java
        // фильтрация по range цены товара
        private final BigDecimal minCost;
        private final BigDecimal maxCost;
        @Override
        public Predicate toPredicate(
            Root<Foo> root,                 // 
            CriteriaQuery<?> query,         // 
            CriteriaBuilder criteriaBuilder // 
        )  {
                if (minCost == null && maxCost == null) {
                    return null;
                }
                if (minCost == null) {
                    return cb.lessThanOrEqualTo(root.get("cost"), maxCost);
                }
                if (maxCost == null) {
                    return cb.greaterThanOrEqualTo(root.get("cost"), minCost);
                }
     
                return cb.between(root.get("cost"), minCost, maxCost);
        }
     ``` 
   - ```java
        // фильтрация по полю id связанной сущности (Client)
        private final Long clientId;
        @Override
        public Predicate toPredicate(
            Root<Foo> root,                 // 
            CriteriaQuery<?> query,         // 
            CriteriaBuilder criteriaBuilder // 
        )  {
                if (clientId == null) {
                    return null;
                }
     
                return cb.equal(root.get("client").get("id"), clientId); // получение значения у связанной сущности
        }
     ``` 
* `JpaSpecificationExecutor<Foo>` - класс, позволяющий использовать `Pageable.java` и `Specification.java` для поиска, фильтрации и выборки данных в `JapRepository<Foo>`. Как и для `JapRepository<Foo>` реализация методов будет сгенерирована автоматически. Наследоваться нужно одновременно от `JpaSpecificationExecutor<Foo>` и `JapRepository<Foo>`.
* `@FieldNameConstans` - `lombok` - позволяет сгенерировать внутренний класс с константами-именами выбранного класса. Это будет гораздо нагляднее, чище и безопаснее при использовании имён полей в `Specification<>`.
* `JPQL` - язык запросов в `jpa`, который позволяет обращаться не к столбцам таблицы бд, а к полям `@Entity`
* `@Query` - аннотация над методом в классе-наследнике `JapRepository<>` позволяет написать кастомный запрос на языке `JPQL` или `SQL`
   - ```java
     // JPQL
      @Query("SELECT foo FROM com.example.foo.Foo foo WHERE foo.product = :productName")
      List<Foo> getByProduct(String productName);
     ```  
   - ```java
     // SQL
      @Query(value = "SELECT * FROM foos foo WHERE foo.product = :productName", nativeQuery = true)
      List<Foo> getByProduct(String productName);
     ```  
* `Кастомная валидация` - возможность создавать свои валидаторы для проверки правильности заполнения полей. Для этого нужно:
   - аннотация, маркирующая необходимость валидации поля(всего объекта):
   - ```java
      @Documented
      @Constraint(validatedBy = OrderFilterValidator.class) // `OrderFilterValidator` - класс, где происходит 
                                                            // проверка (валидация) условий
      @Target(ElementType.TYPE)
      @Retention(RetentionPolicy.RUNTIME)
      public @interface FooFilterValid { // аннотация, маркирующая необходимость валидации поля
        String message() default "Поля пагинации minCost и maxCost должны быть указаны!";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
      }
     ```
   - реализация валидации:
   - ```java
  public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() == null)
                && (value.getMinCost() == null || value.getMaxCoxt() != null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() == null)
                && (value.getMinCost() == null || value.getMaxCoxt() !=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() == null)
                && (value.getMinCost() == null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() == null)
                && (value.getMinCost() ==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() == null)
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() == null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost() ==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() != null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost() !=null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }
        return (value.getMinCost()!=null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }   
        return (value.getMinCost()!=null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }   
        return (value.getMinCost()!=null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);return true;
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }   
        return (value.getMinCost()!=null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
        return true;
    }
  }public class OrderFilterValidator implements ConstaraintValidator<FooFilterValid, FooFilter> { // <аннотация, тип проверяемого значения>
   
    @Override
    public boolean isValid(OrderFilter value, ConstraintValidatorContext context) {
        if (ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())) {
            return false;
        }   
        if ((value.getMinCost() == null && value.getMaxCost() != null) 
                || (value.getMinCost() != null && value.getMaxCoxt() == null)) {
            return (value.getMinCost()!=null || value.getMaxCost()==null) 
                && (value.getMinCost()==null || value.getMaxCoxt()!=null);
        }
        return true;
    }
  }
```
   - аннотировать модель, которую нужно проверить
   - ```java
       @Data
       @OrderFilterValid
       public class OrderFilter { 
          private Integer pageNumber;
          private Integer pageSize;
          private BigDecimal minCost;
          private BigDecimal maxCost;
       }
      ```


#### 4.8 Транзакции
* `ACID`
- `Атомарность (Atomicity)` - транзакция считается атомарной если все её операции выполняются как единое целое. Если хотя бы одна операция транзакции не может быть выполнена, то все изменения, сделанные предыдущими операциями, откатываются.
- `Согласованность (Consistency)` - транзакция должна обеспечивать целостность данных. Это означает, что база данных должна оставаться в согласованном состоянии до и после выполнения транзакции.
- `Изолированность (Isolation)` - транзакции должны быть изолированы друг от друга. Это означает, что результаты одной транзакции не должны влиять на результаты другой транзакции. Это предотвращает конфликты и сбои в случае с параллельным выполнением транзакций.
- `Долговечность (Durability)` - после успешного завершения транзакции её изменения должны быть сохранены в базе данных и остаться действительными даже в случае сбоев системы или отключения питания.
* `@Transactional` - аннотация над `методом` позволяет выполнять внешние вызовы `этого` метода в режиме транзакции
* `@Transactional` - аннотация над `классом` позволяет выполнять внешние вызовы `всех` методов класса в режиме транзакции
* `@Transactional` - позволяет устанавливать режимы `read-only` и `read-write`
* `@Transactional` - позволяет устанавливать уровень изоляции от других транзакций
* `@Transactional` - позволяет управлять точкам сохранения `savepoints` - они позволяют разделить транзакцию на несколько логических частей
* `@Transactional` - позволяет настроить поведение транзакции в случае возникновения исключения
* `@Transactional` - на самом деле сам метод класса не выполняется в режиме транзакции, `Spring` создаёт proxy-объект для всех мест, куда был затребован объект данного типа и уже методы этого proxy-объекта выполняются в режиме транзакции. Это ведёт к следующим особенностям:
- Пусть класс `A` имеет метод `foo()`, который помечен `@Transactional`, класс `B` запросил у `spring` объект класса `A aObject`. Тогда вызовы `aObject.foo()` изнутри `B` будут проходить в режиме транзакции.
- Пусть класс `A` имеет методы `foo()` с пометкой `@Transactional` и `foo2()` без пометки `@Transactional`, пусть метод `foo2()` вызывает метод `foo()`, пусть класс `B` запросил у `spring` объект класса `A aObject`. Тогда при вызове методов объекта `A aObject`изнутри `B` имеются две ситуации:
     + если вызывается метод `aObject.foo()`, то работа метода будет проходить в режиме транзакции
     + если вызывается метод `aObject.foo2()`, то работа метода `foo2()` и вызванного изнутри метода `foo2()` метода `foo()` будут выполняться `вне` режима транзакции, т.к. метод `foo2()` вызвал метод `foo()` у оригинального, а не proxy-объекта.
* `@OneToMany(fetch = LAZY)` - `fetch = LAZY` - этот параметр говорит, что связанная с полем `@Entity` не будет вычитываться сразу при запросе исходной сущности, а только при обращении к полю. Если исходная сущность запрашивается в контексте транзакции, то попытка запроса связанной сущности вне транзакции может привести к выбрасыванию исключения `LazyInitializationException.java`, если свойство `spring.jpa.open-in-view` будет стоять в `false` (по-умолчанию `true`)
* `spring.jpa.open-in-view` - если значение `true` (по-умолчанию `true`) - автоматически открывает соединение при попытке вычитывания связанной сущности, даже вне транзакции. Если значение `false` автоматически соединение не открывается и тогда будет выкидываться ошибка `LazyInitializationException.java`
* `@EntityGraph` - позволяет указать, какие связанные сущности должны быть запрошены при запросе родительской сущности.
* `@Entity` - объект entity полученный внутри `@Transactional` сохранит своё состояние по окончании транзакции автоматически, это нужно учитывать в тех случаях, когда изменяемые поля имеют модификатор отношений со свойством `cascade`.


#### 4.9 AOP
* `@Aspect` - используется для обозначения классов, которые представляют собой аспекты. Классы с этой аннотацией определяют перехватчики, которые будут применяться к целевым методам
* `@Before` - аннотация указывает, что перехватчик должен быть выполнен до вызова целевого метода
* `@After` - аннотация указывает, что перехватчик должен быть выполнен после вызова целевого метода, независимо от результата этого метода
* `@AfterReturning` - аннотация указывает, что перехватчик должен быть выполнен после успешного выполнения целевого метода и получения результата
* `@AfterThrowing` - аннотация указывает, что перехватчик должен быть выполнен, если целевой метод вызвал исключение
* `@Around` - аннотация указывает, что перехватчик должен быть выполнен вокруг вызова целевого метода, что позволяет контролировать его выполнения и результат

#### 4.10 Практическая работа
* `@Singular` - `lombok` - в сочетании с `@Builder` над классом даёт возможность добавлять в поле с типом коллекции по одному элементу, а не всю коллекцию целиком
* ```postgresql
  CREATE TABLE comments ( -- создание таблицы
    id BIGSERIAL PRIMARY KEY, 
    body TEXT NOT NULL, -- текст без ограничения по длинне
    -- REFERENCES users(id) - говорит о тому, что поле user_id - внешний ключ 
    -- ссылающийся на первичный ключ `id` таблицы `users`
    -- 
    -- ON DELETE CASCADE - говорит о том, что при удалении 
    -- записи из таблицы `users`, где первичный ключ `id` совпадает с внешним ключом
    -- `user_id` из таблицы `comments`, такую запись из таблицы `comments` тоже нужно удалить
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, 
    post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE
  );
  ```
* ```java
   private static Specification<Post> categorySpecificationOf(@Nonnull PostListRequest request) {
        return (root, query, cb) -> {
            List<Long> categoryIds = request.getCategories();
            if (CollectionUtils.isEmpty(categoryIds)) {
                return null;
            }
            return root
                    .<Post, Category>join(Post.Fields.categories) // Post.Fields.categories - коллекция List<Category>
                    .get(Category.Fields.id)
                    .in(categoryIds);
        };
    }
  ```
