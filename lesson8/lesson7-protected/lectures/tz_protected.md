#### 8.10 Практическая работа

Защитить приложение `Трекер задач`, созданное в модуле `lesson7`:
* Добавить сущность `enum RoleTyep.class` со значениями: `ROLE_USER`, `ROLE_MANAGER`.
* Для `User.class`:
- добавить поля `password` и `roles`
- `endpoint` `create` доступен для не аутентифицированных запросов
- все `endpoints` для работы с `User.class` должны быть доступны аутентифицированным `User.class` с ролью `ROLE_USER` или `ROLE_MANAGER`
* Для `Task.class`:
- `findAll`, `findTaskById`, `addObserverToTask` доступны `User.class` с ролью `ROLE_USER` или `ROLE_MANAGER`
- `createTask`, `updateTask`, `deleteTask` доступны `User.class` с ролью `ROLE_MANAGER`
* Все данные о текущем `User.class`, передаваемые в контроллер, должны быть получены из `UserDetails.class` 
