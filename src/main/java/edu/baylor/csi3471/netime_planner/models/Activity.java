package edu.baylor.csi3471.netime_planner.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class Activity extends Event {
    // Example: 3:50 PM to 4:25 PM. Not attached to a specific day.
    private TimeInterval time;
    // Example: Monday, Wednesday, Friday, for a MWF class. Uses EnumSet for concrete implementation.
    private Set<DayOfWeek> days;
    // Represents the first day the event COULD occur. However, if it is a MWF event, this could
    // be set to Sunday and it would still be valid, but the first actual day would be the Monday the next day.
    private LocalDate startDate;
    // Optional. Represents the last day the event COULD occur. For a recurring event with no specified end date,
    // this is null; for a non-recurring event, this is set to the same date as the start date.
    private LocalDate endDate;
    // How often the event repeats; e.g. 1 means every week; 3 means every three weeks. Maybe could be set to -1 for non-recurring?
    private int weekInterval;

    // For recurring activity
    public Activity(String name, String description, String location, TimeInterval time, Set<DayOfWeek> days, LocalDate startDate, LocalDate endDate, int weekInterval) {
        super(name, description, location);
        this.time = time;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekInterval = weekInterval;
    }

    // For non-recurring activity
    public Activity(String name, String description, String location, LocalDate singleDay, TimeInterval singleTime) {
        super(name, description, location);
        this.time = singleTime;
        this.days = EnumSet.of(singleDay.getDayOfWeek());
        weekInterval = -1;
        startDate = endDate = singleDay;
    }

    public boolean conflictsWith(Activity other) {
        /*
        Notes from meeting:
        o	getNextWeekDay (LocalDate start, Set<DayOfWeek> days)
        o	Repeat for LCM(weekInterval) weeks
            	If same day, check times
            •	TimeInterval.conflictsWith(other)
            •	Same time -> conflicts
            •	Otherwise, advance BOTH to next week day in set
            	If not same day, advance the earlier one to the next week day in set
         */
        // See MathUtils.LCM and DateUtils.getNextWeekDay

        // TODO
        return true;
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
        double [] theArray = new double[2];
        theArray[0] = ((double)time.start.getHour())/24.0;
        theArray[1] = ((double)time.end.getHour())/24.0;
        return theArray;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + getName() + '\'' +
                ", time=" + time +
                ", days=" + days +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", weekInterval=" + weekInterval +
                ", id=" + getId() +
                ", description=" + getDescription() +
                ", location=" + getLocation() +
                '}';
    }
}
