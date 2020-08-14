package ru.otus.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.otus.pvn.libraryApp.models.Author;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест методов AuthorDaoJdbc")
@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTests {

    @Autowired
    AuthorDaoJdbc jdbc;

    @DisplayName("получает книгу по id")
    @Test
    void shouldGetAuthorFromDBById() {
        Author author = jdbc.getById(1);
        assertThat(author).hasFieldOrPropertyWithValue("fio", "Достоевский Ф М");
    }

    @DisplayName("получает книгу по fio")
    @Test
    void shouldGetAuthorFromDBByFio() {
        Author author = jdbc.getByFio("Пелевин В О");
        assertThat(author).hasFieldOrPropertyWithValue("fio", "Пелевин В О");
    }

    @DisplayName("возвращает новую созданную книгу")
    @Test
    void shouldReturnNewAuthor () {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Author author = new Author("Привалов Д С", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        jdbc.create(author);
        Author authorFromDB = jdbc.getByFio("Привалов Д С");
        assertThat(authorFromDB).hasFieldOrPropertyWithValue("fio", "Привалов Д С");
    }

}
