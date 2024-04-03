#### 6.1 Очереди. Стриминговые системы. Event-Driven
* Очереди, когда применять
  - Обработка задач - разделение задач и отправка их в очереди
  - Асинхронные события - сервисы могут разделять задачи, требующие больше времени на обработку, и отправлять их в очередь
  - Распределённые вычисления - одни сервисы публикуют задачи на выполнение, другие - забирают задачи из очереди и возвращают результат
  - Уведомления и оповещения - сервисы могут использовать очереди для отправки уведомлений и оповещений другим сервисам или пользователям
  - Управление состоянием - очереди могут быть использованы для управления состоянием и синхронизацией данных между разными микросервисами
* Виды очередей:
  - `RabbitMQ` - это распределённый и горизонтально масштабируемый брокер сообщений, который обеспечивает надёжную и масштабируемую асинхронную коммуникацию между сервисами. Он обладает широкими возможностями по настройке обменов, маршрутизации сообщений и обработке событий.
  - `Kafka` - это распределённая платформа для потоковых данных и событий, которая также может использоваться в качестве системы очередей. Он предоставляет высокую пропускную способность и масштабируемость, что делает его подходящим для обработки больших объёмов данных и стриминга событий между сервисами.
  - `ActiveMQ` - система обмена сообщениями с открытым исходным кодом, которая обеспечивает высокую доступность, надёжность и поддерживает разнообразные функции маршрутизации и обработки сообщений.
  - `NATS` - это быстрая и простая система сообщений, предоставляющая асинхронную коммуникацию. NATS часто используется для передачи событий в микросервисных архитектурах
* Стриминговые системы - платформы для обработки, передачи и анализа непрерывных потоков данных в реальном времени. Эти системы предоставляют механизмы для эффективного и масштабируемого управления потоками данных, позволяя обрабатывать их в реальном времени, а также сохранять и анализировать для будущих целей
  - обработка и анализ данных IoT - обработка данных от сенсоров и устройств интернета вещей. Состояние оборудования, телеметрии, управление системами и т.д.
  - финансовые системы - мониторинг рынков, анализа торговых операций, расчёт рисков и обработки финансовых данных в режиме реального времени
  - события и логи - анализ и обработка потоков событий и логово в реальном времени
  - обработка данных машинного обучения
  - игровая индустрия
* Event-Driven подход - архитектурный стиль, ориентированный на обработку и передачу событий или сообщений между компонентами системы. В такой архитектуре компоненты взаимодействуют через асинхронную передачу событий. Основные компоненты:
  - События (Event)
  - Издатель (Publisher)
  - Подписчик (Subscriber)
- Медиатор (Event Bus) - событийная шина (медиатор)
* Преимущества Event-Driven:
  - Отзывчивость - компоненты обрабатывают события независимо друг от друга
  - Масштабируемость - упрощает горизонтальное масштабирование, т.к. каждый компонент может обрабатывать событие независимо
  - Гибкость - издатели и подписчики могут быть добавлены или удалены без существенного воздействия на другие компоненты
  - Лёгкая интеграция - разные компоненты могут обмениваться событиями без необходимости жёсткой связи


#### 6.2 Apache Kafka
* Основные компоненты:
  - `Broker` - хранит данные, управляет записью и чтением сообщений, а также обеспечивает масштабируемость и отказоустойчивость. Кластер Kafka состоит из одного или нескольких брокеров
  - `ZooKeeper` - используется для координации и управления брокерами и другими компонентами кластера
  - `Producer` - компонент, который отправляет (публикует) данные в Kafka, которые назваются сообщениями. Эти сообщения размещаются в топиках
  - `Consumer` - компонент, который читает (подписывается на) данные из Kafka. Потребители читают данные из топиков и обрабатывают их
  - `Consumer Group` - когда несколько потребителей одновременно считывают данные из одного топика, они могут быть объединены в группу потребителей. Каждый потребитель внутри группы получает уникальный поднабор партиций для чтения. Это позволяет горизонтально масштабировать обработку данных, т.к. можно добавлять новых потребителей без изменения самих приложений
  - `Topic` - категория (канал), в который `Producer` отправляют сообщения. `Producer` могут подписаться на `Topic` и читать сообщения из них
  - `Partitions (Партиции)` - каждый `Topic` разбивается на `Partitions` (расположенных на разных `Broker`s), что позволяет горизонтально масштабировать хранение и обработку данных. Каждое сообщение в `Topic` имеет ключ и попадает в одну из `Partition`.
  - `Offsets (Смещения)` - каждый `Consumer` в группе сохраняет смещение (`Offset`) последнего считанного сообщения в каждой `Partition`. `Offset` - это позиция в `Partition`, указывающая, с какого сообщения начать чтение. `Kafka` хранит позицию последнего прочитанного сообщения для каждого своего потребителя (или группы)
* Физическое представление компонентов `Kafka`:
  - `Topics` и `Partitions` - каждый `Topic` имеет одну или более `Partition`. `Partitions` - это логи событий, хранящие сообщения в порядке их добавления. `Partitions` обеспечивают масштабируемость и обработку данных в разных частях кластера.
  - `Физическая структура на диске` - для каждой `Partition` создаётся директория на файловой системе `Kafka` `Broker`. Внутри директории `Partitions` хранятся файлы данных, индексы и другие метаданные.
  - `Файл данных`- `log segment` - сообщения хранятся в файлах данных, называемых сегментами лога (`log segments`). В этом файле доступно только одно изменение - запись в конец файла, другие изменения не поддерживаются. Когда сегмент достигает определённого размера или времени жизни, он закрывается и новый сегмент открывается. Данные из `Kafka` удаляются целыми сегментами (например, по ttl или размеру `Topic`). Вы не можете удалить конкретное сообщение.
  - `Индексы` - каждый сегмент лога сопровождается индексом. Индексы позволяют быстро находить сообщения по их смещению и времени
  - `Сжатие` - `Kafka` поддерживает сжатие данных, чтобы экономить место на диске. Сжатые сегменты могут содержать больше данных, т.к. сообщения сжимаются перед записью.
  - `Удаление данных` - старые сегменты логов с сообщениями, которые больше не нужны, могут быть удалены. Удаление может происходить по времени или вручную.


#### 6.3 Kafka Consumer. Kafka Producer
* `org.springframework.kafka:spring-kafka:3.1.2` - зависимость для подключения библиотеки взаимодействия с Kafka
* `spring.kafka.bootstrap-servers: localhost:9092` - свойство задаёт `host/port` для подключения к `Kafka`
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
```
* `ProducerFactory<Key, Value>` - класс для создания фабрики `Producer`
* `KafkaTemplate<Key, Value>` - облегчает передачу сообщений в `Kafka` (что бы это ни значило). Через него отправляются сообщения в очередь
* `ConsumerFactory<Key, Value` - класс для создания фабрики `Consumer`
* `ConcurrentKafkaListenerContainerFactory<Key, Value>` - с помощью него мы слушаем `Kafka`

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
* `docker-compose.yml`: 
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


#### 6.4 Тестирование Apache Kafka
* `org.awaitiltiy:awaitility:4.2.0` - библиотека, которая позволяет дожидаться определённых условий, удобно для тестирования асинхронных методов
