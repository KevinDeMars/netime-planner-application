import edu.baylor.csi3471.netime_planner.models.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class MockController extends Controller {
    public MockController() {
        user = new User();
        user.setSchedule(makeTestSchedule());
        user.setId(1337);
        user.setEmail("chaddicusII@example.com");
        user.setName("Chaddicus II");
    }

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
            schedule.addEvent(d);
        for (var a : activities)
            schedule.addEvent(a);

        return schedule;
    }
}
