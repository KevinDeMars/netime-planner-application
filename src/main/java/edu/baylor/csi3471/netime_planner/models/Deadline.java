package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalDateTime;
import java.util.Optional;

public class Deadline extends Event {
    private LocalDateTime due;
    private LocalDateTime start;
    private Activity category;

    public Deadline(LocalDateTime due) {
        this.due = due;
    }

    public Deadline(String name, String description, String location, LocalDateTime due, LocalDateTime start, Activity category) {
        super(name, description, location);
        this.due = due;
        this.start = start;
        this.category = category;
    }

    public LocalDateTime getDueDateTime() {
        return due;
    }

    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(start);
    }

    public Optional<Activity> getCategory() {
        return Optional.ofNullable(category);
    }

    @Override
    public String toString() {
        return "Deadline{" +
                "name='" + getName() + '\'' +
                ", due=" + due +
                ", start=" + start +
                ", category=" + category +
                ", dueDateTime=" + getDueDateTime() +
                ", startTime=" + getStartTime() +
                ", id=" + getId() +
                ", description=" + getDescription() +
                ", location=" + getLocation() +
                "} " + super.toString();
    }
}
