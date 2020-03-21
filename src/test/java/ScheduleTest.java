import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.models.DateTimeInterval;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleTest {
    private Controller controller = new MockController();

    @Test
    public void testMakeTodoList()
    {
        var schedule = controller.getSchedule();
        schedule.getEvents().forEach(System.out::println);

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
