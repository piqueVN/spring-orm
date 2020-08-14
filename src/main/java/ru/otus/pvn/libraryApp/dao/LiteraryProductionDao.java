package ru.otus.pvn.libraryApp.dao;

import ru.otus.pvn.libraryApp.models.LiteraryProduction;

public interface LiteraryProductionDao {
    public LiteraryProduction getById(long id);
    public void create(LiteraryProduction literaryProduction);
}
