package ru.otus.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.pvn.libraryApp.dao.Book;
import ru.otus.pvn.libraryApp.dao.BookDaoJdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Тест методов BookDaoJdbc")
@JdbcTest
@Import(BookDaoJdbc.class)
class LibraryAppApplicationTests {

    @Autowired
    BookDaoJdbc jdbc;

    @DisplayName("возвращает новую созданную книгу")
    @Test
    void shouldReturnNewBook () {
        Book book = new Book("Тест книга","1980-1-1",1,1,1);
        jdbc.insert(book);
        Book bookFromDB = jdbc.getById("2");
        assertThat(bookFromDB).hasFieldOrPropertyWithValue("name", "Тест книга");
    }

    @DisplayName("обновляет название книги")
    @Test
    void shouldUpdateBook () {
        Book book = new Book("Тест книга","1980-1-1",1,1,1);
        jdbc.update("1", book);
        Book bookFromDB = jdbc.getById("1");
        assertThat(bookFromDB).hasFieldOrPropertyWithValue("name", "Тест книга");
    }

    @DisplayName("получает книгу")
    @Test
    void shouldGetBookFromDB () {
        Book bookFromDB = jdbc.getById("1");
        assertThat(bookFromDB).hasFieldOrPropertyWithValue("name", "Муму");
    }

    @DisplayName("удаляет книгу")
    @Test
    void shouldDeleteBook () {
        jdbc.deleteById("1");
        try {
            Book bookFromDB = jdbc.getById("1");
        } catch (Exception e) {
            assertThatExceptionOfType(EmptyResultDataAccessException.class);
        }
    }


}
