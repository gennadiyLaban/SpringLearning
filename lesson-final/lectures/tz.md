### Финальная работа курса «Разработка на Spring Framework»
Разработка бэкенд-составляющей сервиса бронирования отелей с возможностью управлять контентом через административную панель CMS.

Основные задачи:
* Обеспечить пользователям возможность забронировать понравившийся отель на определённый период.
* Сделать поиск отелей по критериям и рейтингам
* Сделать систему выставления оценок в приложении (от 1 до 5)
* Сервис должен позволять администраторам выгружать статистику по работе в формате CSV-файла

#### Задание 1. Подготовка окружения
* Создать проект с зависимостями: `Spring`, `Spring MVC`, `Spring Data`, `Mapstruct`
* Создать базу данных `lessonFinal`
* Создать `docker-compose.yml`-файл с описанием параметров запуска контейнера `PostgreSQL`


#### Задание 2. Работа с отелями
* Сущность `Hotel.class`:
  - Fields:
    + `String name`
    + `Address address`
    + `Long distanceFromCenter`
    + `BigDecimal rating`
    + `Integer numberOfRating`
  - Создать отображение на БД с помощью JPA
  - Создать `REST API`:
    + `findById`
    + `createHotel`
    + `updateHotel`:
      * `rating` не может быть изменён
      * `numberOfRating` не может быть изменён
    + `deleteHotel`
    + `GET findAll`
      * реализовать механизм `Pagination`
* Встраиваемая сущность `Address.class`
  - Fields:
    + `String city`;
    + `String street`;
    + `String number`;
* Маппинг сущностей из `DTO` в `@Entity` и обратно должен происходить через библиотеку `Mapstruct`


#### Задание 3. Обработка ошибок
* Создать сущность `ErrorBodyDTO.class`
* Описать `RestControllerAdvice.class`, который
  - обрабатывает `HotelNotFoundException` - 404
  - обрабатывает `MethodArgumentNotValidException` - 400
  - обрабатывает не перехваченные `Exception.class` - 500


#### Задание 4. Работа с комнатами
* Сущность `HotelRoom.class`
  - Fields:
    + `String name`
    + `String description`
    + `Integer roomNumber`
    + `BigDecimal cost`
    + `Integer maxCapacity`
    + `Hotel hotel` (`@Indexed`??)
    + `List<BookingRecord> bookingRecords`
  - Создать отображение на БД с помощью JPA
  - Создать `REST API`:
    + `findById`
    + `createHotelRoom`
    + `updateHotelRoom`
    + `deleteHotelRoom`
* Сущность `BookingRecord.class`
  - Fields:
    + `HotelRoom room` (`@Indexed`??)
    + `Instant start`
    + `Instant end`
  - Создать отображение на БД с помощью JPA


#### Задание 5. Работа с пользователями
* Сущность `User.class`
  - Fields:
    + `String username`;
      * `constraint unique` 
    + `String password`;
    + `String email`
      * `constraint unique`
    + `Set<RoleType> roles`;
  - Создать отображение на БД с помощью JPA
  - Создать в `service/repository`:
    - `findUserByUsername`
  - Создать `REST API`:
    + `findById`
    + `findAll`
    + `createUser`
    + `updateUser`
    + `deleteUser`
  - Добавить обработку ошибки при попытке создание пользователя с уже существующим `username` и `email`
* Сущность `RoleRecord.class`
  - Fields:
    + `RoleType role`;
    + `User user`;
* Сущность `enum RoleType.class`:
  - Fields: `ROLE_USER`, `ROLE_ADMIN`
  - Создать отображение на БД с помощью JPA


#### Задание 6. Работа с бронированием
* Расширить сущность `BookingRecord.class`, описанную в `Задание 4. Работа с комнатами`
  - Fields:
      + `HotelRoom room` (`@Indexed`??)
      + `User user` (`@Indexed`??)
      + `Instant start`
      + `Instant end`
* Создать `REST API`:
  - `bookHotelRoom` 
    - Разные `User.class` могут забронировать `HotelRoom.class`, но только на разные(свободные) даты
  - `findAllBookings`
    + Вопрос №4 
* Создать отображение на БД с помощью JPA


#### Задание 7. Настройка Spring Security
* Использовать `Basic Auth`
* Реализовать `UserDetails` и `UserDetailsService`
* Все `endpoints` должны быть защищены, кроме `api/v1/user/register`
* `api/v1/hotel/create`, `api/v1/hotel/update`, `api/v1/hotel/delete` - доступ только у `RoleType.ROLE_ADMIN`
* `api/v1/room/create`, `api/v1/room/update`, `api/v1/room/delete` - доступ только у `RoleType.ROLE_ADMIN`
* `api/v1/booking/list` - доступ только у `RoleType.ROLE_ADMIN`
* Все остальные `endpoints` должны быть доступны `RoleType.ROLE_ADMIN` и `RoleType.ROLE_USER`


#### Задание 8. Создание метода изменения рейтинга отеля
* Сущность `Hotel.class`:
  - Дополнить `REST API`:
    + `markHotel`
      * Вопрос №5
      * Вопрос №6
      * Формула изменения `numberOfRating`:
        - `umberOfRating = numberOfRating + 1`
      * Формула изменения `rating`: 
        - `var numberOfRating = numberOfRating + 1` (`numberOfRating` - значение до инкремента)
        - `var totalRating = rating * numberOfRating`
        - `totalRating = totalRating - rating + newMark`
        - `rating = totalRating / numberOfRating`


#### Задание 9. Разработка метода, отдающего постраничную информацию об отелях с учётом фильтрации от пользователя
* Сущность `Hotel.class`:
  - Дополнить `REST API`:
    + `POST findAll`
      * Реализовать механизм `Pagination`
      * Реализовать механизм фильтрации отелей:
        - Вопрос №7
        - `Hotel.id`
        - `Hotel.name`
        - `Hotel.distanceFromCenter` (промежуток?)
        - `Hotel.rating` (промежуток?)
        - `Hotel.numberOfRating` (промежуток?)


#### Задание 10. Разработка метода, отдающего постраничную информацию о комнатах с учётом фильтрации от пользователя
* Сущность `HotelRoom.class`:
  - Дополнить `REST API`:
    + `POST findAll`
      * Реализовать механизм `Pagination`
      * Фильтрация `startBooking` и `endBooking` должна проходить по обоим полям (в случае заполнения только одного поля - выбрасывать ошибку). При фильтрации должны показываться `СВОБОДНЫЕ` комнаты на выбранные даты
      * Реализовать фильтрацию:
        - `HotelRoom.id` 
        - `HotelRoom.name`
        - `minCost` и `maxCost` - `minCost <= HotelRoom.cost <= maxCost`
        - Вопрос №9
        - `targetCapacity` (фильтрация должна быть по полю `HotelRoom.maxCapacity`)
        - `startBooking` и `endBooking` - `HotelRoom.bookingRecords`
        - `HotelRoom.hotel`
* Сущность `BookingRecord.class`:
  - Дополнить `REST API`:
    + `findAllBookings`
      * Вопрос №8
      * Реализовать `Pagination`
* Вопрос №10


#### Задание 11. Создание слоя, отвечающего за сбор статистики в приложении
* Событие `UserRegisteredEvent.class`
  - Fields:
    + Long userId;
    + Instant timestamp;
  - Отправляется при регистрации нового `User.class` 
  - Отправляется/читается из `Topic` `user_registration`
* Событие `HotelRoomBookedEvent.class` 
  - Fields:
    + Long userId;
    + Long hotelRoomId;
    + Instant startBooking;
    + Instant endBooking;
    + Instant timestamp;
  - Отправляется при новом `BookingRecord.class`
  - Отправляется/читается из `Topic` `hotel_room_booking`
* События отправляются в асинхронную очередь `Kafka`
* События сохраняются в `MongoDB`
* Создать `REST API`:
  - `downloadUserRegistrationStatistics`
    + доступен пользователям с ролью `ROLE_ADMIN`
  - `downloadBookingStatistics`
    + доступен пользователям с ролью `ROLE_ADMIN`
* Вопрос №11
