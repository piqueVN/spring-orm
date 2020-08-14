package ru.otus.pvn.libraryApp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.pvn.libraryApp.models.Book;
import ru.otus.pvn.libraryApp.models.LiteraryProduction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao{

    private final NamedParameterJdbcOperations jdbc;
    private final GenreDaoJdbc  genreJdbc;
    private final LiteraryProductionDaoJdbc literaryProductionDaoJdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, GenreDaoJdbc genreJdbc, LiteraryProductionDaoJdbc literaryProductionDaoJdbc) {
        this.jdbc = jdbc;
        this.genreJdbc = genreJdbc;
        this.literaryProductionDaoJdbc = literaryProductionDaoJdbc;
    }


    @Override
    public Book getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query("SELECT * FROM BOOKS WHERE ID = :id",
                Map.of("id", id),
                new BookExtractor());
    }

    @Override
    public Book getByName(String name) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("name", name);
        return jdbc.query("SELECT * FROM BOOKS WHERE NAME = :name",
                Map.of("name", name),
                new BookExtractor());
    }

    @Override
    public void create(Book book) {
        List<Long> id = jdbc.query("SELECT BOOKS_SEQ.nextval nval",
                (resultSet, i) -> resultSet.getLong("nval"));
        for (LiteraryProduction lp : book.getLiteraryProductions()) {
            jdbc.update("insert into books (" +
                            " ID,\n" +
                            " NAME,\n" +
                            " ISBN,\n" +
                            " LITERARY_ID,\n" +
                            " GENRE_ID\n" +
                            ")" +
                            "   values ( " +
                            " :id,\n" +
                            " :name,\n" +
                            " :isbn,\n" +
                            " :literaryId,\n" +
                            " :genreId\n" +
                            ")",
                    Map.of("id", id.get(0),
                            "name", book.getName(),
                            "isbn", book.getIsbn(),
                            "literaryId", lp.getId(),
                            "genreId", book.getGenre().getId(),
                            "publishingId", lp.getId()));
        }
    }

    //это можно назвать говнокодом, но за неимением уникального ключа
    //проще удалить и создать заново c тем же айдишником, накладно, но гибко
    @Override
    public void update(Book book) {
        if (getById(book.getId()) != null) {
            deleteById(book.getId());
            for (LiteraryProduction lp : book.getLiteraryProductions()) {
                jdbc.update("insert into books (" +
                                " ID,\n" +
                                " NAME,\n" +
                                " ISBN,\n" +
                                " LITERARY_ID,\n" +
                                " GENRE_ID\n" +
                                ")" +
                                "   values ( " +
                                " :id,\n" +
                                " :name,\n" +
                                " :isbn,\n" +
                                " :literaryId,\n" +
                                " :genreId\n" +
                                ")",
                        Map.of("id", book.getId(),
                                "name", book.getName(),
                                "isbn", book.getIsbn(),
                                "literaryId", lp.getId(),
                                "genreId", book.getGenre().getId(),
                                "publishingId", lp.getId()));
            }
        }
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    private class BookExtractor implements ResultSetExtractor<ru.otus.pvn.libraryApp.models.Book> {
        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Book book = null;
            while (resultSet.next()) {
                if (book == null) {
                    book = new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("isbn"),
                            new ArrayList<LiteraryProduction>(),
                            genreJdbc.getById(resultSet.getLong("genre_id")));
                }
                book.getLiteraryProductions().add(literaryProductionDaoJdbc.getById(resultSet.getInt("literary_id")));
            }
            return book;
        }
    }

}
