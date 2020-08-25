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
        return jdbc.query("SELECT ail.AUTHOR_ID, \n" +
                        "ail.LITERARY_ID,\n" +
                        "FIO,\n" +
                        "BIRTHDAY,\n" +
                        "DATE_OF_DEATH,\n" +
                        "NAME \n" +
                        "FROM AUTHORS_IN_LITERARY ail\n" +
                        "JOIN AUTHORS aut ON ail.author_id = aut.id\n" +
                        "JOIN LITERARY lit ON ail.literary_id = lit.id\n" +
                        "WHERE LITERARY_ID = :id",
                Map.of("id", id),
                new LiteraryProductionExtractor());
    }

    @Override
    public void create(LiteraryProduction literaryProduction) {
        List<Long> id = jdbc.query("SELECT LITERARY_SEQ.nextval nval",
                (resultSet, i) -> resultSet.getLong("nval"));
            jdbc.update("insert into LITERARY (" +
                            "    ID,\n" +
                            "    NAME\n" +
                            ")" +
                            "   values ( " +
                            "      :id,\n" +
                            "      :name\n" +
                            ")",
                    Map.of("id", id.get(0),
                            "name", literaryProduction.getName()));
            for (Author author : literaryProduction.getAuthors()) {
                jdbc.update("insert into AUTHORS_IN_LITERARY (" +
                                "AUTHOR_ID,\n" +
                                "LITERARY_ID)\n" +
                                " values ( " +
                                ":lpId,\n" +
                                ":id)\n",
                        Map.of("id", id.get(0), "lpId", author.getId()));
        }
    }

    private class LiteraryProductionExtractor implements ResultSetExtractor<LiteraryProduction> {
        @Override
        public LiteraryProduction extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            LiteraryProduction literaryProduction = null;
            while (resultSet.next()) {
                if (literaryProduction == null) {
                    literaryProduction = new LiteraryProduction(resultSet.getLong("literary_id"),
                            resultSet.getString("name"),
                            new ArrayList<Author>());
                }
                literaryProduction.getAuthors().add(authorJdbc.getById(resultSet.getInt("author_id")));
            }
            return literaryProduction;
        }
    }
}


