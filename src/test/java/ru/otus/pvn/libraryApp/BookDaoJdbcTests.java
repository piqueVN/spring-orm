package ru.otus.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.pvn.libraryApp.dao.*;
import ru.otus.pvn.libraryApp.models.Book;

import java.util.List;

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
    }

    @DisplayName("добавить книгу")
    @Test
    void shouldCreateBook() {
        Book book = bookJdbc.getById(1);
        book.setName("Сборник типа русских авторов йоу!");
        bookJdbc.create(book);
        assertThat(bookJdbc.getById(5)).hasFieldOrPropertyWithValue("name", "Сборник типа русских авторов йоу!");
    }

    @DisplayName("обновить книгу")
    @Test
    void shouldUpdateBook() {
        Book book = bookJdbc.getById(2);
        book.setName("Ужасы говнокода!");
        bookJdbc.update(book);
        book = bookJdbc.getById(2);
        assertThat(book).hasFieldOrPropertyWithValue("name", "Ужасы говнокода!");
    }

    @DisplayName("удалить книгу")
    @Test
    void shouldDeleteBook() {
        bookJdbc.deleteById(1);
        assertThat(bookJdbc.getById(1)).isNull();
    }

    @DisplayName("получить книги")
    @Test
    void shouldGetAllBook() {
        List<Book> books = bookJdbc.getAll();
        assertThat(books).hasSizeGreaterThanOrEqualTo(2);
    }


}
