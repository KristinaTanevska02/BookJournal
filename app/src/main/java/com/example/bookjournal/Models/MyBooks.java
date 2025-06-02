package com.example.bookjournal.Models;

import java.io.Serializable;
import java.util.Date;

public class MyBooks implements Serializable {
    private Integer id;
    private Books book; // Reference to the original book
    private User user; // Owner of this MyBook
    private String title;
    private String author;
    private String publisher;
    private Float globalRating;
    private Float myRating;
    private Integer likes;
    private Date dateAdded;
    private String review;
    private boolean isRead;
    private boolean isBorrowed;
    private boolean isMineBorrowed;
    private boolean isFave;
    private boolean currentlyReading;
    private boolean wantToRead;

    public MyBooks(int myBookId, String title, String author, String publisher, float globalRating, float myRating, int likes, String dateAdded, String review, boolean isRead, boolean isBorrowed, boolean isMineBorrowed, boolean isFave, boolean currentlyReading, boolean wantToRead) {
    }

    public boolean isCurrentlyReading() {
        return currentlyReading;
    }

    public void setCurrentlyReading(boolean currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

    public boolean isWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(boolean wantToRead) {
        this.wantToRead = wantToRead;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public boolean isMineBorrowes() {
        return isMineBorrowed;
    }

    public void setMineBorrowes(boolean mineBorrowes) {
        isMineBorrowed = mineBorrowes;
    }

    public boolean isFave() {
        return isFave;
    }

    public void setFave(boolean fave) {
        isFave = fave;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Float getGlobalRating() {
        return globalRating;
    }

    public void setGlobalRating(Float globalRating) {
        this.globalRating = globalRating;
    }

    public Float getMyRating() {
        return myRating;
    }

    public void setMyRating(Float myRating) {
        this.myRating = myRating;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public MyBooks() {}

    public MyBooks(Integer id, Books book, User user, String title, String author, String publisher, Float globalRating, Float myRating, Integer likes, Date dateAdded, String review, boolean isRead, boolean isBorrowed, boolean isMineBorrowed, boolean isFave, boolean currentlyReading, boolean wantToRead) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.globalRating = globalRating;
        this.myRating = myRating;
        this.likes = likes;
        this.dateAdded = dateAdded;
        this.review = review;
        this.isRead = isRead;
        this.isBorrowed = isBorrowed;
        this.isMineBorrowed = isMineBorrowed;
        this.isFave = isFave;
        this.currentlyReading = currentlyReading;
        this.wantToRead = wantToRead;
    }
}
