insert into
    hotels(name, title, address_city, address_street, address_number, distance_from_center, rating, number_of_rating)
    values
        ('Hotel1', 'Title1', 'City1', 'Street1', '1', 100, 1.0, 10),
        ('Hotel2', 'Title2', 'City2', 'Street2', '2', 200, 2.0, 20),
        ('Hotel3', 'Title3', 'City3', 'Street3', '3', 300, 3.0, 30);

insert into
    rooms(name, description, room_number, price, max_capacity, hotel_id)
    values
        ('Hotel1Room1', 'Hotel1Room1Description', 1, 1, 1, 1),
        ('Hotel1Room2', 'Hotel1Room2Description', 2, 2, 2, 1),
        ('Hotel1Room3', 'Hotel1Room3Description', 3, 3, 3, 1),

        ('Hotel2Room1', 'Hotel2Room1Description', 1, 10, 10, 2),
        ('Hotel2Room2', 'Hotel2Room2Description', 2, 20, 20, 2),

        ('Hotel3Room1', 'Hotel3Room1Description', 1, 30, 30, 3);

insert into
    user_roles(role_type)
    values
        ('ROLE_ADMIN'),
        ('ROLE_USER');

insert into
    users(username, password, email)
    values
        ('user1', '$2a$12$r.JGVIl1qPTWJR6K9vtaFOHWrpyiprFEzcFiNz3GzZt18bLXqSRcK', 'user1@ya.ru'),
        ('user2', '$2a$12$Pbpuxa1iEuAxq6MhVb2AqujEYhHoUWAobpjGKUPTKKObYPdioHku6', 'user2@ya.ru'),
        ('user3', '$2a$12$oDpAIIZbwoyWHMeCu0G1hu5pc/bqsq0e83VvH.xl.Z/4Dyqf23.0e', 'user3@ya.ru'),
        ('user4', '$2a$12$pIcxh4MJ09nfQmU9olqrDup55a2ifGWTS5hjWjWrJxGr4zdKWa7p.', 'user4@ya.ru');

insert into
    user_role_records(user_id, role_type)
    values
        (1, 'ROLE_ADMIN'),
        (2, 'ROLE_USER'),
        (3, 'ROLE_USER'),
        (4, 'ROLE_USER');

insert into
    bookings(start_date, end_date, room_id, user_id)
values
    ('2024-08-20T00:00:00Z', '2024-08-24T00:00:00Z', 1, 2),
    ('2024-07-13T00:00:00Z', '2024-07-19T00:00:00Z', 1, 4),
    ('2024-07-03T00:00:00Z', '2024-07-08T00:00:00Z', 1, 1),
    ('2024-05-26T00:00:00Z', '2024-06-05T00:00:00Z', 1, 2),
    ('2024-06-14T00:00:00Z', '2024-06-21T00:00:00Z', 1, 1),
    ('2024-07-31T00:00:00Z', '2024-08-11T00:00:00Z', 1, 2),
    ('2024-08-04T00:00:00Z', '2024-08-09T00:00:00Z', 2, 4),
    ('2024-05-30T00:00:00Z', '2024-06-01T00:00:00Z', 2, 4),
    ('2024-07-24T00:00:00Z', '2024-07-30T00:00:00Z', 2, 4),
    ('2024-05-22T00:00:00Z', '2024-05-25T00:00:00Z', 2, 3),
    ('2024-07-15T00:00:00Z', '2024-07-17T00:00:00Z', 2, 4),
    ('2024-06-29T00:00:00Z', '2024-07-09T00:00:00Z', 2, 4),
    ('2024-06-07T00:00:00Z', '2024-06-17T00:00:00Z', 2, 4),
    ('2024-06-05T00:00:00Z', '2024-06-13T00:00:00Z', 3, 2),
    ('2024-06-21T00:00:00Z', '2024-07-01T00:00:00Z', 3, 2),
    ('2024-05-20T00:00:00Z', '2024-05-31T00:00:00Z', 3, 3),
    ('2024-06-13T00:00:00Z', '2024-06-17T00:00:00Z', 3, 4),
    ('2024-05-19T00:00:00Z', '2024-05-31T00:00:00Z', 4, 3),
    ('2024-06-22T00:00:00Z', '2024-06-28T00:00:00Z', 4, 3),
    ('2024-06-11T00:00:00Z', '2024-06-12T00:00:00Z', 4, 4),
    ('2024-07-10T00:00:00Z', '2024-07-12T00:00:00Z', 4, 2),
    ('2024-05-25T00:00:00Z', '2024-05-27T00:00:00Z', 5, 3),
    ('2024-06-29T00:00:00Z', '2024-06-30T00:00:00Z', 5, 1),
    ('2024-06-08T00:00:00Z', '2024-06-18T00:00:00Z', 5, 2),
    ('2024-07-10T00:00:00Z', '2024-07-14T00:00:00Z', 5, 4),
    ('2024-05-21T00:00:00Z', '2024-05-23T00:00:00Z', 6, 3),
    ('2024-06-01T00:00:00Z', '2024-06-08T00:00:00Z', 6, 1),
    ('2024-06-10T00:00:00Z', '2024-06-21T00:00:00Z', 6, 3),
    ('2024-06-26T00:00:00Z', '2024-06-28T00:00:00Z', 6, 2);