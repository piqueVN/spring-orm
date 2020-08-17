package ru.otus.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.otus.pvn.libraryApp.dao.LiteraryProductionDaoJdbc;
import ru.otus.pvn.libraryApp.models.Author;
import ru.otus.pvn.libraryApp.models.LiteraryProduction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест методов LiteraryDaoJdbc")
@JdbcTest
@Import({LiteraryProductionDaoJdbc.class, AuthorDaoJdbc.class})
public class LiteraruProductionDaoJdbcTests {

    @Autowired
    LiteraryProductionDaoJdbc literaryJdbc;

    @Autowired
    AuthorDaoJdbc authorJdbc;

    @DisplayName("получить информацию по id литературного произведения")
    @Test
    void shouldGetLiteraryProductionFromDBById() {
        LiteraryProduction literaryProduction = literaryJdbc.getById(1);
        assertThat(literaryProduction).hasFieldOrPropertyWithValue("name", "Униженные и оскорбленные");
        assertThat(literaryProduction.getAuthors().get(0)).hasFieldOrPropertyWithValue("fio", "Достоевский Ф М");
    }

    @DisplayName("добавить информацию по id литературного произведения")
    @Test
    void shouldCreateLiteraryProduction() throws SQLException {
        List<Author> authors =  new ArrayList<Author>();
        authors.add(authorJdbc.getById(1));
        authors.add(authorJdbc.getById(2));
        LiteraryProduction literaryProduction = new LiteraryProduction("Приключения приклю", authors);
        literaryJdbc.create(literaryProduction);
        assertThat(literaryJdbc.getById(5)).hasFieldOrPropertyWithValue("name", "Приключения приклю");
    }

}
