package ru.otus.pvn.libraryApp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.pvn.libraryApp.models.Author;
import ru.otus.pvn.libraryApp.models.Book;
import ru.otus.pvn.libraryApp.models.Genre;
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

    private final String  selectBooks = "SELECT bks.id book_id,\n" +
            "                       bks.name book_name, \n" +
            "                       bks.isbn book_isbn,\n" +
            "                       gnrs.id genre_id, \n" +
            "                       gnrs.name genre_name,\n" +
            "                       lit.id literary_id, \n" +
            "                       lit.name literary_name,\n" +
            "                       aut.id author_id,\n" +
            "                       aut.fio author_fio,\n" +
            "                       aut.birthday author_birthday,\n" +
            "                       aut.date_of_death author_date_of_death \n" +
            "        FROM BOOKS bks\n" +
            "        JOIN LITERARY_IN_BOOKS lib ON bks.id = lib.book_id\n" +
            "        JOIN LITERARY lit ON lit.id = lib.literary_id\n" +
            "        JOIN AUTHORS_IN_LITERARY ail ON ail.literary_id = lit.id\n" +
            "        JOIN AUTHORS aut ON aut.id = ail.author_id\n" +
            "        JOIN GENRES gnrs ON gnrs.id = bks.genre_id\n";

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, GenreDaoJdbc genreJdbc, LiteraryProductionDaoJdbc literaryJdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query( selectBooks +
                        "WHERE bks.id = :id",
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
        jdbc.update("DELETE FROM LITERARY_IN_BOOKS WHERE BOOK_ID = :id", Map.of("id", id));
        jdbc.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(selectBooks+
                " ORDER BY book_id, literary_id", new BooksExtractor());
    }


    private class BookExtractor implements ResultSetExtractor<Book> {
        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> books = new BooksExtractor().extractData(resultSet);
            if (books.isEmpty()) return null;
                else return books.iterator().next();
        }
    }

    private class BooksExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            Map<Long, LiteraryProduction> literaryProductions = new HashMap<>();
            while (resultSet.next()) {
                Book book = books.get(resultSet.getLong("id"));
                if (book == null) {
                    book = new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("isbn"),
                            new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name")),
                            new ArrayList<LiteraryProduction>());
                    books.put(resultSet.getLong("id"), book);
                    literaryProductions =  new HashMap<>();
                }
                LiteraryProduction literaryProduction = literaryProductions.get(resultSet.getLong("literary_id"));
                if (literaryProduction == null) {
                    literaryProduction = new LiteraryProduction(
                            resultSet.getLong("literary_id"),
                            resultSet.getString("literary_name"),
                            new ArrayList<Author>());
                    literaryProductions.put(resultSet.getLong("literary_id"), literaryProduction);
                }
                literaryProduction.getAuthors().add(new Author(
                        resultSet.getLong("author_id"),
                        resultSet.getString("author_fio"),
                        resultSet.getDate("author_birthday"),
                        resultSet.getDate("author_date_of_death")));
                book.getLiteraryProductions().add(literaryProduction);
                books.replace(resultSet.getLong("id"), book);
            }
            return new ArrayList<>(books.values());
        }
    }
}

