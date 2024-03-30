insert into categories(name)
values ('Fantazy'),
       ('Science fiction'),
       ('History');

insert into books(author, name, category_id)
values ('George R. R. Martin', 'A Song of Ice and Fire', 1),
       ('FirstAuthor', 'FirstBook', 1),
       ('SecondAuthor', 'SecondBook', 1),
       ('ThirdAuthor', 'ThirdBook', 1),
       ('Frank Herbert,', 'Dune', 2),
       ('FirstAuthor,', 'FourthBook', 2),
       ('SecondAuthor,', 'FifthBook', 2),
       ('ThirdAuthor,', 'SixthBook', 2),
       ('Harold Holzer', 'Brought Forth on This Continent: Abraham Lincoln and American Immigration', 3),
       ('FirstAuthor', 'SeventhBook', 3),
       ('SecondAuthor', 'EighthBook', 3),
       ('ThirdAuthor', 'NinthBook', 3);
