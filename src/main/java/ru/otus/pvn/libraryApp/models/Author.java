package ru.otus.pvn.libraryApp.models;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String fio;

    @Getter
    @Setter
    private Date birthday;

    @Getter
    @Setter
    private Date dateOfDeath;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", birthday=" + birthday +
                ", dateOfDeath=" + dateOfDeath +
                '}';
    }

    public Author(String fio, Date birthday, Date dateOfDeath) {
        this.fio = fio;
        this.birthday = birthday;
        this.dateOfDeath = dateOfDeath;
    }
}
