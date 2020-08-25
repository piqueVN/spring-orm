package ru.otus.pvn.libraryApp.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.pvn.libraryApp.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public Author getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM AUTHORS WHERE ID = :id",
                Map.of("id", id),
                new AuthorMapper());
    }

    @Override
    public Author getByFio(String name) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.queryForObject("SELECT * FROM AUTHORS WHERE FIO = :name",
                Map.of("name", name),
                new AuthorMapper());
    }

    @Override
    public void create(Author author) {
        jdbc.update("insert into authors (" +
                        "    fio,\n" +
                        "    birthday,\n" +
                        "    date_of_death\n" +
                        ")"+
                        "   values ( " +
                        "      :fio,\n" +
                        "      :birthday,\n" +
                        "      :dateOfDeath\n" +
                        ")",
                Map.of("fio", author.getFio(),
                       "birthday", author.getBirthday(),
                       "dateOfDeath", author.getDateOfDeath()));
        }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"),
                    resultSet.getString("fio"),
                    resultSet.getDate("birthday"),
                    resultSet.getDate("date_of_death"));
        }
    }
}


