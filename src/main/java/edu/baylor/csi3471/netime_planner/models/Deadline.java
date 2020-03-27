package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement
public class Deadline extends Event {
    // The instant (time + date) it is due.
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime due;

    // Optional. The instant (maybe should be just date?) the user started working or plans to start on the task.
    @XmlElement(required = false)
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime start;

    // Optional. The Activity that the deadline is categorized under.
    @XmlTransient // Don't store this in the XML, we want to store the ID instead
    // TODO: Handle serialization of this
    private Activity category;

    // Required for JAXB
    public Deadline() {

    }

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
        theArray[0] += ((double)due.getMinute()/60.0/24.0);
        return theArray;
    }

    @Override
    public int[] findDayOccurance() {
        int [] arr = new int[1];
        arr[0] = due.getDayOfWeek().getValue();
        return arr;
    }

    @Override
    public int getOccurance() {
        return 0;
    }

    public LocalDate getDay(){
        return due.toLocalDate();
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
                "}";
    }

    @Override
    public void visit(EventVisitor v) {
        v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deadline deadline = (Deadline) o;
        return Objects.equals(due, deadline.due) &&
                Objects.equals(start, deadline.start) &&
                Objects.equals(category, deadline.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(due, start, category);
    }
}
