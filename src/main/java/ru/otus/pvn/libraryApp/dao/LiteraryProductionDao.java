package ru.otus.pvn.libraryApp.dao;

import ru.otus.pvn.libraryApp.models.LiteraryProduction;

public interface LiteraryProductionDao {
    LiteraryProduction getById(long id);
    void create(LiteraryProduction literaryProduction);
}
