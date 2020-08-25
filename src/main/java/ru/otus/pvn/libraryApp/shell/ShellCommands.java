package ru.otus.pvn.libraryApp.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.otus.pvn.libraryApp.dao.BookDaoJdbc;
import ru.otus.pvn.libraryApp.dao.GenreDaoJdbc;
import ru.otus.pvn.libraryApp.dao.LiteraryProductionDaoJdbc;
import ru.otus.pvn.libraryApp.models.Author;
import ru.otus.pvn.libraryApp.models.Book;
import ru.otus.pvn.libraryApp.models.Genre;
import ru.otus.pvn.libraryApp.models.LiteraryProduction;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ShellComponent
public class ShellCommands {

    private final AuthorDaoJdbc jdbcAuthor;
    private final LiteraryProductionDaoJdbc jdbcAuthorLiterary;
    private final GenreDaoJdbc jdbcGenreDao;
    private final BookDaoJdbc jdbcBookDao;

    public ShellCommands(AuthorDaoJdbc jdbcAuthor, LiteraryProductionDaoJdbc jdbcAuthorLiterary, GenreDaoJdbc jdbcGenreDao, BookDaoJdbc jdbcBookDao) {
        this.jdbcAuthor = jdbcAuthor;
        this.jdbcAuthorLiterary = jdbcAuthorLiterary;
        this.jdbcGenreDao = jdbcGenreDao;
        this.jdbcBookDao = jdbcBookDao;
    }

    @ShellMethod(value = "run H2 console", key = {"console"})
    public String runConsoleH2() throws SQLException {
        Console.main();
        return "Консоль H2 запущена";
    }

    @ShellMethod(value = "add Author to DB", key = {"add-author"})
    public String addAuthor(@ShellOption String fio,
                            @ShellOption String birthday,
                            @ShellOption String dayOfDeath) throws SQLException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Author author = null;
        author = new Author(fio, format.parse(birthday), format.parse(dayOfDeath));
        jdbcAuthor.create(author);
        return String.format("Автор %s добавлен", author);
    }

    //Пользователь вводит имя произведения и список id авторов,
    //в дальнейшем не сложно переделать что будет вводить ФИО
    //Без фанатизма (с)
    @ShellMethod(value = "add LiteraryProduction to DB", key = {"add-lp"})
    public String addAuthor(@ShellOption String name,
                            @ShellOption String author_id) throws SQLException, ParseException {
        LiteraryProduction literaryProduction = new LiteraryProduction( name, new ArrayList<Author>());
        for (String id : author_id.split(",")) {
            literaryProduction.getAuthors().add(jdbcAuthor.getById(Long.parseLong(id)));
        }
        jdbcAuthorLiterary.create(literaryProduction);
        return String.format("Произведение %s добавлено", name);
    }

    @ShellMethod(value = "add Genre to DB", key = {"add-genre"})
    public String addAuthor(@ShellOption String name) throws SQLException {
        Genre genre = new Genre(name);
        jdbcGenreDao.create(genre);
        return String.format("Жанр %s добавлен", name);
    }

    @ShellMethod(value = "delete book from DB", key = {"delete-book"})
    public String deleteBook(@ShellOption long id) throws SQLException {
        jdbcBookDao.deleteById(id);
        return String.format("Книга id=%s удалена", id);
    }

    @ShellMethod(value = "get book from DB", key = {"get-book"})
    public String getBook(@ShellOption long id) throws SQLException {
        Book book = jdbcBookDao.getById(id);
        return book.toString();
    }


    @ShellMethod(value = "add book to DB", key = {"add-book"})
    public String addBook(@ShellOption String name,
                          @ShellOption String isbn,
                          @ShellOption String literarysId, // вводить через запятую, например 1,2
                          @ShellOption String genreId) throws SQLException {
        Book book = new Book();
        book.setName(name);
        book.setIsbn(isbn);
        book.setGenre(jdbcGenreDao.getById(Long.parseLong(genreId)));
        List<LiteraryProduction> literaryProductions = new ArrayList<>();
        for (String id : literarysId.split(",")) {
            literaryProductions.add(jdbcAuthorLiterary.getById(Long.parseLong(id)));
        }
        book.setLiteraryProductions(literaryProductions);
        jdbcBookDao.create(book);
        return "Книга " + book.toString() + "добавлена!";
    }

    @ShellMethod(value = "update book in DB", key = {"update-book"})
    public String updateBookById(@ShellOption String id,
                                 @ShellOption String name,
                                 @ShellOption String isbn,
                                 @ShellOption String genreId) throws SQLException {

            Book bookForUpdate = new Book();
            bookForUpdate.setId(Long.parseLong(id));
            bookForUpdate.setName(name);
            bookForUpdate.setIsbn(isbn);
            bookForUpdate.setGenre(jdbcGenreDao.getById(Long.parseLong(genreId)));
            jdbcBookDao.update(bookForUpdate);
            return "Книга " + bookForUpdate.toString() + "обновлена!";
        }
    }






