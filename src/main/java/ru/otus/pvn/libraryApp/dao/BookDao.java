package ru.otus.pvn.libraryApp.dao;


import ru.otus.pvn.libraryApp.models.Book;

import java.util.List;

public interface BookDao {
    Book getById(long id);
    void create(Book book);
    void update(Book book);
    void deleteById(long book);
    List<Book> getAll();
}
