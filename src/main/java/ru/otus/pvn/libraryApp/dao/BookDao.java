package ru.otus.pvn.libraryApp.dao;

public interface BookDao {
    public void insert(Book book);
    public void update(String id, Book book);
    public void deleteById(String id);
    public Book getById(String id);
}
