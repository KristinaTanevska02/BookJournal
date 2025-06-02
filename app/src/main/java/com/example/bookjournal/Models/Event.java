package com.example.bookjournal.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Event implements Serializable {
    private Integer id;
    private User organizer;
    private String place;
    private LocalDateTime dateTime;
    private Double latStart;
    private Double lonStart;

    public Event(Integer id, String place, LocalDateTime dateTime, Double latStart, Double lonStart) {
        this.id = id;
        this.place = place;
        this.dateTime = dateTime;
        this.latStart = latStart;
        this.lonStart = lonStart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getLatStart() {
        return latStart;
    }

    public void setLatStart(Double latStart) {
        this.latStart = latStart;
    }

    public Double getLonStart() {
        return lonStart;
    }

    public void setLonStart(Double lonStart) {
        this.lonStart = lonStart;
    }

    public Event(Integer id, User organizer, String place, LocalDateTime dateTime, Double latStart, Double lonStart) {
        this.id = id;
        this.organizer = organizer;
        this.place = place;
        this.dateTime = dateTime;
        this.latStart = latStart;
        this.lonStart = lonStart;
    }
    public Event() {}

}
