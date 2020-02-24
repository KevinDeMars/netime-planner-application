package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalDateTime;

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

    public boolean contains(LocalDateTime dateTime) {
        return !(dateTime.isBefore(start)) && !(dateTime.isAfter(end));
    }
}
