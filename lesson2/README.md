Запуск программы производить через:
1. `Lesson2ShellApplication`
2. `docker-compose.yml`

Режим первоначального заполнения базы данных студентами контролируется property `app.init.file.enabled`, определённом в application.yml. Свойство берёт значение из переменной окружения `INIT_FROM_FILE`, если переменная не задана - выставляет дефолтное значение `false`. В docker-compose.yml значение `INIT_FROM_FILE` установленно в `false`.

Команды:
* `create`
* `deleteAll` (`deleteall`)
* `delete`
* `printAll` (`printall`)

Особенности:
* Реализован отдельный слушатель кастомных событий `StudentCreatedEvent` и `StudentDeletedEvent` - `StudentEventListener`, откуда в терминал выводятся сообщения о созданных/удалённых студентах
