#### 8.1 Основы безопасности
* `Безопасность веб-приложений` - процесс обеспечения защиты веб-приложений от различных угроз и атак, таких как несанкционированный доступ, подделка запросов, утечка данных и многое другое. Основыми аспектами безопасности веб-приложений являются аутентификация, авторизация, управление доступом и обработка данных.
* `Аутентификация` - процесс проверки пользователя. Аутентификация гарантирует, что пользователь является тем, за кого себя выдаёт, путём проверки предоставленных учётных данных (обычно логин и пароль). Т.е. когда вы заходите на сайт, используя логин и пароль, вы проходите процедуру аутентификации.
* `Авторизация` - процесс предоставления прав доступа к ресурсам и функциональности приложения на основе аутентификации пользователя. Авторизация определяет, какие действия и ресурсы доступны для конкретного пользователя. Т.е. когда вы делаете какие-либо действия в системе, перед разрешением произвести эти действия вы проходите процедуру авторизации.
* `Роли` - это группы прав доступа, которые могут быть присвоены пользователям. Например, "пользователь", "администратор", "модератор". Роли определяют уровень доступа к различным частям приложения
* `Разрешения` - это конкретные действия или ресурсы, к которым у пользователя есть доступ. Роли могут содержать разрешения и пользователи получают доступ на основе назначенных им ролей.
* `Виды атак`:
  - `SQL Injection (SQLi)` 
    + Определение: вид атаки, при которой злоумышленник внедряет SQL-код в пользовательский ввод, чтобы выполнить нежелательные запросы к базе данных.
    + Защита: использование параметризованных запросов (Prepared Statements) или ORM-систем, которые автоматически обрабатывают параметры запросов
  - `Cross-Site Scripting (XSS)`
    + Определение: злоумышленник внедряет в веб-страницу вредоносный скрипт, который выполняется в браузере пользователя.
    + Защита: экранирование вывода, использование Content Security Police (CSP), валидация ввода
  - `Cross-Site Request Forgery (CSRF)`
    + Определение: атака, при которой злоумышленник заставляет пользователя случайно выполнить действия в своей учётной записи на сайте.
    + Защита: использование уникальных CSRF-токенов, SameSite Cookies, Double Submit Cookie
  - `Insecure Deserialization`
    + Определение: злоумышленник изменяет или внедряет вредоносные данные в сериализованные объекты, которые затем десериализуются.
    + Защита: ограничение десериализации, валидация данных, использование библиотек с механизмами безопасной десериализации
  - `Broken Authentication`
    + Определение: уязвимость, связанная с недостаточной защитой механизмов аутентификации и авторизации, что позволяет злоумышленникм получать доступ к аккаунтам пользователей
    + Защита: использование сильных хеш-функций и алгоритмов шифрования для паролей, двухфакторная аутентификация
  - `SecurityMisconfiguration`
    + Определение: неправильная конфигурация сервера, базы данных или приложения, которая может раскрывать уязвимости.
    + Защита: строгая настройка серверов и приложений, регулярные аудиты конфигурации.
  - `Sensitive Data Exposure`
    + Определение: открытие конфиденциальных данных, таких как пароли или номера кредитных карт.
    + Защита: хранение конфиденциальных данных в хэшированном или зашифрованном виде, использование HTTPS
  - `XML External Entity (XXE) Attack`
    + Определение: злоумышленник внедряет вредоносный XML-код, который обрабатывается небезопасно, что может привести к разглашению конфиденциальной информации.
    + Защита: отключение обработки внешних сущностей, использование безопасных парсеров XML
  - `Broken Access Control`
    + Определение: недостаточная проверка прав доступа, что может привести к получению несанкционированной информации или выполнению нежелательных действий.
    + Защита: строгая система авторизации, правильная проверка прав доступа
* `Протоколы безопасности`:
  - `OAuth 2.0` - протокол для делегирования доступа третьей стороне без предоставления своих учётных данных. Используется для авторизации на сторонних сервисах, таких как социальные сети
  - `OpenID Connect` - расширение протокола OAuth 2.0, которое позволяет провайдеру идентичности верифицировать личность пользователя. Вы можете думать об этом, как о карточке удостоверения личности в виртуальном мире. Это система, которая позволяет вам войти в ваши аккаунты безопасно, используя вашу учётную запись от провайдера идентификации (например, Google, VK или другие), которую вы уже создали
  - `SALM (Security Assertion Markup Language)` - протокол для обмена информацией о безопасности между идентичностью верифицирующим поставщиком и службами (х*ня какая-то, а не определение)
  - `JWT (JSON Web Token)` - открытый стандарт (RFC 7519), использующий JSON для передачи информации между сторонами в виде объекта. JWT используется для передачи утверждений между клиентом и сервером. (будет использовать в практической работе)
* `CORS (Cross-Origin Resource Sharing)` - это механизм веб-безопасности, который позволяет веб-приложениям запрашивать ресурсы (например, данные или скрипты) с другого домена, отличающегося от источника, с которого была загружена страница. Браузеры применяют политику безопасности одного источника (Same-Origin Policy), которая ограничивает взаимодействие между веб-страницами на разных доменах. Однако CORS позволяет разрешать специфическим доменам доступ к ресурсам через HTTP-заголовки.
* `CORS`-заголовки:
  - `Origin` - определяет, откуда пришёл запрос
  - `Access-Control-Allow-Origin` - указывает, какие домены могут получать доступ к ресурсу. Может быть конкретным доменом, `*`, или списком разрешённых доменов
  - `Access-Control-Allow-Methods` - определяет разрешённые методы HTTP для доступа к ресурсу
  - `Access-Control-Allow-Headers` - указывает, какие заголовки могут быть доступны при чтении ответа
  - `Access-Control-Allow-Credentials` - определяет, разрешено ли передавать учётные данные в запросе
  - `Access-Control-Max-Age` - устанавливает время (в секундах), на протяжении которого браузер может кэшировать ответы `CORS`
* `Spring Security` - это мощный фреймворк безопасности для Java-приложений, предоставляющий разнообразные средства для обеспечения аутентификации, авторизации и защиты от различных угроз в веб-приложениях. Он позволяет разработчикам легко интегрировать функции безопасности в свои приложения, обеспечивая защиту от несанкционированного доступа и утечек данных.
  - `Аутентификация и авторизация` - `Spring Security` обеспечивает механизмы аутентификации пользователей (проверка подлинности) и авторизации (предоставление доступа к ресурсам и функциональности)
  - `Фильтры безопасности` - `Spring Security` использует цепочку фильтров для обработки запросов и реализации различных функций безопасности, таких как проверка подлинности, авторизация, защита от CSRF и другие
  - `UserDetailsService` - интерфейс для загрузки пользовательских данных из источника, такого как база данных, их проверка и предостваление `Spring Security`
  - `PasswordEncoder` - интерфейс для шифрования паролей пользователей, чтобы они не хранились в открытом виде
  - `SecurityConfigurer` - классы для конфигурирования различных аспектов безопасности, таких как аутентификация, авторизация, CORS, CSRF и другие
  - `Аннотации безопасности` - `Spring Security` предоставляет аннотации, такие как Secured, PerAuthorize, PostAuthorize и др., для настройки безопасности на уровне методов
  - `Интеграция с разными источниками аутентификации` - `Spring Security` поддерживает аутентификацию через базу данных, LDAP, OAuth, OpenID Connect и другие протоколы
  - `Поддержка REST- и WEB-приложений` - `Spring Security` предоставляет возможности безопасности как для традиционных WEB-приложений, так и для REST-сервисов
  - `Защита от атак` - `Spring Security` обеспечивает защиту от атак, таких как инъекции, CSRF (межсайтовая подделка запроса) и другие


#### 8.2 Основы Spring Security. Часть 1
* `Basic Authentication` - это метод аутентификации в WEB-приложениях, при котором клиент (обычно WEB-браузер) отправляет логин и пароль в виде закодированной строки в заголовке `Authorization` HTTP-запроса. Сервер в свою очередь проверяет переданные учётные данные и разрешает доступ к ресурсам, если аутентификация успешна. **НЕ ЯВЛЯЕТСЯ БЕЗОПАСНОЙ!**. 
* `@EnableWebSecurity` - (над `@Configuration`) - активирует и настраивает базовую конфигурацию web-безопасности приложения. Внутри класса `@Configuration`, который дополнительно аннотирован `@EnableWebSecurity` можно настраивать конфигурацию web-безопасности
  * `@EnableMethodSecurity` - (над `@Configuration`) - активирует поддержку безопасности на уровне методов, позволяет использовать такие аннотации, как `@PreAuthorize`, `@PostAuthorize`, `@Secured` для определения правил безопасности непосредственно в коде приложения, например какие пользователи с какими ролями имеют доступ к конкретному методу класса.
* `@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")` - метод (контроллера) доступен только тем пользователям, у которых есть роль `USER` или `ADMIN` (префикс `ROLE_` - стандартный?). Может быть заменой настройки в `SecurityFilterChain`.
* `@PostAuthorize` - 
* `@Secured` - 
* `PasswordEncodeer` - базовый интерфейс, задание которого определяет, как именно будут храниться `password`, зашифровано, захешировано или без шифрации/хеширования 
* `UserDetailsService` - отвечает за подгрузку данных пользователей. 
* `SecurityFilterChain` - задаёт основные настройки безопасности (какие запросы с какими ролями можно использовать, включение/выключение CSRF, задание способа authentication, задания политики управления сессиями)
* `@AuthenticationPrincipal UserDetails userDetails` - указывает, что в метод должны быть переданы данные вызывающего метод юзера


#### 8.2 Основы Spring Security. Часть 2
* `Authorization` - `Header` для передачи данный для authorization. 
- Для задания `Basic Authentication` нужно передать значение `Basic ` + `<username>:<password>`, где часть `<username>:<password>` записана в `Base64`
* `@Enumerated` - показывает `JPA`, что тип поля - `enum`
- `@Enumerated(value = EnumType.STRING)` - задаёт тип записи значения в колонку как `STRING`
* `GrantedAuthority.class` - используется `SpringSecurity` (видимо для проверки прав)
- `SimpleGrantedAuthority.class` - простая реализация `GrantedAuthority.class`, в конструктор которого передаётся имя `authority` (`RoleType.class`)
* `BCryptPasswordEncoder.class` - задаёт хеширование для паролей, принимает в конструктор количество раундов хеширования - чем больше раундов, тем "прочнее" хеширование, но тем больше нужно времени на дешифровку
* `DaoAuthenticationProvider.class` - 


#### 8.4 OAuth 2.0. Протокол OAuth 2.0. Основные компоненты
* `OAuth 2.0` - это открытый стандарт для авторизации и аутентификации, предназначенный для обеспечения безопасного доступа к ресурсам веб-приложений и API. Он представляет сторонним приложениям ограниченный доступ к защищённым ресурсам от имени пользователя без необходимости раскрытия учётных данных.
* Основные компоненты `OAuth 2.0`:
  - `Resource Owner` (владелец ресурса) - это пользователь, который владеет защищёнными ресурсами, к которым требуется доступ
  - `Resource Server` (сервер ресурсов) - это сервер, который хранит и предоставляет доступ к защищённым ресурсам. Он проверяет предоставленные токены и определяет, имеет ли клиент право доступа к запрашиваемому ресурсу
  - `Client` (клиент) - это приложение, которое запрашивает доступ к защищённым ресурсам от имени пользователя. Клиент может быть веб-приложением, мобильным приложением, сервером или другой сторонней службой
  - `Authorization Server` (сервер авторизации) - это сервер, ответственный за аутентификацию пользователей и предоставление разрешений на доступ к защищённым ресурсам. Он выдаёт токены доступа и, при необходимости, токены обновления.
  - `Authorization Grant` - это разрешение, предоставляемое пользователем, чтобы клиент мог получить доступ к его ресурсам
  - `Access Token (токен доступа)` - это токен, который выдаётся клиенту сервером авторизации и позволяет клиенту получить доступ ка защищённым ресурсам пользователя. Токен содержит информацию о разрешениях и сроке действия
  - `Refresh Token (токен обновления)` - это токен, который используется для получения нового `Access Token` без необходимости повторной аутентификации. Он обменивается на новый токен, когда текущий токен протухает
  - `Scope (область)` - это параметр, указывающий, на какие ресурсы и с какими разрешениями клиент запрашивает доступ. Область определяет, какие данные и действия разрешены
  - `Redirect URI (URI перенаправления)` - URL, на который пользователь будет перенаправлен после успешной аутентификации и выдачи разрешения. Клиент должен предоставить корректный URL для получения кода авторизации или токенов
  - `Token Endpoint` - это `endpoint` на сервере авторизации, куда клиент отправляет запрос для обмена кода авторизации на `Access Token`
 * Виды потоков авторизации `OAuth 2.0`:
   - `Authorization Code Flow` - используется для приложений с серверным взаимодействием. После аутентификации пользователя на сервере авторизации клиентское приложение получает авторизационный код, который обменивается на `Access Token` и `Refresh Token`
   - `Implicit Flow` - применяется для веб-приложений. `Access Token` выдаётся непосредственно веб-приложению через URL-параметр, после чего браузер редиректится на страницу клиента
   - `Rsource Owner Password Credentials Flow` - позволяет приложению использовать логин и пароль пользователя для получения токена напрямую. Этот поток не рекомендуется, т.к. он включает передачу учётных данных
   - `Client Credentials Flow` - применяется для аутентификации приложения, а не конкретного пользователя. Приложение использует свои собственные учётные данные для получения `Access Token`
*  `JSON Web Token (JWT)` - это структура, которая представляет собой компактный и самодостаточный способ передачи информации между двумя сторонами в виде объекта `JSON`. `JWT` часто используется для передачи утверждений о пользователе между сервером и клиентом, особенно в контексте аутентификации и авторизации.
* Компоненты `JWT`:
  - Заголовок (`Header`) - содержит тип токена и используемый алгоритм шифрования
  - Полезная нагрузка (`Payload`) - содержит набор клэймсов (`claims`), который предоставляют информацию о пользователе или другие данные
  - Подпись (`Signature`) - получается путём кодирования заголовка и полезной нагрузки с использованием секретного ключа, что позволяет проверить подлинность токена
*  `JWT Refresh Token` - дополнительный токен в протоколе `OAuth 2.0`, который предоставляется вместе с `Access Token`, он позволяет обменять его на новый `Access Token`, чтобы заново не проходить процесс аутентификации


#### 8.5 Защита приложения с помощью JWT. Часть 1
* Соединения таблиц в JPA без аннотаций типа `@ManyToOne`

```java
import java.util.HashSet;

@Entity
public class User {
  @Id
  @GeneratedValue(startegy = GeneratedType.IDENTITY)
  private Long id;

  @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "roles", nullable = false)
  @Builder.Default
  private final Set<RoleType> roleTypeSet = new HashSet<>();
}
```
* `@RedisHash("refresh_token")` - сущность должна быть сохранена в `Redis`, как hash-структура, где `refresh_token` - имя хеша, в котором будут храниться данные из сущности
* `@*.redis.core.index.Indexed` - поле будет использоваться для поиска и индексироваться, это позволяет быстро искать сущность по полю
* `Jwts.class` - 
  * `Jwts.builder()` - создаёт builder для построения токена
  * `Jwts.Builder.setSubject()` - `subject` токена, который обычно представляет собой идентификатор пользователя
  * `Jwts.Builder.setIssuedAt()` - время выдачи токена
  * `Jwts.Builder.setExpiration()` - время протухания токена
  * `Jwts.Builder.signWith(SignatureAlgorithm.HS512, String jwSecret)` - алгоритм шифрования + `secret key`
  * `Jwts.Builder.compact()` - тут переводит объект в строковое представление
  * `Jwts.parser()` - создаёт `parser` для токена
  * `Jwts.Parser.setSigningKey(String jwtSecret)` - задаёт `secret key` для `parser`
  * `Jwts.Parser.parseClaimsJws(String token)` - парсит токен на `claims`
  * `Jwts.Claims.getBody().getSubject()` - позволяет получить `subject`, который мы задали в `Jwts.Builder.setSubject()` при генерации токена
* Пример `authentication`:
```java
@RequiredArgsConstructor
@Service
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        
        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }
}
```


#### 8.5 Защита приложения с помощью JWT. Часть 2
* `UsernamePasswordAuthenticationToken.class` - класс-реализация для Authentication
* `AuthenticationEntryPoint.class` - 
  - `AuthenticationEntryPoint.commence()` - вызывается, когда запрос пытается получить доступ к защищённому ресурсу, но попытка авторизации не проходит. Здесь можно подготовить ответ на эту ошибку
*  Пример конфигурации `SecurityFilterChain.class`
```java
@Configuration
public class SecurityConfiguration {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      
      authenticationProvider.setUserDetailsService(userDetailsService);
      authenticationProvider.setPasswordEncoder(passwordEncoder);
      
      return authenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            DaoAuthenticationProvider authenticationProvider,
            JwtTokeFilter jwtTokeFilter
    ) {
        http.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/app/**").permitAll()
                .anyRequest().authenticated()
        )
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPlolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtTokeFilter, UsernamePasswordAuthenticationFilter.class); // выполнение фильтра до выполнения стандартной аутентификации по имени пользователя и паролю
        
        return http.build();
    }
}
```
* `JedisConnectionFactory.class` - ещё один клиент для работы с `Redis`
* `KeyspaceConfiguration.class` - позволяет настроить время жизни значений в пространстве ключей (коллекции?)
* `@EnableRedisRepositories` - 
  - `@EnableRedisRepositories(keyspaceConfiguration = MyKyespaceConfiguration.class)` - устанавливаем класс-источник конфигурации, наследника `KeyspaceConfiguration.class`
  - `@EnableRedisRepositories(enableKeySpaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)` - события `Events` со значениями из пространства, заданного в `MyKyespaceConfiguration.class` будут включены на старте приложения

```java
import java.time.Duration;
import java.util.Collections;

@EnableRedisRepositories(
        keyspaceConfiguration = RedisConfiguration.MyKyespaceConfiguration.class,
        enableKeySpaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP
)
@Configuration
public class RedisConfiguration {
  @Value("${app.jwt.refreshTokenExpiration}")
  private Duration refreshTokenExpiration;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
    RedisStandalonConfiguration configuration = new RedisStandalonConfiguration();

    configuration.setHostName(redisProperties.getHost());
    configuration.setPort(redisProperties.getPort());

    return new JedisConnectionFactory(configuration);
  }

  public class MyKeyspaceConfiguration extends KeyspaceConfiguration {
    private static final String REFRESH_TOKEN_KEYSPACE = "refresh_token";

    @Override
    protected Iterable<KeyspaceSettings> initialConfiguration() {
      KeyspaceSettings keyspaceSettings = new KeyspaceSettings(RefreshToken.class, REFRESH_TOKEN_KEYSPACE);

      keyspaceSettings.setTimeToLive(refreshTokenExpiration.getSeconds());

      return Collections.singleton(keyspaceSettings);
    }
  }
}
```
* `JWT` авторизация происходит путём добавления в header `Authorization` значения `Bearer <jwt_token>`


#### 8.7 Авторизация во внешних системах


#### 8.8 Тестирование с Spring Security
* `@WithMockUser(username = "user", roles = {"USER"})` - позволяет замокать authority для вызова метода
* `@WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "testUser", setupBefore = TestExecutionEvent.TEST_EXECUTION)` - указывает имя бина `UserDetailsService.class`, `value` указывает имя юзера, которое будет запрашиваться; `setupBefore` - определяет момент, когда установка аутентификации будет выполняться (перед запуском теста, но после установки юзера в базе данных)


#### 8.9 Reactive Spring Security
* В реальных приложениях нужно использовать неблокирующий драйвер `Postgresql`
* `@EnableWebFluxSecurity` - 
* `@EnableReactiveMethodSecurity` - 
* `ServerHttpSecurity.class` - похоже на потоковую реализацию `HttpSecurity`
* `ReactiveUserDetailsService` - похоже на такую же реализацию для непотокового способа


#### 8.10 Практическая работа
* `RoleHierarchy` - The simple interface of a role hierarchy.
* `@PreAuthorize` и `@PostAuthorize` позволяют использовать любые `@Component` в выражениях проверки прав
```java
@Component
public class AuthComponent {
    public boolean hasPermission(User user, Long id) {
        // do whatever checks you want here
        return someResult;
    }
}

@RequireArgsConstructor
@RestController
@RequestMapping("/api/v1/foo")
public class FooController {
    private final FooService fooService;

    @PreAuthorize("@authComponent.hasPermission(#authUser, #userId)")
    @GetMapping("/{id}")
    public ResponseEntity<FooDTO> fooById(
            @PathVariable @NotNull Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
      return ResponseEntity.ok(fooService.getFooDTOById(id));
    }
}
```
