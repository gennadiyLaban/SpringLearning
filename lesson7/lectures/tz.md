#### 7.6 Практическая работа

Реализовать REST API для "Трекера задач", которое должно:
* Оперировать сущностями `User` и `Task`
* `User`:
  - Состоит из полей документа:
    + String id
    + String username
    + String email
  - API:
    + findAll()
    + findUserById()
    + createUser()
    + updateUser()
    + deleteUserById()
* `Task`:
  - Состоит из полей документа:
    + String id,
    + String name,
    + String description,
    + Instant createdAt,
    + Instant updatedAt,
    + TaskStatus status,
    + String authorId,
    + String assigneeId,
    + Set<String>observerIds.
  - Имеет дополнительные поля `@ReadOnlyProperty`(вне документа):
    + User author
    + User assignee
    + Set<User> observers
  - API:
    + findAll() - все дополнительные поля должны быть заполнены
    + findTaskById() - все дополнительные поля должны быть заполнены
    + createTask()
    + updateTask()
    + addObserverToTask()
    + deleteTaskById()
* `TaskStatus` - `enum` со значениями TODO, IN_PROGRESS, DONE
* Маппинг сущностей должен быть реализован через `MapStruct`
* Все `@RestController` должны возвращать `Mono` или `Flux`
