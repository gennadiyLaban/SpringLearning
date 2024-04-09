#### 7.1 Основы Spring. Реактивное программирование в Spring

#### 7.2 Spring WebFlux
* `03:58` - создание проекта
* `05:22` - создание `SubItemModel` и `ItemModel`
* `07:41` - создание `ItemHandler`
* `14:45` - реализация `ItemHandler`
* `16:02` - создание `@Configuration ItemRouter`
* `18:09` - реализация `ItemRouter.itemRoute(): RouterFunction<ServerResponse>`
* `18:37` - создание `RouterErrorConfiguration`
* `21:13` - проверка работы в `Postman`


#### 7.3 MongoDB
* `09:16` - создание `docker-compose.yml` для `MongoDB`
* `10:18` - подключение к контейнеру `MongoDB` через консоль


#### 7.4 Написание приложения с MongoDB и WebFlux
* `01:03` - подключение зависимости `org.springframework.boot:spring-boot-starter-data-mongodb-reactive`
* `01:47` - создание `Item.class` - `@Document` для `MongoDB`
* `03:49` - реализация `Item.class`
* `06:57` - создание `SubItem.class`
* `08:15` - создание `ReactiveMongoRepository<EntityType, KeyType>`
* `09:58` - создание `ItemService.class`
* `14:04` - создание `ItemController.class`
* `20:19` - настройка подключения `MongoDB` в `application.yml`
* `20:54` - настройка контейнера в `docker-compose.yml`
* `24:52` - создание `ItemUpdatesPublisher.class`, использование `Sinks`
* `26:16` - реализация `ItemUpdatesPublisher.class`
* `27:53` - пример использования `ItemUpdatesPublisher.publish()`
* `29:07` - пример создания стримменговых обновлений через `WebFlux` в `ItemController.class`
* `30:08` - проверка стримминга в `Postman`


#### 7.5 Тестирование реактивных компонентов
* `00:56` - подключение зависимостей
* `06:05` - создание `AbstractTest.class`
* `07:14` - создание `ItemControllerTest.class`
* `09:44` - реализация теста и пример использования `WebTestClient`
* `17:01` - пример теста стримменга `Item` 
* `:`
