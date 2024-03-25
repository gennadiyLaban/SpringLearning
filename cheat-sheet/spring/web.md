#### Spring Web

* `@Controller` vs `@RestController` - основная разница в том, что `@RestController` - это, по сути,
  комбинация `@Controller` и `@ResponseBody`. Где `@ResponseBody` говорит spring, что ответ нужно сериализовать напрямую
  в JSON или XML.
* `@RestControllerAdvice` - помечает класс как контроллер, который может содержать методы обработки ошибок (
  аннотация `@ExceptionHandler(*Exception.class)`). И эти методы будут использоваться по-умолчанию во всех остальных
  контроллерах.
* `ResponseEntity.class` - класс-обёртка для http-ответа, он позволяет гибко отдать ответ, включая тело ответа, код
  ответа и заголовки ответа.
* `@ExceptionHandler(*Exception.class)` - метод объявленный в контроллере и аннотированный этой аннотацией будет
  использоваться для обработки (отправки соответствующего ответа) не перехваченного исключения типа `*Exception.class`
* `@PathVariable("id") Long clientId` - указывает, что переменная `clientId` берётся из переменной пути запроса,
  указанной в `@*Mapping` - аннотации. Например: `@PostMapping("/{id}")`
* `@RequestBody Foo foo` - означает, что переменная класса `Foo.class` `foo` должна быть десериализована из тела
  запроса.
* `@ResponseStatus(HttpStatus.*)` - при обозначении над `*Exception.class` генерирует соответствующий `HttpStatus.*` код
  http-ответа, если происходит выбрасывание соответствующего исключения
* `Interceptors` - перехватчики - это механизм Spring, который позволяет перехватывать и обрабатывать HTTP-запросы и
  ответы перед и после их обработки контроллерами.
    - Работают на уровне Spring MVC
    - Применяются только к обработке запросов внутри Spring MVC контроллеров
    - Имеют доступ к высокоуровневым Spring-специфичным объектам (например ModelAndView)
* `Web Filter` - это механизм Java Servlet API, который позволяет перехватывать и модифицировать HTTP-запросы и ответы
  до и после их обработки сервлетами.
    - Работают на уровне Java Servlet API
    - Может быть применён ко всему приложению, включая статические ресурсы и другие сервлеты
    - Работает с (относительно) низкоуровневыми объектами `Request` и `Response`
* `OncePerRequestFilter.class` - базовый класс для WebFilter (одного из типов), который должен быть выполнен один раз
  для каждого запроса
* `HandlerInterceptor.class` - базовый интерфейс `Interceptors`
* `@Configuration` - аннотация показывает, что данный класс предоставляет beans для приложения
* `WebMvcConfigurer.class` - базовый интерфейс, который предоставляет методы для конфигурации Spring Web MVC, при
  нахождении реализации с пометкой `@Configuration`, методы данного класса будут вызваны для конфигурирования
  автоматически при старте приложения.
    - `WebMvcConfigurer` предоставляет возможность регистрации `Interceptors`, как общий (для всех путей), так и
      типизированный для путей
* `@Scope`:
    * `@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)` - указывает скоуп,
      т.е. к чему относится bean. `value = WebApplicationContext.SCOPE_REQUEST` - говорит о том, что на каждый запрос
      будет один экземпляр bean. `proxyMode = ScopedProxyMode.TARGET_CLASS` говорит spring использовать proxy-объект на
      основе класса целевого bean (класс, над которым висит аннотация `@Scope`), объект, который будет создан будет
      иметь тот же класс, что и целевой bean, но будет обёрнут в proxy для обеспечения правильной обработки запросов.
    * `@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)` - то же, что и
      выше, только будет создан один объект на одну сессию работы с пользователем, которая может включать в себя
      несколько запросов.
    * `@Scope` ???судя по всему??? создаёт на основе класса один proxy-объект, который под капотом маршрутизирует
      обращение к конкретному объекту данной session (request)

#### Примеры кода

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
* Пример реализации метода `WebMvcConfigurer.addInterceptors()`, он предоставляет возможность
  регистрации `Interceptors`, как общий (для всех путей), так и типизированный для путей
    - ```java
      @Override
      public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(loggingControllerInterceptor()); // для всех запросов
         registry.addInterceptor(loggingControllerInterceptor())
                 .addPathPatterns("/api/v1/client/**"); // для запросов на определённый паттерн пути
      }
      ```
