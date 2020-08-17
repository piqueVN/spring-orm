package ru.otus.pvn.libraryApp.dao;

import ru.otus.pvn.libraryApp.models.Genre;

public interface GenreDao {
    Genre getById(long id);
    void create(Genre genre);
}
