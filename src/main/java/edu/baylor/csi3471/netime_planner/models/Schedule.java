package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement(name = "schedule")
public class Schedule {
    // Placeholder, we will use DB later
    private static int NEXT_ID = 1;

    @XmlElement(required = true)
    private int id;

    @XmlElementWrapper(name = "workTimes", required = true)
    private Collection<DateTimeInterval> workTimes = new ArrayList<>();

    @XmlElementWrapper(name = "events", required = true)
    @XmlElementRef
    private Collection<Event> events = new ArrayList<>();

    public Schedule() {
        id = NEXT_ID++;
    }

    public int getId() {
        return id;
    }

    public Collection<DateTimeInterval> getWorkTimes() {
        return workTimes;
    }

    public Collection<Event> getEvents() {
        return events;
    }

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
