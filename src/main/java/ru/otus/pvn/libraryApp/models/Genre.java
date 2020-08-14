package ru.otus.pvn.libraryApp.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Genre {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
