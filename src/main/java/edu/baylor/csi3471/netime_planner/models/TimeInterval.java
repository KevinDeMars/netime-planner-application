package edu.baylor.csi3471.netime_planner.models;

import java.time.LocalTime;

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
