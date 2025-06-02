package com.example.bookjournal.Models;

import java.io.Serializable;

public class Books implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private Float rating;
    private boolean added;

    public Books(String title, String author, String genre, String publisher, int i, int i1) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public Books(Integer id, String title, String author, String publisher, Float rating, boolean added, String genre) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.rating = rating;
        this.added = added;
        this.genre = genre;
    }
    public Books() {}

}
