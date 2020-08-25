package ru.otus.pvn.libraryApp.dao;

import ru.otus.pvn.libraryApp.models.Author;

public interface AuthorDao {
    Author getById(long id);
    Author getByFio(String name);
    void create(Author author);
}
