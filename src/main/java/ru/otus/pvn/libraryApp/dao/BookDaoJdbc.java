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
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final GenreDaoJdbc genreJdbc;
    private final LiteraryProductionDaoJdbc literaryJdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, GenreDaoJdbc genreJdbc, LiteraryProductionDaoJdbc literaryJdbc) {
        this.jdbc = jdbc;
        this.genreJdbc = genreJdbc;
        this.literaryJdbc = literaryJdbc;
    }

    @Override
    public Book getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query("SELECT * FROM LITERARY_IN_BOOKS lb\n" +
                             "JOIN BOOKS bks ON bks.id = lb.book_id\n" +
                             "WHERE id = :id",
                Map.of("id", id),
                new BookExtractor());
    }

    @Override
    public void create(Book book) {
        List<Long> id = jdbc.query("SELECT LITERARY_SEQ.nextval nval",
                (resultSet, i) -> resultSet.getLong("nval"));
        jdbc.update("insert into books (" +
                        " ID,\n" +
                        " NAME,\n" +
                        " ISBN,\n" +
                        " GENRE_ID\n" +
                        ")" +
                        "   values ( " +
                        " :id,\n" +
                        " :name,\n" +
                        " :isbn,\n" +
                        " :genreId\n" +
                        ")",
                Map.of( "id", id,
                        "name", book.getName(),
                        "isbn", book.getIsbn(),
                        "genreId", book.getGenre().getId()));
        for (LiteraryProduction lp : book.getLiteraryProductions()) {
            jdbc.update("insert into literary_in_books (" +
                    "BOOK_ID,\n" +
                    "LITERARY_ID)\n" +
                    " values ( " +
                    ":id,\n" +
                    ":lpId)\n",
                Map.of("id", id, "lpId", lp.getId()));
        }
    }

    @Override
    public void update(Book book) {
            jdbc.update("update books  SET" +
                            " NAME = :name,\n" +
                            " ISBN = :isbn,\n" +
                            " GENRE_ID = :genreId\n" +
                            "WHERE ID = :id",
                    Map.of("id", book.getId(),
                            "name", book.getName(),
                            "isbn", book.getIsbn(),
                            "genreId", book.getGenre().getId() ));
        }



    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM LITERARY_IN_BOOKS WHERE BOOK_ID = :id", Map.of("id", id));
        jdbc.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT * FROM LITERARY_IN_BOOKS lb\n" +
                        "        JOIN BOOKS bks ON bks.id = lb.book_id\n" +
                        "        ORDER BY ID",
                new AllBookExtractor());
    }


    private class BookExtractor implements ResultSetExtractor<Book> {
        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Book book = null;
            while (resultSet.next()) {
                if (book == null) {
                    book = new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("isbn"),
                            genreJdbc.getById(resultSet.getLong("genre_id")),
                            new ArrayList<LiteraryProduction>());
            }
            book.getLiteraryProductions().add(literaryJdbc.getById(resultSet.getLong("literary_id")));
        }
            return book;
        }
    }

    private class AllBookExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> books = new ArrayList<>();
            long id = 0;
            long next_id;
            Book book = null;
            while (resultSet.next()) {
                next_id = resultSet.getLong("id");
                if (id != next_id) {
                    if (id != 0) books.add(book);
                    book = new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("isbn"),
                            genreJdbc.getById(resultSet.getLong("genre_id")),
                            new ArrayList<LiteraryProduction>());
                }
                    book.getLiteraryProductions().add(literaryJdbc.getById(resultSet.getLong("literary_id")));
                    id = resultSet.getLong("id");
            }
            books.add(book);
            return books;
        }
    }
}
