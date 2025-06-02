package com.example.bookjournal.Models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;
    private String fullName;
    private Integer age;
    private String UserId;


    public User(String userId, String fullName, int age) {
    }

    public User(int id, String fullName, int age) {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    private List<MyBooks> myBooks;
    private List<Event> organizedEvents;
    private List<Event> participatingEvents;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<MyBooks> getMyBooks() {
        return myBooks;
    }

    public void setMyBooks(List<MyBooks> myBooks) {
        this.myBooks = myBooks;
    }

    public List<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    public void setOrganizedEvents(List<Event> organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public List<Event> getParticipatingEvents() {
        return participatingEvents;
    }

    public void setParticipatingEvents(List<Event> participatingEvents) {
        this.participatingEvents = participatingEvents;
    }

    public User() {}

    public User(Integer id, String UserId, String fullName, Integer age) {
        this.id = id;
        this.UserId = UserId;
        this.fullName = fullName;
        this.age = age;
    }
}
