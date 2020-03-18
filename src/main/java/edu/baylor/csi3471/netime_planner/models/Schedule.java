package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@XmlRootElement(name="schedule")
public class Schedule {
    // Placeholder, we will use DB later
    private static int NEXT_ID = 1;
    private int id;
    private Collection<DateTimeInterval> workTimes = new ArrayList<>();
    private Collection<Event> events = new ArrayList<>();

    public Schedule() {
        id = NEXT_ID++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<DateTimeInterval> getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(Collection<DateTimeInterval> workTimes) {
        this.workTimes = workTimes;
    }

    @XmlElementRef // each thing in the list is an elementRef (because it's polymorphic)
    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id &&
                Objects.equals(workTimes, schedule.workTimes) &&
                Objects.equals(events, schedule.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workTimes, events);
    }

    @Override
    public String toString() {
        return "Schedule{id = " + id + ", " + events.size() + " events, " + workTimes.size() + " worktimes}";
    }
}
