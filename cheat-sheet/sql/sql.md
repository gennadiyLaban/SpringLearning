Создание схемы и включение в путь поиска по-умолчанию:
```postgresql
-- создание схемы
CREATE SCHEMA IF NOT EXISTS lesson3;
-- задание созданной схемы `lesson3` в качестве путя по-умолчанию 
-- для пользователя admin базы данных lesson3
ALTER ROLE admin IN DATABASE lesson3 SET SEARCH_PATH=lesson3,public;
SET SEARCH_PATH=lesson3,public;
```

Создание таблицы:
```postgresql
CREATE TABLE IF NOT EXISTS contacts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    email VARCHAR (100) NOT NULL,
    phone VARCHAR (50) NOT NULL
);
```

Инсерт данных в таблицу:
```postgresql
insert into lesson3.contacts (first_name, last_name, email, phone)
values
    ('Barry',   'Joyner',   'lectus@icloud.net',                      '+119424554770'),
    ('Cara',    'Lynn',     'ut.odio@yahoo.edu',                      '+112318781251'),
    ('Carla',   'Clarke',   'vivamus.non@hotmail.net',                '+16544852'),
    ('Yuli',    'Rivera',   'libero.est@protonmail.ca',               '+16063337'),
    ('August',  'Baird',    'diam@protonmail.org',                    '+17037542');
```

Update уже существующей строки
```postgresql
UPDATE contacts
SET first_name = ?, last_name = ?, email = ?, phone = ?
WHERE id = ?
```

Удаление строки
```postgresql
DELETE FROM contacts WHERE id = ?
```

Пример таблицы отношений @ManyToOne

```postgresql
  CREATE TABLE comments
  (                            -- создание таблицы
      id      BIGSERIAL PRIMARY KEY,
      body    TEXT   NOT NULL, -- текст без ограничения по длинне
      -- REFERENCES users(id) - говорит о тому, что поле user_id - внешний ключ 
      -- ссылающийся на первичный ключ `id` таблицы `users`
      -- 
      -- ON DELETE CASCADE - говорит о том, что при удалении 
      -- записи из таблицы `users`, где первичный ключ `id` совпадает с внешним ключом
      -- `user_id` из таблицы `comments`, такую запись из таблицы `comments` тоже нужно удалить
      user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
      post_id BIGINT NOT NULL REFERENCES posts (id) ON DELETE CASCADE
  );
  ```
