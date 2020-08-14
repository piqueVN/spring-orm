package ru.otus.pvn.libraryApp.models;

import lombok.*;
import java.util.List;

/**
 * Информация о литературном произведении
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LiteraryProduction {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<Author> authors;

    public LiteraryProduction(String name, List<Author> authors) {
        this.name = name;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "LiteraryProductionDao{" +
                "name='" + name + '\'' +
                ", authors=" + authors +
                '}';
    }
}
