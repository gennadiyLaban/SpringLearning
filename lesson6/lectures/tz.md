#### 6.5 Практическая работа

1) Реализовать отдельным приложением REST API, которое должно:
   * принимать сущность dto-`Order` в endpoint `POST api/v1/order`
     - `Order` имеет поля `String product` и `Integer quantity`
   * создавать на основе `Order` dto-сущность `OrderEvent`
     - `OrderEvent` имеет поля `String product` и `Integer quantity`
   * отправлять `OrderEvent` в `Kafka` в `Topic` `order-topic`
   * слушать `Topic` `order-status-topic` и выводить в лог полученные сообщения
     - сообщения в формате `log.info("Received message: {}", message); ` и `log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);`
2) Реализовать отдельное приложение без REST API, которое должно:
  * слушать `Topic` `order-topic`
  * при получении сообщения из `order-topic` создавать dto-сущность `OrderStatusEvent`
    - `OrderStatusEvent` имеет поля `String status` и `Instant date`
    - В поле `OrderStatusEvent.status` записывается строка `CREATED` или `PROCESS`
    - В поле `OrderStatusEvent.date` записывается текущая дата
  * отправлять `OrderStatusEvent` в `Topic` `order-status-topic`
3) Общие требования для `1` и `2`:
  * `OrderEvent` и `OrderStatusEvent` должны принадлежать одному пакету
