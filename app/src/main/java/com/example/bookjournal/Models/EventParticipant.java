package com.example.bookjournal.Models;

import java.io.Serializable;

public class EventParticipant implements Serializable {
    private Integer id;
    private User user;
    private Event event;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventParticipant() {}

    public EventParticipant(Integer id, User user, Event event) {
        this.id = id;
        this.user = user;
        this.event = event;
    }
}
