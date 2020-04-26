package edu.baylor.csi3471.netime_planner.models.domain_objects;

import edu.baylor.csi3471.netime_planner.models.EventVisitor;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Optional;

@XmlRootElement(name = "event")
public abstract class Event extends DomainObject {
    // Name is required; if not entered by user, it can be "New Event" or similar
    @XmlElement(required = true)
    private String name;

    // Description is optional.
    @XmlElement()
    @Nullable
    private String description;

    // Location is optional.
    @XmlElement()
    @Nullable
    private String location;

    public Event() {
        name = "New Event";
        description = location = null;
    }

    public Event(String name, @Nullable String description, @Nullable String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    public void setLocation(@Nullable String location) {
        this.location = location;
    }

    public abstract boolean occursOnDay(LocalDate day);

    public void acceptVisitor(EventVisitor v) {
        v.visit(this);
    }
}
