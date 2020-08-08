package ru.otus.pvn.libraryApp.shell;


import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pvn.libraryApp.dao.Book;
import ru.otus.pvn.libraryApp.dao.BookDaoJdbc;

import java.sql.SQLException;

@ShellComponent
public class ShellCommands {

    private final BookDaoJdbc jdbc;

    public ShellCommands(BookDaoJdbc jdbc) {
        this.jdbc = jdbc;
    }

    @ShellMethod(value="run H2 console", key = {"console"})
    public String runConsoleH2() throws SQLException {
        Console.main();
        return "Консоль H2 запущена";
    }

    @ShellMethod(value="add Book to DB", key = {"add-book"})
    public String createBook(@ShellOption String bookName,
                             @ShellOption String createDate,
                             @ShellOption String authorId,
                             @ShellOption String genreId,
                             @ShellOption String publishingId) throws SQLException {
        Book book = new Book(bookName,
                            createDate,
                            Integer.valueOf(authorId),
                            Integer.valueOf(genreId),
                            Integer.valueOf(publishingId));
        jdbc.insert(book);
        return String.format("Книга %s добавлена", bookName);
    }

    @ShellMethod(value="update Book in DB", key = {"update-book"})
    public String updateBook(@ShellOption String id,
                             @ShellOption String bookName,
                             @ShellOption String createDate,
                             @ShellOption String authorId,
                             @ShellOption String genreId,
                             @ShellOption String publishingId) throws SQLException {
        Book book = new Book(bookName,
                createDate,
                Integer.valueOf(authorId),
                Integer.valueOf(genreId),
                Integer.valueOf(publishingId));
        jdbc.update(id, book);
        return String.format("Книга %s обновлена", bookName);
    }

    @ShellMethod(value="delete Book in DB", key = {"delete-book"})
    public String deleteBook(@ShellOption String id) throws SQLException {
        jdbc.deleteById(id);
        return String.format("Книга id=%s удалена", id);
    }

    @ShellMethod(value="get Book by ID", key = {"get-book"})
    public String getBookById(@ShellOption String id) throws SQLException {
        return String.format("Книга %s получена", jdbc.getById(id).toString());
    }
}
