insert into AUTHORS (
    FIO,
    BIRTHDAY,
    DATE_OF_DEATH)
values ('Достоевский Ф М', '1821-10-30', '1881-01-28');

insert into AUTHORS (
    FIO,
    BIRTHDAY)
values ('Стивен Эдвин Кинг', '1947-09-21');

insert into AUTHORS (
    FIO,
    BIRTHDAY)
values ('Пелевин В О', '1962-11-22');

insert into PUBLISHINGS (
    NAME,
    PHONE)
values ('ACT', '8-920-930-50-47');

insert into PUBLISHINGS (
    NAME,
    PHONE)
values ('ЭКСМО', '8-920-874-44-47');

insert into GENRES (
    NAME)
values ('Роман');

insert into GENRES (
    NAME)
values ('Ужасы');

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
values ('Униженные и оскорбленные', '1861-01-01', 1, 3, 2);
insert into BOOKS (
    NAME,
    CREATE_DATE,
    AUTHOR_ID,
    GENRE_ID,
    PUBLISHING_ID
)
values ('Чапаев и пустота', '1995-01-01', 3, 1, 1);
insert into BOOKS (
    NAME,
    CREATE_DATE,
    AUTHOR_ID,
    GENRE_ID,
    PUBLISHING_ID
)
values ('ОНО', '1889-01-01', 2, 2, 1);
