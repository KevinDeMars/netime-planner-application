package edu.baylor.csi3471.netime_planner.models;

import java.util.Optional;

public abstract class Event {
    private int id;
    private String name;
    private String description;
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

}
