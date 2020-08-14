package ru.otus.pvn.libraryApp.dao;


import ru.otus.pvn.libraryApp.models.Book;

public interface BookDao {
    public ru.otus.pvn.libraryApp.models.Book getById(long id);
    public ru.otus.pvn.libraryApp.models.Book getByName(String name);
    public void create(Book book);
    public void update(Book book);
    public void deleteById(long book);
}
