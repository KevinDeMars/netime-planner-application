package edu.baylor.csi3471.netime_planner.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

public class Activity extends Event {
    private TimeInterval time;
    private Set<DayOfWeek> days; // see EnumSet
    private LocalDate startDate;
    private LocalDate endDate;
    private int weekInterval;

    // For recurring activity
    public Activity(String name, String description, String location, TimeInterval time, Set<DayOfWeek> days, LocalDate startDate, LocalDate endDate, int weekInterval) {
        super(name, description, location);
        this.time = time;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekInterval = weekInterval;
    }

    // For non-recurring activity
    public Activity(String name, String description, String location, LocalDate singleDay, TimeInterval singleTime) {
        super(name, description, location);
        this.time = singleTime;
        this.days = EnumSet.of(singleDay.getDayOfWeek());
        weekInterval = -1;
        startDate = endDate = singleDay;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + getName() + '\'' +
                ", time=" + time +
                ", days=" + days +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", weekInterval=" + weekInterval +
                ", id=" + getId() +
                ", description=" + getDescription() +
                ", location=" + getLocation() +
                '}';
    }
}
