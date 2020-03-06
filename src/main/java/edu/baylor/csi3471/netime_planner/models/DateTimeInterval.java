package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalDateTime;

// Represents a interval in time attached to a specific DATE and time. E.g: Monday 3/2 3:45 PM to Thursday 3/5 1:00 PM.
// The difference between this and TimeInterval is that TimeInterval does not have a date attached to it; e.g. 1:00 PM to 3:45 PM.
public class DateTimeInterval {
    private LocalDateTime start;
    private LocalDateTime end;

    public DateTimeInterval(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Determines whether the interval contains a single instant (time + date) of time.
     * @param dateTime the instant (time + date) to check.
     * @return Whether this interval contains dateTime.
     */
    public boolean contains(LocalDateTime dateTime) {
        return !(dateTime.isBefore(start)) && !(dateTime.isAfter(end));
    }
}
