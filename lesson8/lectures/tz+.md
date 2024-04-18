#### 8.10 Практическая работа

1. Защитить приложение `Новости`, созданное в модуле `lesson4`:
   * Добавить сущность `enum RoleTyep.class` со значениями: `ROLE_ADMIN`, `ROLE_USER`, `ROLE_MODERATOR`.
   * Создать сущность `@Entity RoleRecord.class`, которая будет хранить связанные с `User.class` роли в системе (`RoleType.class`)
     - Каждый `User.class` может обладать одной или несколькими ролями
   * Создать сущность `@Entity UserCredentials.class`, которая:
     - хранит поле `String username` в открытом виде
     - хранит поле `String password` в зашифрованном виде
     - хранит поле `String jwtToken` в зашифрованном виде
     - создать `protected endpoint`: `api/v1/user/{id}/credentials`, доступ к которому имеет только `owner` данного `UserCredentials.class`
     - создать `protected endpoint`: `api/v1/user/{id}/credentials/update`, доступ к которому имеет только `owner` данного `UserCredentials.class`
     - запретить `User.class`, даже с ролью `ROLE_ADMIN` или `ROLE_MODERATOR` удалять или запрашивать не принадлежащие им `UserCredentials.class`
   * Создать `Auth API`:
     - `api/v1/auth/registration` - создание нового `User.class`, доступно неавторизованному пользователю
       + аутентификация не требуется
     - `api/v1/auth/signin` - получение `JWT-token` и `Refresh-token` через `Basic-authentication` для взаимодействия с защищённым `api`
       + аутентификация не требуется
     - `api/v1/auth/refresh` - обмен полученного ранее `Refresh-token` на новый `JWT-token` и `Refresh-token`
       + аутентификация не требуется 
     - `api/v1/auth/logout` - выход из системы: инвалидация `JWT-token` и `Refresh-token`
       + `User.class` должен быть аутентифицирован
   * Для `User.class`:
     - поле `String username` теперь подтягивается из `UserCredentials.class`
     - создать поле `Set<RoleType> roles`, которое будет подтягиваться из `RoleRecord.class`
     - удалить `endpoint` `create` - создание `User.class` должно происходить через `auth api`
     - защитить `endpoint` `findAll`:
       + использовать этот метод может только `User.class` с ролью `ROLE_ADMIN`
     - защитить `endpoint` `findById`:
       + `User.class` может запрашивать информацию о себе
       + запрашивать `User.class` может другой `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `update`:
       + `User.class` может обновлять информацию о себе
       + обновлять `User.class` может другой `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
     -  защитить `endpoint` `delete`:
       + `User.class` может удалить свой профиль
       + удалять `User.class` может другой `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
     -  Проверка правил на удаление, обновление и получение информации о пользователе по ID должна происходить через AOP
   * Для `Category.class`:
     - защитить `endpoint` `create`:
       + создавать `Category` может только `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR` 
     - защитить `endpoint` `findAll`:
       + получать список `Category` может только `User.class` с ролью `ROLE_USER`, `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `findById`:
       + получать `Category` по `id` может только `User.class` с ролью `ROLE_USER`, `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `update`:
       + обновлять `Category` может только `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR` 
     - защитить `endpoint` `delete`:
       + удалять `Category` может только `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
   * Для `Post.class`:
     - Удалить все передаваемые данные о пользователе, которые раньше передавал клиент (`userId`)
     - Данные о пользователе должны быть получены из `UserDetails.class`
     - защитить `endpoint` `create`:
       + создавать `Post.class` может любой `User.class` с ролью  `ROLE_USER`, `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `findAll`:
       + получать список `Post.class` может любой `User.class` с ролью  `ROLE_USER`, `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `findById`:
       + получать `Post.class` по `id` может любой `User.class` с ролью  `ROLE_USER`, `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `update`:
       + обновлять `Post.class` может только `owner` `User.class`
     - защитить `endpoint` `delete`:
       + `User.class` может удалить те `Post.class`, которые создал
       + удалять `Post.class` может `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
   * Для `Comment.class`:
     - Удалить все передаваемые данные о пользователе, которые раньше передавал клиент (`userId`)
     - Данные о пользователе должны быть получены из `UserDetails.class`
     - защитить `endpoints` `create`, `findAllByPostId`, `findById`
       + создавать, получать по `id`, получать список `Comment.class` для `Post.class` может любой `User.class` с ролью `ROLE_USER`, `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `update`:
       + обновлять `Comment.class` может только тот `User.class`, который создал `Comment.class`
     - защитить `endpoint` `delete`:
       + `User.class` может удалить те `Comment.class`, которые создал
       + удалять `Comment.class` может `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
2. Защитить приложение `Трекер задач`, созданное в модуле `lesson7`:
    * Добавить сущность `enum RoleTyep.class` со значениями: `ROLE_USER`, `ROLE_MANAGER`.
    * Для `User.class`:
      - добавить поля `password` и `roles`
      - `endpoint` `create` доступен для не аутентифицированных запросов
      - все `endpoints` для работы с `User.class` должны быть доступны аутентифицированным `User.class` с ролью `ROLE_USER` или `ROLE_MANAGER`
    * Для `Task.class`:
      - `findAll`, `findTaskById`, `addObserverToTask` доступны `User.class` с ролью `ROLE_USER` или `ROLE_MANAGER`
      - `createTask`, `updateTask`, `deleteTask` доступны `User.class` с ролью `ROLE_MANAGER`
    * Все данные о текущем `User.class`, передаваемые в контроллер, должны быть получены из `UserDetails.class` 
