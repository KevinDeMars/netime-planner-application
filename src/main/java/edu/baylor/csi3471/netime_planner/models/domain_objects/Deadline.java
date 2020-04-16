package edu.baylor.csi3471.netime_planner.models.domain_objects;

import edu.baylor.csi3471.netime_planner.models.EventVisitor;
import edu.baylor.csi3471.netime_planner.models.adapters.LocalDateTimeAdapter;
import org.jetbrains.annotations.Nullable;

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
    @XmlElement()
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    @Nullable
    private LocalDateTime start;

    // Optional. The Activity that the deadline is categorized under.
    @XmlTransient // Don't store this in the XML, we want to store the ID instead
    @Nullable
    // TODO: Handle serialization of this
    private Activity category;

    // Required for JAXB
    public Deadline() {

    }

    public Deadline(LocalDateTime due) {
        this.due = due;
    }

    public Deadline(String name, String description, String location, LocalDateTime due, @Nullable LocalDateTime start, @Nullable Activity category) {
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
        return LocalDate.from(this.due).equals(day);
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
