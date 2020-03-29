package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Optional;

@XmlRootElement(name = "event")
public abstract class Event {
    // Placeholder. We will use DB instead later on.
    private static int NEXT_ID = 1;

    @XmlElement(required = true)
    private int id;

    // Name is required; if not entered by user, it can be "New Event" or similar
    @XmlElement(required = true)
    private String name;

    // Description is optional.
    @XmlElement(required = false)
    private String description;

    // Location is optional.
    @XmlElement(required = false)
    private String location;

    public Event() {
        id = NEXT_ID++;
        name = "New Event";
        description = location = null;
    }

    public Event(String name, String description, String location) {
        id = NEXT_ID++;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    public abstract boolean occursOnDay(LocalDate day);

    public void visit(EventVisitor v) {
        v.visit(this);
    }
}
