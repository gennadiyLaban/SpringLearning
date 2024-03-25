#### OpenAPI

* `OpenAPI` - набор спецификаций для описания и документирования веб-сервисов RESTful API. Обычно используется
  формат `JSON` или `YAML`
* подключение `org.springdoc.springdoc-openapi-starter-webmvc-ui:2.0.4` позволяет автоматически генерировать страничку с
  описанием api по пути `/swagger-ui/index.html`
* `Server.class` - хранит информацию о сервере (url, description), серверов может быть несколько, например тестовый и
  продовый
* `Contact.class` - хранит информацию о контактах, к кому можно обратиться в случае вопросов.
* `License.class` - хранит информацию о лицензии
* `Info.class` - хранит такую информацию, как title, version, contact (`Contact.class`), description, termsOfService,
  licence (`License.class`)
* `OpenAPI.class` - итоговый объект-контейнер для описания api, принимает в себя `Info.class`, `Server.class`. Его нужно
  сконструировать в классе конфигурации (`@Configuration`) и представить, как bean (`@Bean`)
* `@Tag(name="Client v1", description="Client API version V1")` - аннотация ставиться над контроллером для того, чтобы
  определить дополнительную информацию о контроллере, который содержит методы api. Методы api группируются по
  контроллерам.
* `@Operation(summary="some summary", description="some description" tags = {"tag1", "tag2"}) - `добавляет описание
  отдельного метода контроллера, в частности:

- `summary` - краткое описание того, что делает метод
- `description` - развёрнутое описание того, что делает метод
- `tags` - тэги для добавления групп-тегов, где будут те же методы, что и в группировке по контроллерам, но они будут
  объединены под одним из тегов.

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
