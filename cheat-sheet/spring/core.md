#### Spring Core

* `@ConditionalOnExpression("${app.check-client-api-header:true}")` - возможность "включать/выключать" компонент по
  условному выражению, в данном случае значение берётся из свойств приложения
* `@Value("${app.client-api-key")` - считывает значение свойства в поле класса

#### Примеры

* Чтение значения поля сразу в поле класса:
    - ```java
        @Value("${app.client-api-key")
        private String clientApiKey;
      ``` 
* 
