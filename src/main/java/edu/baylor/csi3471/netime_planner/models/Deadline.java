package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Deadline extends Event {
    // The instant (time + date) it is due.
    private LocalDateTime due;
    // Optional. The instant (maybe should be just date?) the user started working or plans to start on the task.
    private LocalDateTime start;
    // Optional. The Activity that the deadline is categorized under.
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
    public boolean occursOnDay(LocalDate day) {
        // TODO
        return false;
    }

    @Override
    public DayPercentageInterval findDayPercentageInterval(LocalDate day) {
        // TODO
        return null;
    }

    @Override
    public double[] findPercentage() {
        double [] theArray = new double[1];
        theArray[0] = ((double)due.getHour())/24.0;
        return theArray;
    }

    @Override
    public int[] findDayOccurance() {
        int [] arr = new int[1];
        arr[0] = due.getDayOfWeek().getValue();
        return arr;
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

    @Override
    public void visit(EventVisitor v) {
        v.visit(this);
    }
}
