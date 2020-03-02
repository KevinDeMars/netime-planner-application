package edu.baylor.csi3471.netime_planner.models;

/**
 * Represents a range of a day as the proportion of the number of hours.
 * For example, 9:00 AM to 3:00 PM would be {start = 0.375, end = 0.625} because it goes from hour 9/24 to hour 15/24.
 */
public class DayPercentageInterval {
    private double start;
    private double end;

    public DayPercentageInterval(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
