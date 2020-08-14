package ru.otus.pvn.libraryApp.models;

import lombok.*;
import ru.otus.pvn.libraryApp.models.Author;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String isbn;

    @Getter
    @Setter
    private List<LiteraryProduction> literaryProductions;

    @Getter
    @Setter
    private Genre genre;


    public Book(String name, String isbn, ArrayList<LiteraryProduction> literaryProductions, Genre genre) {
        this.name = name;
        this.isbn = isbn;
        this.literaryProductions = literaryProductions;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", literaryProductions=" + literaryProductions +
                ", genre=" + genre +
                '}';
    }
}
