insert into users (username, email, password)
values
    ('tagudur',           'tagudur@yandex.ru',   'tagudr'),
    ('natali',            'natali@gmail.com',    'natali');

insert into categories (name)
values
    ('Sport'), ('International'), ('Economy'), ('Political');

insert into posts (title, description, body, user_id, created_at, updated_at)
values
    ('first title', 'first description', 'first body', 1, current_timestamp, current_timestamp),
    ('second title', 'second description', 'second body', 1, current_timestamp, current_timestamp),
    ('third title', 'third description', 'third body', 2, current_timestamp, current_timestamp);

insert into posts_categories (categories_id, posts_id)
values
    (2, 1),
    (4, 1),
    (4, 2),
    (3, 2),
    (3, 3);

insert into comments (body, user_id, post_id, created_at, updated_at)
    values
        ('first tagudur comment for firts title post', 1, 1, current_timestamp, current_timestamp),
        ('first natali comment for firts title post', 2, 1, current_timestamp, current_timestamp),
        ('second tagudur comment for firts title post', 1, 1, current_timestamp, current_timestamp),
        ('first natali comment for second title post', 2, 2, current_timestamp, current_timestamp),
        ('second natali comment for second title post', 2, 2, current_timestamp, current_timestamp),
        ('first tagudur comment for second title post', 1, 2, current_timestamp, current_timestamp),
        ('second tagudur comment for second title post', 1, 2, current_timestamp, current_timestamp);
