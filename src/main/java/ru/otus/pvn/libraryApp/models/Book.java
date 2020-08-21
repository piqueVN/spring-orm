package ru.otus.pvn.libraryApp.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    private List<LiteraryProduction> literaryProductions;

    private long id;

    private String name;

    private String isbn;

    private Genre genre;

    public Book(long id, String name, String isbn, Genre genre, List<LiteraryProduction> literaryProductions) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.literaryProductions = literaryProductions;
        this.genre = genre;
    }


    @Override
    public String toString() {
        return "Book{" +
                "  id=" + id +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", literaryProductions=" + literaryProductions +
                ", genre=" + genre +
                '}';
    }
}
