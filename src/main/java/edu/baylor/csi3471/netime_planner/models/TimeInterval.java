package edu.baylor.csi3471.netime_planner.models;

import edu.baylor.csi3471.netime_planner.models.adapters.LocalTimeAdapter;
import edu.baylor.csi3471.netime_planner.util.Formatters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;
import java.util.Objects;

// Represents a interval in time not attached to a specific date. e.g: 1:00 PM to 3:45 PM.
// The difference between this and TimeInterval is that DateTimeInterval DOES have a date attached to it; e.g: Monday 3/2 3:45 PM to Thursday 3/5 1:00 PM.

@XmlRootElement
public class TimeInterval {
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
    private LocalTime start;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
    private LocalTime end;

    // Required by JAXB
    public TimeInterval() {

    }

    public TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public TimeInterval(TimeInterval other) {
        start = LocalTime.of(other.start.getHour(), other.start.getMinute());
        end = LocalTime.of(other.end.getHour(), other.end.getMinute());
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    /**
     * Determines whether the interval contains a single instant (time without date) of time.
     * @param t the time to check.
     * @return Whether this interval contains t.
     */
    public boolean contains(LocalTime t) {
        return !(t.isBefore(start)) && !(t.isAfter(end));
    }

    @Override
    public String toString() {
        return start.format(Formatters.TWELVE_HOURS) + '-' + end.format(Formatters.TWELVE_HOURS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
