package edu.baylor.csi3471.netime_planner.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class Schedule {
    private int id;
    private Collection<DateTimeInterval> workTimes = new ArrayList<>();
    // TODO: 1 Collection of events, or one for deadlines and one for activities?
    private Collection<Activity> activities = new ArrayList<>();
    private Collection<Deadline> deadlines = new ArrayList<>();

    public int getId() {
        return id;
    }

    public Collection<DateTimeInterval> getWorkTimes() {
        return workTimes;
    }

    public Collection<Event> getEvents() {
        var result = new ArrayList<Event>(activities); // copy activities
        result.addAll(deadlines);
        return Collections.unmodifiableCollection(result);
    }
    public Collection<Deadline> getDeadlines() {
        return deadlines;
    }
    public Collection<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity a) {
        activities.add(a);
    }

    public void addDeadline(Deadline d) {
        deadlines.add(d);
    }

    public Collection<Deadline> makeToDoList(DateTimeInterval period) {
        return getDeadlines().stream()
                .filter(d -> period.contains(d.getDueDateTime()))
                .collect(Collectors.toList());
    }
}
