package ru.otus.pvn.libraryApp.models;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {

    private long id;

    private String fio;

    private Date birthday;

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
