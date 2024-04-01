#### Spring Core

* `@ConditionalOnExpression("${app.check-client-api-header:true}")` - возможность "включать/выключать" компонент по
  условному выражению, в данном случае значение берётся из свойств приложения
* `@Value("${app.client-api-key")` - считывает значение свойства в поле класса
* `@ConfigurationProperties` - показывает, что класс является репрезентацией и хранилищем пропертей с каким-либо
  префиксом
  - `@ConfigurationProperties(prefix = "app.cache")` - показывает, что данный класс хранит property
    префиксом `app.cache`
* `@EnableConfigurationProperties(FooProperties.class)` - разрешает использование `FooProperties.class`, регистрирует
  его, автоматически парсит и создаёт компонент для inject. Аннотация должна размещаться над `@Configuration`
* `@ConditionalOnExpression` - позволяет задать условное выражение, которое будет разрешать/запрещать
  создание/использование `@Bean` или `@Component`

#### Примеры

* Чтение значения поля сразу в поле класса:
    - ```java
        @Value("${app.client-api-key")
        private String clientApiKey;
      ``` 
* 
