package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalTime;

// Represents a interval in time not attached to a specific date. e.g: 1:00 PM to 3:45 PM.
// The difference between this and TimeInterval is that DateTimeInterval DOES have a date attached to it; e.g: Monday 3/2 3:45 PM to Thursday 3/5 1:00 PM.
public class TimeInterval {
    LocalTime start;
    LocalTime end;

    public TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
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
        return "TimeInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
