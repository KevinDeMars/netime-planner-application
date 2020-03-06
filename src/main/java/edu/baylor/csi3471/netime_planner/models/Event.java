package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalDate;
import java.util.Optional;

public abstract class Event {
    // We won't worry about ID for now
    private int id;
    // Name is required; if not entered by user, it can be "New Event" or similar
    private String name;
    // Description is optional.
    private String description;
    // Location is optional.
    private String location;

    public Event() {
        name = "New Event";
        description = location = null;
    }

    public Event(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    public abstract boolean occursOnDay(LocalDate day);

    public abstract DayPercentageInterval findDayPercentageInterval(LocalDate day);
}
