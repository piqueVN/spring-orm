package ru.otus.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.pvn.libraryApp.dao.*;
import ru.otus.pvn.libraryApp.models.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест методов BookDaoJdbc")
@JdbcTest
@Import({LiteraryProductionDaoJdbc.class, AuthorDaoJdbc.class, BookDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTests {

    @Autowired
    LiteraryProductionDaoJdbc literaryJdbc;
    @Autowired
    AuthorDaoJdbc authorJdbc;
    @Autowired
    BookDaoJdbc bookJdbc;
    @Autowired
    GenreDaoJdbc genreJdbc;

    @DisplayName("получить книгу по id")
    @Test
    void shouldGetBookFromDBById() {
        Book book = bookJdbc.getById(1);
        assertThat(book).hasFieldOrPropertyWithValue("name", "Сборник русских авторов");
        assertThat(book.getLiteraryProductions()).hasSizeGreaterThanOrEqualTo(2);
    }

    @DisplayName("получить книгу по name")
    @Test
    void shouldGetBookFromDBByName() {
        Book book = bookJdbc.getByName("Сборник русских авторов");
        assertThat(book).hasFieldOrPropertyWithValue("name", "Сборник русских авторов");
        assertThat(book.getLiteraryProductions()).hasSizeGreaterThanOrEqualTo(2);
    }

    @DisplayName("добавить книгу")
    @Test
    void shouldCreateBook() {
        Book book = bookJdbc.getByName("Сборник русских авторов");
        book.setName("Сборник типа русских авторов йоу!");
        bookJdbc.create(book);
        assertThat(book).hasFieldOrPropertyWithValue("name", "Сборник типа русских авторов йоу!");
        assertThat(book.getLiteraryProductions()).hasSizeGreaterThanOrEqualTo(2);
    }

    @DisplayName("обновить книгу")
    @Test
    void shouldUpdateBook() {
        Book book = bookJdbc.getById(2);
        book.setName("Ужасы говнокода!");
        bookJdbc.update(book);
        book = bookJdbc.getById(2);
        assertThat(book).hasFieldOrPropertyWithValue("name", "Ужасы говнокода!");
        assertThat(book.getLiteraryProductions()).hasSize(1);
    }

    @DisplayName("удалить книгу")
    @Test
    void shouldDeleteBook() {
        bookJdbc.deleteById(1);
        assertThat(bookJdbc.getById(1)).isNull();
    }


}
