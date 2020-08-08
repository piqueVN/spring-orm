insert into AUTHORS (
    FIO,
    BIRTHDAY,
    DATE_OF_DEATH)
values ('Тургенев Ф М', '1821-10-30', '1881-01-28');

insert into PUBLISHINGS (
    NAME,
    PHONE)
values ('ACT', '8-920-930-50-47');

insert into GENRES (
    NAME)
values ('Классика');

insert into BOOKS (
    NAME,
    CREATE_DATE,
    AUTHOR_ID,
    GENRE_ID,
    PUBLISHING_ID
)
values ('Муму', '1861-01-01', 1, 1, 1);
