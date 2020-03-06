import edu.baylor.csi3471.netime_planner.models.*;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {
    static Schedule makeTestSchedule() {
        List<Deadline> deadlines = Arrays.asList(new Deadline("Group Project",
                        "Finish skeleton before meeting",
                        null,
                        LocalDateTime.of(2020, 2, 27, 16, 0),
                        null,
                        null),
                new Deadline("Algorithms Homework",
                        "Weird modulus stuff",
                        null,
                        LocalDateTime.of(2020, 2, 24, 23, 59),
                        null,
                        null),
                new Deadline("Add tests using JUnit",
                        null,
                        null,
                        LocalDateTime.of(2020, 2, 24, 0, 0),
                        null,
                null),
                new Deadline("Deadline 4", null, null,
                        LocalDateTime.of(2020, 3, 1, 0, 0),
                        null, null)
        );
        List<Activity> activities = Arrays.asList(new Activity("Software Engineering", "Cerninator", null,
                new TimeInterval(LocalTime.of(9, 30), LocalTime.of(10, 45)),
                EnumSet.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 3, 31),
                1));

        var schedule = new Schedule();
        for (var d : deadlines)
            schedule.addDeadline(d);
        for (var a : activities)
            schedule.addActivity(a);

        return schedule;
    }

    @Test
    public void testMakeTodoList()
    {
        var schedule = makeTestSchedule();
        schedule.getActivities().forEach(System.out::println);
        schedule.getDeadlines().forEach(System.out::println);

        var interval1 = new DateTimeInterval(
                LocalDateTime.of(2020, 2, 24, 0, 0),
                LocalDateTime.of(2020, 2, 24, 23, 59)
        );
        var todo = schedule.makeToDoList(interval1);
        System.out.println("\nThings to do on 2/24:");
        todo.forEach(System.out::println);
        assertEquals(todo.size(),2);

        var interval2 = new DateTimeInterval(
                LocalDateTime.of(2020, 2, 24, 0, 0),
                LocalDateTime.of(2020, 2, 29, 23, 59)
        );
        todo = schedule.makeToDoList(interval2);
        System.out.println("\n\nThings to do from 2/24 through 2/29:");
        todo.forEach(System.out::println);
        assertEquals(todo.size(), 3);
    }
}
