package edu.baylor.csi3471.netime_planner.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Schedule {
    private int id;
    private Collection<DateTimeInterval> workTimes = new ArrayList<>();
    // TODO: 1 Collection of events, or one for deadlines and one for activities?
    private Collection<Event> events = new ArrayList<>();

    public int getId() {
        return id;
    }

    public Collection<DateTimeInterval> getWorkTimes() {
        return workTimes;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    // TODO: Should this return just deadlines that are due during the period?
    public Collection<Event> makeToDoList(DateTimeInterval period) {
        return getDeadlines()
                .filter(d -> period.contains(d.getDueDateTime()))
                .collect(Collectors.toList());
    }

    private Stream<Deadline> getDeadlines() {
        return events.stream().filter(e -> e instanceof Deadline).map(Deadline.class::cast);
    }
}
