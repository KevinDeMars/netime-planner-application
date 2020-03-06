package edu.baylor.csi3471.netime_planner.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public LocalDate getNextWeekDay(LocalDate start) {
        boolean isTheDay = false;
        do {
            for (DayOfWeek day : days) {
                isTheDay = start.getDayOfWeek().equals(day);
                if (isTheDay)
                    break;
            }
            if (!isTheDay)
                start = start.plusDays(1);
        } while (!isTheDay);

        return start;
    }

    public boolean conflictsWith(Activity other, int numOfWeeks) {

        if(!(this.time.contains(other.time.getStart()))
                || (this.time.contains(other.time.getEnd()))
                || (other.time.contains(this.time.getStart()))
                || (other.time.contains(this.time.getEnd())))
            return false;

        Set<DayOfWeek> commonDays = this.days.stream()
                .filter(other.days::contains)
                    .collect(Collectors.toSet());
        if(commonDays.isEmpty())
            return false;

        LocalDate date1 = this.getNextWeekDay(LocalDate.now());
        LocalDate date2 = other.getNextWeekDay(LocalDate.now());
        boolean collides = false;
        do {
            if (date1.equals(date2)) {
                collides = true;
            } else if (date1.isBefore(date2))
                date1 = this.getNextWeekDay(date1.plusDays(1));
            else
                date2 = other.getNextWeekDay(date2.plusDays(1));
        } while (!collides && !(date1.isAfter(LocalDate.now().plusWeeks(numOfWeeks)) && date2.isAfter(LocalDate.now().plusWeeks(numOfWeeks))));
        return collides;
    }

    @Override
    public boolean occursOnDay(LocalDate day) {
        return false;
    }

    @Override
    public DayPercentageInterval findDayPercentageInterval(LocalDate day) {
        return null;
    }

    @Override
    public double[] findPercentage() {
        return new double[0];
    }
}
