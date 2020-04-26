import edu.baylor.csi3471.netime_planner.models.*;
import edu.baylor.csi3471.netime_planner.util.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleTest {
    private static Logger LOGGER = Logger.getLogger(ScheduleTest.class.getName());
    private Controller controller = new MockController();
    
    private static final LocalTime defaultTime = LocalTime.of(12, 0);
    private static final LocalTime defaultTime2 = LocalTime.of(12, 1);
    
    private static final TimeInterval defaultTimeInterval = new TimeInterval(defaultTime,defaultTime2);

	private static final LocalDate defaultStartDate = LocalDate.of(2020, 1, 1);
	private static final LocalDate defaultEndDate = LocalDate.of(2020, 12, 31);
	
	private static final LocalDateTime defaultStartDateTime = LocalDateTime.of(defaultStartDate, defaultTime);
	private static final LocalDateTime defaultEndDateTime = LocalDateTime.of(defaultEndDate, defaultTime);
	
	
    @Test
    public void testMakeTodoList() {
    	
        var schedule = controller.getSchedule();
        schedule.getEvents().forEach(e -> LOGGER.info(e.toString()));

        var interval1 = new DateTimeInterval(
                LocalDateTime.of(2020, 3, 23, 0, 0),
                LocalDateTime.of(2020, 3, 23, 23, 59)
        );
        var todo = schedule.makeToDoList(interval1);
        LOGGER.info("\nThings to do on 3/23:");
        todo.forEach(e -> LOGGER.info(e.toString()));
        assertEquals(2, todo.size());

        var interval2 = new DateTimeInterval(
                LocalDateTime.of(2020, 3, 23, 0, 0),
                LocalDateTime.of(2020, 3, 25, 23, 59)
        );
        todo = schedule.makeToDoList(interval2);
        LOGGER.info("\n\nThings to do from 3/23 through 3/25:");
        todo.forEach(e -> LOGGER.info(e.toString()));
        assertEquals(4, todo.size());
    }
    
    @Test
    public void testAddEvent1() {
    	Deadline deadline = new Deadline("a", "b", "c", defaultEndDateTime, defaultStartDateTime, null);
    	Deadline deadlineCopy = new Deadline("a", "b", "c", defaultEndDateTime, defaultStartDateTime, null);
    	
    	controller.addEvent(deadline);
    	
    	Assertions.assertTrue(controller.getEvents().contains(deadline));
    	Assertions.assertTrue(controller.getEvents().contains(deadlineCopy));
    }
    
    @Test
    public void testAddEvent2() {
    	Activity recurring = new Activity("a","b","c",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
    	Activity recurringCopy = new Activity("a","b","c",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
    	
    	controller.addEvent(recurring);
    	
    	Assertions.assertTrue(controller.getEvents().contains(recurring));
    	Assertions.assertTrue(controller.getEvents().contains(recurringCopy));
    }
    
    @Test
    public void testAddEvent3() {
    	Activity nonRecurring = new Activity("a","b","c",defaultStartDate, defaultTimeInterval);
    	Activity nonRecurringCopy = new Activity("a","b","c",defaultStartDate, defaultTimeInterval);
    	
    	controller.addEvent(nonRecurring);
    	
    	Assertions.assertTrue(controller.getEvents().contains(nonRecurring));
    	Assertions.assertTrue(controller.getEvents().contains(nonRecurringCopy));
    }
    
    
    @Test
    public void testRemoveEvent1() {
    	Deadline deadline = new Deadline("remove", "", "", defaultEndDateTime, defaultStartDateTime, null);
    	Deadline deadlineCopy = new Deadline("remove", "", "", defaultEndDateTime, defaultStartDateTime, null);
    	
    	controller.addEvent(deadline);
    	controller.removeEvent(deadline);
    	
    	Assertions.assertFalse(controller.getEvents().contains(deadline));
    	Assertions.assertFalse(controller.getEvents().contains(deadlineCopy));
    }
    
    @Test
    public void testRemoveEvent2() {
    	Activity recurring = new Activity("remove","","",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
    	Activity recurringCopy = new Activity("remove","","",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
    	
    	controller.addEvent(recurring);
    	controller.removeEvent(recurring);
    	
    	Assertions.assertFalse(controller.getEvents().contains(recurring));
    	Assertions.assertFalse(controller.getEvents().contains(recurringCopy));
    	
    }
    
    @Test
    public void testRemoveEvent3() {
    	Activity nonRecurring = new Activity("remove","","",defaultStartDate, defaultTimeInterval);
    	Activity nonRecurringCopy = new Activity("remove","","",defaultStartDate, defaultTimeInterval);
    	
    	controller.addEvent(nonRecurring);
    	controller.removeEvent(nonRecurring);
    	
    	Assertions.assertFalse(controller.getEvents().contains(nonRecurring));
    	Assertions.assertFalse(controller.getEvents().contains(nonRecurringCopy));
    }
    
    
    
}
