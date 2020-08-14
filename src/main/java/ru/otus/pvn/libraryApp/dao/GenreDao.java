package ru.otus.pvn.libraryApp.dao;

import ru.otus.pvn.libraryApp.models.Genre;

public interface GenreDao {
    public Genre getById(long id);
    public void create(Genre genre);
}
