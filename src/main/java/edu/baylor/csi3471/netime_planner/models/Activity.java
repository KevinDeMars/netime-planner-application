package edu.baylor.csi3471.netime_planner.models;

import edu.baylor.csi3471.netime_planner.util.DateUtils;
import edu.baylor.csi3471.netime_planner.util.MathUtils;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@XmlRootElement
public class Activity extends Event {
    private static final Logger LOGGER = Logger.getLogger(Activity.class.getName());

    @XmlElement(required = true)
    private TimeInterval time;

    @XmlElement(required = true)
    private Set<DayOfWeek> days; // see EnumSet

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlElement()
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @Nullable
    private LocalDate endDate;

    @XmlElement()
    @Nullable
    private Integer weekInterval;

    public Activity() {

    }

    // For recurring activity
    public Activity(String name, String description, String location, TimeInterval time, Set<DayOfWeek> days, LocalDate startDate, @Nullable LocalDate endDate, int weekInterval) {
        super(name, description, location);
        this.time = time;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekInterval = weekInterval;
        normalizeStartAndEndDates();
    }

    // For non-recurring activity
    public Activity(String name, String description, String location, LocalDate singleDay, TimeInterval singleTime) {
        super(name, description, location);
        this.time = singleTime;
        this.days = EnumSet.of(singleDay.getDayOfWeek());
        weekInterval = null;
        startDate = endDate = singleDay;
        normalizeStartAndEndDates();
    }

    private void normalizeStartAndEndDates() {
        var origStart = startDate;
        while (!days.contains(startDate.getDayOfWeek())) {
            startDate = startDate.plusDays(1);
        }
        if (!origStart.equals(startDate)) {
            LOGGER.fine(getName() + ": Normalized start date from " + origStart + " to " + startDate);
        }

        if (endDate != null) {
            var origEnd = endDate;
            while (!days.contains(endDate.getDayOfWeek()))
                endDate = endDate.plusDays(-1);
            if (!origEnd.equals(endDate)) {
                LOGGER.fine(getName() + ": Normalized end date from " + origEnd + " to " + endDate);
            }
        }
    }

    /**
     * Finds the next time the activity occurs after the given date (excluding the given date), if any.
     * There could be no next occurring day if the activity is non-recurring, or if the activity has an end date
     *   and there are no occurrences after curDate and before or on the end date.
     * @param curDate The starting date to search for the next occurrence. The activity does not need to occur
     *    on this date, and this date does not need to be between the start and end date of the activity.
     * @return An Optional containing the next occurrence of the activity if it exists,
     *   or Optional.empty if there is none.
     */
    public Optional<LocalDate> getNextOccurringDay(LocalDate curDate) {
        if (curDate.isBefore(this.startDate)) {
            curDate = startDate;
            if (days.contains(curDate.getDayOfWeek()))
                return Optional.of(curDate);
        }

        if (weekInterval == null)
            return Optional.empty();

        curDate = DateUtils.getNextWeekDay(curDate, days);

        int whichWeeksCurDate = (curDate.getDayOfYear() / 7) % weekInterval;
        int whichWeeksStartDate = (curDate.getDayOfYear() / 7) % weekInterval;
        int weekDiff = whichWeeksStartDate - whichWeeksCurDate;
        curDate = curDate.plusWeeks(Math.abs(weekDiff));

        if (endDate != null && curDate.isAfter(endDate))
            return Optional.empty();
        else
            return Optional.of(curDate);
    }

    public boolean conflictsWith(Activity other) {
        if (time.start.isAfter(other.time.end) || time.end.isBefore(other.time.start))
            return false;

        // Use retainAll to get set intersection
        var commonDays = EnumSet.copyOf(days);
        commonDays.retainAll(other.days);
        if(commonDays.isEmpty())
            return false;

        // Start comparing at the latest startDate
        var start = startDate;
        if (startDate.isBefore(other.startDate))
            start = other.startDate;

        var thisDate = start;
        if (!this.occursOnDay(start)) {
            var next = getNextOccurringDay(thisDate);
            if (!next.isPresent())
                return false;
            else
                thisDate = next.get();
        }

        var otherDate = start;
        if (!other.occursOnDay(start)) {
            var next = other.getNextOccurringDay(otherDate);
            if (!next.isPresent())
                return false;
            else
                otherDate = next.get();
        }

        int weeksToCheck = (weekInterval == null || other.weekInterval == null) ? 1 : MathUtils.LCM(weekInterval, other.weekInterval);

        // Keep going until null (because there is no next occurring day)
        // or a conflict is impossible because an LCM has passed
        while (thisDate != null && otherDate != null
            && ChronoUnit.WEEKS.between(start, thisDate) < weeksToCheck)
        {
            if (thisDate.equals(otherDate))
                return true;
            else if (thisDate.isBefore(otherDate))
                thisDate = getNextOccurringDay(thisDate).orElse(null);
            else
                otherDate = other.getNextOccurringDay(otherDate).orElse(null);
        }
        return false;
    }

    @Override
    public boolean occursOnDay(LocalDate day) {
        if (endDate != null && day.isAfter(endDate))
            return false;

        var curDate = startDate;
        while (curDate != null && curDate.isBefore(day)) {
            curDate = getNextOccurringDay(curDate).orElse(null);
        }
        return curDate != null && curDate.equals(day);
    }
    
    public Set<DayOfWeek> getDaysOfWeek() {
    	return days;
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
        return Objects.equals(time, activity.time) &&
                Objects.equals(days, activity.days) &&
                Objects.equals(startDate, activity.startDate) &&
                Objects.equals(endDate, activity.endDate) &&
                Objects.equals(weekInterval, activity.weekInterval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, days, startDate, endDate, weekInterval);
    }

    public TimeInterval getTime() {
        return time;
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Optional<LocalDate> getEndDate() {
        return Optional.ofNullable(endDate);
    }

    public Optional<Integer> getWeekInterval() {
        return Optional.ofNullable(weekInterval);
    }
}
