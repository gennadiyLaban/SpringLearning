#### 8.10 Практическая работа

1. Защитить приложение `Новости`, созданное в модуле `lesson4`:
   * Добавить сущность `enum RoleType.class` со значениями: `ROLE_ADMIN`, `ROLE_USER`, `ROLE_MODERATOR`.
   * Создать сущность `@Entity RoleRecord.class`, которая будет хранить связанные с `User.class` роли в системе (`RoleType.class`)
     - Каждый `User.class` может обладать одной или несколькими ролями
   * Для `User.class`:
     - добавить поле `String password` 
       +  поле должно быть в зашифрованном виде
     - добавить поле `Set<RoleType> roles`, которое будет подтягиваться из `RoleRecord.class`
     - дополнить `endpoint` `create`:
       + при создании передавать `Set<RoleType.class>`
       + при создании передавать `String password`
     - защитить `endpoint` `findAll`:
       + использовать этот метод может только `User.class` с ролью `ROLE_ADMIN`
     - защитить `endpoint` `findById`:
       + `User.class` может запрашивать информацию о себе
       + запрашивать `User.class` может другой `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR`
     - защитить `endpoint` `update`:
       + `User.class` может обновлять информацию о себе (**включая** `password` и `username`)
       + обновлять `User.class` может другой `User.class` с ролью `ROLE_ADMIN` или `ROLE_MODERATOR` (**исключая** `password` и `username`)
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
