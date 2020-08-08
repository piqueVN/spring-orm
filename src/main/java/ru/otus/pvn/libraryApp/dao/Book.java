package ru.otus.pvn.libraryApp.dao;

public class Book {
    private String name;
    private String createDate;
    private int authorId;
    private int genreId;
    private int publishingId;

    public Book(String name, String createDate, int authorId, int genreId, int publishingId) {
        this.name = name;
        this.createDate = createDate;
        this.authorId = authorId;
        this.genreId = genreId;
        this.publishingId = publishingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getPublishingId() {
        return publishingId;
    }

    public void setPublishingId(int publishingId) {
        this.publishingId = publishingId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", createDate='" + createDate + '\'' +
                ", authorId=" + authorId +
                ", genreId=" + genreId +
                ", publishingId=" + publishingId +
                '}';
    }
}
