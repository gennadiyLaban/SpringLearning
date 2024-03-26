#### Spring Tests

* `@SpringBootTest` - указывает, что тест является интеграционным, при его выполнении нужно загружать весь контекст
  приложения.
* `@AutoConfigureMockMvc` - автоматическое создание и внедрение экземпляра `MockMvc.class`
* `MockMvc` - эмулирует отправку http-запроса и позволяет проверить ответ контроллера.
* `ObjectMapper` - основной класс библиотеки `Jackson` - библиотека преобразования JSON (и других протоколов) в
  Java-объекты и обратно.
* `@MockBean` - создание заглушки-bean в выполняемом тесте

#### Примеры: