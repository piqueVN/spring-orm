package ru.otus.pvn.libraryApp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.pvn.libraryApp.models.Author;
import ru.otus.pvn.libraryApp.models.LiteraryProduction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LiteraryProductionDaoJdbc implements LiteraryProductionDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDaoJdbc  authorJdbc;

    public LiteraryProductionDaoJdbc(NamedParameterJdbcOperations jdbc, AuthorDaoJdbc authorJdbc) {
        this.jdbc = jdbc;
        this.authorJdbc = authorJdbc;
    }

    @Override
    public LiteraryProduction getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query("SELECT * FROM AUTHORS_LITERARY_PRODUCTIONS WHERE ID = :id",
                Map.of("id", id),
                new LiteraryProductionExtractor());
    }

    @Override
    public void create(LiteraryProduction literaryProduction) {
        List<Long> id = jdbc.query("SELECT LITERARY_PRODUCTIONS_SEQ.nextval nval",
                (resultSet, i) -> resultSet.getLong("nval"));
        // у каждого произведения может быть несколько авторов
        // реализуем отношение когда у одного произведения может быть несколько
        // авторов (энциклопедии, совместно написанные романы итд)
        for (Author author : literaryProduction.getAuthors()) {
            jdbc.update("insert into AUTHORS_LITERARY_PRODUCTIONS (" +
                            "    ID,\n" +
                            "    NAME,\n" +
                            "    AUTHOR_ID\n" +
                            ")" +
                            "   values ( " +
                            "      :id,\n" +
                            "      :name,\n" +
                            "      :author_id\n" +
                            ")",
                    Map.of("id", id.get(0),
                            "name", literaryProduction.getName(),
                            "author_id", author.getId()));
        }
    }

    private class LiteraryProductionExtractor implements ResultSetExtractor<LiteraryProduction> {
        @Override
        public LiteraryProduction extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            LiteraryProduction literaryProduction = null;
            while (resultSet.next()) {
                if (literaryProduction == null) {
                    literaryProduction = new LiteraryProduction(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            new ArrayList<Author>());
                }
                literaryProduction.getAuthors().add(authorJdbc.getById(resultSet.getInt("author_id")));
            }
            return literaryProduction;
        }
    }
}


