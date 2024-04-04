### Kafka in Spring
* `org.springframework.kafka:spring-kafka:3.1.2` - зависимость для подключения библиотеки взаимодействия с Kafka
* `spring.kafka.bootstrap-servers: localhost:9092` - свойство задаёт `host/port` для подключения к `Kafka`
* `ProducerFactory<Key, Value>` - класс для создания фабрики `Producer`
* `KafkaTemplate<Key, Value>` - облегчает передачу сообщений в `Kafka` (что бы это ни значило). Через него отправляются сообщения в очередь
* `ConsumerFactory<Key, Value` - класс для создания фабрики `Consumer`
* `ConcurrentKafkaListenerContainerFactory<Key, Value>` - с помощью него мы слушаем `Kafka`
* `@KafkaListener` - аннотация над методом компонента (`@Component`) указывает, что этот метод-listener должен вызываться при получении сообщения (определённого топика и группы) из `Kafka`
  - `@KafkaListener(topics = "{app.kafka.kafkaMessageTopic})"` - указывает, какой топик нужно слушать
  - `@KafkaListener(groupId = "{app.kafka.kafkaMessageGroupId})"` - указывает, какую группу нужно слушать
  - `@KafkaListener(containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory"` - указывает, как называется `@Bean ConcurrentKafkaListenerContainerFactory<String, KafkaMessage>`, который должен использоваться для прослушивания сообщений. Задаётся имя `bean`, в данном случае - имя метода в `@Configuration`
* `@Payload` - в методе, помеченном `@KafkaListener` эта аннотация означает, что аргумент является значением сообщения
* `@Header` - в методе, помеченном `@KafkaListener` эта аннотация означает, что аргумент является значение `header` в запросе сообщения
    - `@Header(required = false)` - означает, что значение необязательно, т.е. `@Nullable`
    - `@Header(value = KafkaHeaders.RECEIVED_KEY)` - предоставление уникального uid сообщений (`UUID.class`)
    - `@Header(value = KafkaHeaders.RECEIVED_TOPIC)` - имя считываемого топика (`String.class`)
    - `@Header(value = KafkaHeaders.RECEIVED_PARTITTION)` - номер партиции (`Integer.class`)
    - `@Header(value = KafkaHeaders.RECEIVED_TIMESTAMP)` - время отправки (регистрации в топике?) сообщения (`Long.class`)


### Примеры
#### Конфигурация Kafka в Spring
* В `java-code`:
```java
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${app.kafka.kafkaMessageGroupId}")
  private String kafkaMessageGroupId;

  @Bean
  public ProducerFactory<String, KafkaMessage> kafkaMessageProducerFactory(ObjectMapper objectMapper) {
    Map<String, Object> config = new HashMap<>();

    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // из пакета org.apache.kafka.common.serialization
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // из пакета org.springframework.kafka.support.serializer

    return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>(objectMapper));
  }

  @Bean
  public KafkaTemplate<String, KafkaMessage> kafkaTemplate(ProducerFactory<String, KafkaMessage> kafkaMessageProducerFactory) {
    return new KafkaTemplate<>(kafkaMessageProducerFactory);
  }

  @Bean
  public ConsumerFactory<String, KafkaMessage> kafkaMessageConsumerFactory(ObjectMapper objectMapper) {
    Map<String, Object> config = new HashMap<>();
    
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // из пакета org.apache.kafka.common.serialization
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // из пакета org.springframework.kafka.support.serializer
    config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaMessageGroupId);
    config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    
    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(objectMapper));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaMessageConcurrentKafkaListenerContainerFactory(
          ConsumerFactory<String, KafkaMessage> kafkaMessageConsumerFactory
  ) {
      var factory = new ConcurrentKafkaListenerContainerFactory<String, KafkaMessage>();
      factory.setConsumerFactory(kafkaMessageConsumerFactory);
      
      return factory;
  }
}
```
* В `application.yml`:
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
#      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
#      bootstrap-servers: localhost:9092
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      group-id: "kafka-order-group-id"
      properties:
        spring.json.trusted.packages: "org.laban.learning.spring.lesson6.common.kafkamessage"
```
#### Конфигурация Kafka для Docker Compose
* `docker-compose.yml`
```yaml

version: '3.9'
services:
  zookeeper:                                # нужен для управления метаданными kafka
    image: confluentinc/cp-zookeeper:6.2.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181           # порт для коннекта с zookeeper
    ports:
      - "2181:2181"
  
  kafka:
    image: confluentinc/cp-kafka:6.2.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1                      # уникальный id брокера в Kafka-кластере
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181 # контейнер и порт подключения к zookeeper
      KAFKA_LOG4J_LOGGERS: "kafka.controller=INFO, kafka.producer.async.DefaultEventHandler=INFO, state.change.logger=INFO"  # настройка уровней логирования 
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://$DOCKERHOST:9092    # адрес и порт по который будет объявлен в метаданных Kafka и будет доступен для клиентов. $DOCKERHOST - переменная, задаваемая через скрипт, через который запускаем контейнеры
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT   # определяет пары ключ-значение для использования в протоколах безопасности для каждого имени прослушивателя. В данном случае используется простой протокол безопасности PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1                   # указывает сколько реплик будет создано для топика (топиков?)
      KAFKA_AUTO_CREATE_TOPIC_ENABLE: 'true'                      # следует ли автоматически создавать топики при отправке сообщений на несуществующий топик
```
* `docker-start.sh`:
```shell
#!/bin/sh
export DOCKERHOST-$(ifconfig | grep -E "([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -v 127.0.0.1 | awk '{ print $2 }' | cut -f2 -d | head -n1)
docker compose -f docker-compose.yml up
```
