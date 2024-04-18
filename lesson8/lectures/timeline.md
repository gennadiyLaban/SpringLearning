#### 8.1 Основы безопасности
* `07:09` - схема работы `CORS`


#### 8.2 Основы Spring Security. Часть 1
* `00:41` - `Basic Authentication` схема
* `01:44` - создание проекта для `Basic Authentication`
* `02:28` - создание `SecurityConfiguration`. Пример использования `@EnableWebSecurity`, `@EnableMethodSecurity`
* `04:17` - создание IN_MEMORY `PasswordEncoder` 
* `05:00` - задание IN_MEMORY `UserDetailsService`
* `06:51` - создание IN_MEMORY `AuthenticationManager`
* `08:51` - создание `SecurityFilterChain` с основными настройками безопасности
* `10:03` - выключение `CSRF` - защиты... почему?...
* `13:33` - создание `PublicController`
* `15:24` - создание `UserController`


#### 8.2 Основы Spring Security. Часть 2
* `00:10` - создание `AdminController`
* `01:31` - реализация `adminGet` 
* `02:52` - задание credentials для `Basic Authentication` в `Header Authorization`
* `04:12` - задание ролей в `RoleType`
* `06:14` - создание `@Entity Role.class`
* `08:29` - создание `@Entity User.class`
* `09:17` - создание `@Repository UserRepository.class` и пример использования `@EntityGraph(attributePaths = {"roles"})`
* `11:03` - создание `UserService.class`
* `12:41` - создание `AppUserPrincipal.class` - реализации `UserDetails.class`
* `14:13` - создание `UserDetailsServiceImpl.class` - реализации `UserDetailsService.class`
* `14:51` - задание DB `PasswordEncoder` - `BCryptPasswordEncoder.class`
* `16:56` - задание DB `AuthenticationManager`
* `19:48` - создание `POST /account` endpoint для добавления новых пользователей `User.class`
* `21:09` - задание `application.yml` со свойством `spring.datasource.hikari.connection-init-sql: CREATE SCHEMA IF NOT EXISTS user_schema`
* `22:41` - проверка работы в `Postman`
* `24:44` - исправление `UserService.class` - добавление в новую запись `Role.class` сущности `User.class`


#### 8.4 OAuth 2.0. Протокол OAuth 2.0. Основные компоненты
* `:` - 


#### 8.5 Защита приложения с помощью JWT. Часть 1
* `02:31` - подключение зависимостей проекта
* `04:08` - настройка `application.yml` файла
* `06:16` - создание `RoleType.class`
* `07:56` - создание `User.class`
* `09:33` - создание `RefreshToken`, пример использования `@RedisHash("")`
* `11:15` - создание `UserRepository.class`
* `11:46` - создание `RefreshTokenRepository.class`
* `12:54` - создание `AppUserDetails.class` - реализацию `UserDetails.class`
* `15:19` - реализация `UserDetailsService.class`
* `18:33` - создание `RefreshTokenService.class`
* `21:32` - создание `JwtUtils.class` - класс для создания `access token`
* `24:02` - реализация `JwtUtils.validate(String authToken)` 
* `29:14` - создание `AuthResponse.class` - dto класс для ответа авторизации
* `29:14` - создание dto-классов `CreateUserRequest.class`, `LoginRequest.class`, `RefreshTokenRequest.class`, `RefreshTokenResponse.class`, `SimpleRequest.class`
* `33:39` - создание `SecurityService.class`. Реализация `SecurityService.authenticateUser(LoginRequest loginRequest)`
* `34:23` - реализация `SecurityService.register(CreateUserRequest loginRequest)`
* `36:40` - реализация `SecurityService.refreshToken()`
* `37:21` - реализация `SecurityService.logout()`
* `37:24` - реализация `SecurityService`


#### 8.5 Защита приложения с помощью JWT. Часть 2
* `00:25` - создание `JwtTokeFilter.class` - проверяет токен на валидность
* `03:55` - реализация `JwtTokeFilter.class` - проверка jwtToken, создание на его основе authentication и заполнение SecurityContextHolder.getContext().setAuthentication(authentication)
* `04:25` - создание `JwtAuthenticationEntryPoint.class` - реализация `AuthenticationEntryPoint.class`
* `07:22` - реализация `JwtAuthenticationEntryPoint.class`
* `07:45` - создание `@Configuration SecurityConfiguration.class`
* `09:13` - предоставление `@Bean DaoAuthenticationProvider.class`
* `09:31` - предоставление `@Bean AuthenticationManager`
* `11:26` - настройка `@Bean SecurityFilterChain`
* `12:52` - создание `@Configuration RedisConfiguration`
* `13:38` - использование клиента `Jedis` для настройки `Redis` (`JedisConnectionFactory`)
* `16:26` - пример использования `KeyspaceConfiguration.class` и `@EnableRedisRepositories`
* `16:58` - создание `RedisExpirationEvent.class` - пример слушателя события `RedisKeyExpiredEvent<RefreshToken> event`
* `22:11` - создание `AuthController.class`
* `25:37` - реализация `AuthController.class`
* `26:00` - создание `AppController.class`
* `28:30` - реализация `docker-compose.yml`
* `31:00` - проверка работы через `Postman`


#### 8.7 Авторизация во внешних системах
* `01:45` - создание `WebClientConfiguration.class`
* `02:27` - создание `BasicAuthClient.class`
* `02:50` - создание `AuthRequest.class`
* `04:20` - создание `TokenResponse.class`
* `04:32` - создание `AppController.class`
* `07:58` - создание `TokenProvider.class`
* `11:00` - реализация `TokenProvider.class`, работа с методами `cache()` для `Mono<>`. В решении есть проблема: многопоточность запросов
* `13:28` - создание `WebClient` с подстановкой авторизации из `TokenProvider.class`
* `15:05` - реализация `WebClient` с подстановкой авторизации из `TokenProvider.class`
* `16:22` - создание `JwtAuthClient`


#### 8.8 Тестирование с Spring Security
* `01:14` - создание `AbstractTest.class`
* `04:48` - реализация `AbstractTest.class`
* `06:37` - создание и реализация `UserControllerTest.class`, пример использования `@WithMockUser`
* `08:58` - пример использования `@WithUserDetails`


#### 8.9 Reactive Spring Security
* `02:06` - создание `SecurityConfiguration.class`, пример использования `@EnableWebFluxSecurity` и `@EnableReactiveMethodSecurity`
* `05:16` - реализация IN_MEMORY `SecurityConfiguration.class`
* `06:27` - создание `PublicController.class`
* `07:40` - создание `UserController.class`
* `09:00` - создание `AdminController.class`
* `10:00` - проверка работы через `Postman`
* `11:01` - включение зависимостей для БД
* `13:22` - реализация `@Entity Role.class`
* `14:33` - реализация `@Entity User.class`
* `15:26` - реализация `UserRepository.class`
* `16:57` - реализация `AppUserPrincipal.class`
* `17:10` - создание `ReactiveUserDetailsServiceImpl.class` - реализация интерфейса `ReactiveUserDetailsService.class`
* `18:00` - создание `UserService.class`
* `18:46` - реализация `UserService.findByUsername()`
* `19:46` - реализация `ReactiveUserDetailsServiceImpl.class`
* `23:18` - реализация `PublicController.createUserAccount()`
* `23:35` - задание свойств в `application.yml`
* `24:53` - задание DB `@Bean ReactiveAuthenticationManager` в `SecurityConfiguration.class`
* `25:26` - задание DB `@Bean SecurityWebFilterChain` в `SecurityConfiguration.class`
* `26:23` - проверка работы в `Postman`
