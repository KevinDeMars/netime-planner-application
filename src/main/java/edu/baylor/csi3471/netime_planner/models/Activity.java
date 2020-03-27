package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement
public class Activity extends Event {
    @XmlElement(required = true)
    private TimeInterval time;

    @XmlElement(required = true)
    private Set<DayOfWeek> days; // see EnumSet

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlElement(required = false)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate endDate;

    @XmlElement(required = true)
    private int weekInterval;

    public Activity() {
        // required for JAXB
    }

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

    //@Override
    /*public String toString() {
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
    */
    public LocalDate getNextWeekDay(LocalDate start) {
        boolean isTheDay = false;
        do {
            for (DayOfWeek day : days) {
                isTheDay = start.getDayOfWeek().equals(day);
                if (isTheDay)
                    break;
            }
            if (!isTheDay)
                start = start.plusDays(1);
        } while (!isTheDay);

        return start;
    }

    public boolean conflictsWith(Activity other, int numOfWeeks) {

        if(!(this.time.contains(other.time.getStart()))
                || (this.time.contains(other.time.getEnd()))
                || (other.time.contains(this.time.getStart()))
                || (other.time.contains(this.time.getEnd())))
            return false;

        Set<DayOfWeek> commonDays = this.days.stream()
                .filter(other.days::contains)
                    .collect(Collectors.toSet());
        if(commonDays.isEmpty())
            return false;

        LocalDate date1 = this.getNextWeekDay(LocalDate.now());
        LocalDate date2 = other.getNextWeekDay(LocalDate.now());
        boolean collides = false;
        do {
            if (date1.equals(date2)) {
                collides = true;
            } else if (date1.isBefore(date2))
                date1 = this.getNextWeekDay(date1.plusDays(1));
            else
                date2 = other.getNextWeekDay(date2.plusDays(1));
        } while (!collides && !(date1.isAfter(LocalDate.now().plusWeeks(numOfWeeks)) && date2.isAfter(LocalDate.now().plusWeeks(numOfWeeks))));
        return collides;
    }

    @Override
    public boolean occursOnDay(LocalDate day) {
        return false;
    }

    @Override
    public DayPercentageInterval findDayPercentageInterval(LocalDate day) {
        return null;
    }

    @Override
    public double[] findPercentage() {

        double [] theArray = new double[2];
        theArray[0] = ((double)time.start.getHour())/24.0;
        theArray[1] = ((double)time.end.getHour())/24.0;
        theArray[0] += ((double)time.start.getMinute()/60.0/24.0);
        theArray[1] += ((double)time.start.getMinute()/60.0/24.0);

        return theArray;
    }

    @Override
    public int[] findDayOccurance() {
        int[] arr = new int[days.size()];
        int i = 0;
        if(days.contains(DayOfWeek.SUNDAY)){
            arr[i] = 0;
            i++;
        }
        if(days.contains(DayOfWeek.MONDAY)){
            arr[i] = 1;
            i++;
        }
        if(days.contains(DayOfWeek.TUESDAY)){
            arr[i] = 2;
            i++;
        }
        if(days.contains(DayOfWeek.WEDNESDAY)){
            arr[i] = 3;
            i++;
        }
        if(days.contains(DayOfWeek.THURSDAY)){
            arr[i] = 4;
            i++;
        }
        if(days.contains(DayOfWeek.FRIDAY)){
            arr[i] = 5;
            i++;
        }
        if(days.contains(DayOfWeek.SATURDAY)){
            arr[i] = 6;
        }
        return arr;
    }

    @Override
    public int getOccurance() {
        return this.weekInterval;

    }

    public LocalDate getDay(){
        return startDate;
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

    @Override
    public void visit(EventVisitor v) {
        v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return weekInterval == activity.weekInterval &&
                Objects.equals(time, activity.time) &&
                Objects.equals(days, activity.days) &&
                Objects.equals(startDate, activity.startDate) &&
                Objects.equals(endDate, activity.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, days, startDate, endDate, weekInterval);
    }
}
