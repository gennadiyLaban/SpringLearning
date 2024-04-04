#### 6.1 Очереди. Стриминговые системы. Event-Driven


#### 6.2 Apache Kafka


#### 6.3 Kafka Consumer. Kafka Producer
* `03:18` - создание `KafkaMessageService`
* `04:18` - задание параметров в `application.yml`
* `08:10` - создание `@Bean ProducerFactory<String, KafkaMessage>` в `@Configuration KafkaConfiguration`
* `08:54` - создание `@Bean KafkaTemplate<Key, Value>`
* `11:24` - создание `@Bean ConsumerFactory<Key, Value>`
* `12:29` - полная реализация `@Configuration KafkaConfiguration`
* `14:52` - создание `KafkaMessageListener`
* `19:17` - реализация `KafkaMessageListener`
* `21:59` - реализация `KafkaController`, где показано, как передавать сообщения в `Kafka`
* `26:34` - docker-compose.yml для `zookeeper` и `kafka`
* `29:33` - docker-start.sh - скрипт запуска контейнеров


#### 6.4 Тестирование Apache Kafka
* `00:34` - зависимости для подключения `Testcontainers` для `Kafka`
* `03:29` - создание `KafkaMessageListenerTest.java`
* `06:50` - пример работы библиотеки `awaitiltiy` 
* `07:34` - объяснение теста в `KafkaMessageListenerTest.java`
