package ru.otus.pvn.libraryApp.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.pvn.libraryApp.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM GENRES WHERE ID = :id",
                Map.of("id", id),
                new GenreMapper() );
    }

    @Override
    public void create(Genre genre) {
        jdbc.update("insert into genres (" +
                        "    name\n" +
                        ")"+
                        "   values ( " +
                        "    :name\n" +
                        ")",
                Map.of("name", genre.getName()));
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"),
                    resultSet.getString("name"));
        }
    }
}
