package ru.otus.pvn.libraryApp.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Genre {

    private long id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
