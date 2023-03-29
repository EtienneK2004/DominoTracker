package com.example.dominotracker.model.events;

import java.time.LocalDate;

public class Event {
    private String name;
    private String startTime;
    private String description;
    private LocalDate date;

    public Event(String name, String startTime, String description, LocalDate date) {
        this.name = name;
        this.startTime = startTime;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventTime() {
        return startTime;
    }

    public void setEventTime(String startTime) {
        this.startTime = startTime;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
