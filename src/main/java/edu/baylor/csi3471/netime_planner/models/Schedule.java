package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@XmlRootElement(name = "schedule")
public class Schedule {
    private int id;
    private Collection<DateTimeInterval> workTimes = new ArrayList<>();
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
    /*public Collection<Deadline> getDeadlines() {
        return deadlines;
    }
    public Collection<Activity> getActivities() {
        return activities;
    }*/

    public void addEvent(Event e) {
        events.add(e);
    }

    public Collection<Deadline> makeToDoList(DateTimeInterval period) {
        var result = new ArrayList<Deadline>();
        var visitor = new EventVisitor() {
            @Override
            void visit(Deadline d) {
                if (period.contains(d.getDueDateTime()))
                    result.add(d);
            }
        };
        getEvents().forEach(e -> e.visit(visitor));
        return result;
    }
}
