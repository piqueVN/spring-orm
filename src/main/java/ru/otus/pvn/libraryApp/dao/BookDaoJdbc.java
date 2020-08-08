package ru.otus.pvn.libraryApp.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao{

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public void insert(Book book) {
        jdbc.update("insert into books (" +
                        "    NAME,\n" +
                        "    CREATE_DATE,\n" +
                        "    AUTHOR_ID,\n" +
                        "    GENRE_ID,\n" +
                        "    PUBLISHING_ID\n" +
                        ")"+
                        "   values ( " +
                        "      :name,\n" +
                        "      :createDate,\n" +
                        "      :authorId,\n" +
                        "      :genreId,\n" +
                        "      :publishingId" +
                        ")",
                Map.of("name", book.getName(),
                        "createDate", book.getCreateDate(),
                        "authorId", book.getAuthorId(),
                        "genreId", book.getGenreId(),
                        "publishingId", book.getPublishingId()));
    }

    @Override
    public void update(String id, Book book) {
        jdbc.update("UPDATE books SET \n" +
                            "NAME = :name, \n" +
                            "CREATE_DATE = :createDate, \n" +
                            "AUTHOR_ID = :authorId, \n" +
                            "GENRE_ID = :genreId, \n" +
                            "PUBLISHING_ID = :publishingId \n" +
                            "WHERE ID = :id",
                Map.of("name", book.getName(),
                        "createDate", book.getCreateDate(),
                        "authorId", book.getAuthorId(),
                        "genreId", book.getGenreId(),
                        "publishingId", book.getPublishingId(),
                        "id", id));
    }

    @Override
    public void deleteById(String id) {
        jdbc.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    @Override
    public Book getById(String id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM BOOKS WHERE ID = :id",
                Map.of("id", id),
                new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book(
                    resultSet.getString("name"),
                    resultSet.getString("create_date"),
                    resultSet.getInt("author_id"),
                    resultSet.getInt("genre_id"),
                    resultSet.getInt("publishing_id"));
            return book;
        }
    }

}
