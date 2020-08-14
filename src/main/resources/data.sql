insert into AUTHORS (
    FIO,
    BIRTHDAY,
    DATE_OF_DEATH)
values ('Достоевский Ф М', '1821-10-30', '1881-01-28'); --1

insert into AUTHORS (
    FIO,
    BIRTHDAY)
values ('Стивен Эдвин Кинг', '1947-09-21'); --2

insert into AUTHORS (
    FIO,
    BIRTHDAY)
values ('Пелевин В О', '1962-11-22'); --3

insert into AUTHORS_LITERARY_PRODUCTIONS (ID, NAME, AUTHOR_ID)
values (LITERARY_PRODUCTIONS_SEQ.nextval, 'Униженные и оскорбленные', 1); --1

insert into AUTHORS_LITERARY_PRODUCTIONS (ID, NAME, AUTHOR_ID)
values (LITERARY_PRODUCTIONS_SEQ.nextval, 'Чапаев и пустота', 3);

insert into AUTHORS_LITERARY_PRODUCTIONS (ID, NAME, AUTHOR_ID)
values (LITERARY_PRODUCTIONS_SEQ.nextval, 'ОНО', 2);

insert into AUTHORS_LITERARY_PRODUCTIONS (ID, NAME, AUTHOR_ID)
values (LITERARY_PRODUCTIONS_SEQ.nextval, 'Коллаборация ужасов', 3);

insert into AUTHORS_LITERARY_PRODUCTIONS (ID, NAME, AUTHOR_ID)
values (LITERARY_PRODUCTIONS_SEQ.currval, 'Коллаборация ужасов', 2);

insert into GENRES (NAME) values ('Роман'); --1
insert into GENRES (NAME) values ('Ужасы'); --2
insert into GENRES (NAME) values ('Классика'); --3

insert into BOOKS (ID, NAME, ISBN, LITERARY_ID, GENRE_ID)
values (1, 'Сборник русских авторов', 'ISBN-00001', 1, 1);

insert into BOOKS (ID, NAME, ISBN, LITERARY_ID, GENRE_ID)
values (1,'Сборник русских авторов', 'ISBN-00001', 2, 1);

insert into BOOKS (ID, NAME, ISBN, LITERARY_ID, GENRE_ID)
values (2,'Лучшее Стивена Кинга', 'ISBN-00023', 3, 2);
