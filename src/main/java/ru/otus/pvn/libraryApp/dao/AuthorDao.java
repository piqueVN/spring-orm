package ru.otus.pvn.libraryApp.dao;

import ru.otus.pvn.libraryApp.models.Author;

public interface AuthorDao {
    public Author getById(long id);
    public Author getByFio(String name);
    public void create(Author author);
}
