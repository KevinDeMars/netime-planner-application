package edu.baylor.csi3471;

import edu.baylor.csi3471.netime_planner.models.DateTimeInterval;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;
import edu.baylor.csi3471.netime_planner.services.LoginVerificationService;
import edu.baylor.csi3471.netime_planner.services.ScheduleService;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;
import edu.baylor.csi3471.netime_planner.util.DateUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleTest {
    private static final Logger LOGGER = Logger.getLogger(ScheduleTest.class.getName());
    private static ScheduleService scheduleSvc;

    private static final LocalTime defaultTime = LocalTime.of(12, 0);
    private static final LocalTime defaultTime2 = LocalTime.of(12, 1);
    
    private static final TimeInterval defaultTimeInterval = new TimeInterval(defaultTime,defaultTime2);
	//private static final TimeInterval defaultTimeInterval2 = new TimeInterval(defaultTime.plusHours(11),defaultTime2.plusHours(11));
	private static final LocalDate defaultStartDate = LocalDate.of(2020, 1, 1);
	private static final LocalDate defaultEndDate = LocalDate.of(2020, 12, 31);
	
	private static final LocalDateTime defaultStartDateTime = LocalDateTime.of(defaultStartDate, defaultTime);
	private static final LocalDateTime defaultEndDateTime = LocalDateTime.of(defaultEndDate, defaultTime);

	@BeforeAll
    static void configure() {
	    ServiceConfiguration.configureServices();
        scheduleSvc = ServiceManager.getInstance().getService(ScheduleService.class);
        ServiceManager.getInstance().getService(LoginVerificationService.class).register("test", new char[]{'1', '2', '3'});
    }

    @AfterAll
    static void cleanUpTestUser() {
	    var dao = ServiceManager.getInstance().getService(UserDAO.class);
	    dao.delete(dao.findByUsername("test").get());
    }

    @Test
    public void testMakeTodoList() {
        var schedule = scheduleSvc.getSchedule("test");
        scheduleSvc.addEvent(schedule, new Deadline(LocalDateTime.of(2020, 3, 23, 11, 30)));
        scheduleSvc.addEvent(schedule, new Deadline(LocalDateTime.of(2020, 3, 23, 22, 0)));
        scheduleSvc.addEvent(schedule, new Deadline(LocalDateTime.of(2020, 3, 24, 0, 0)));
        scheduleSvc.addEvent(schedule, new Deadline(LocalDateTime.of(2020, 3, 25, 9, 15)));
        scheduleSvc.addEvent(schedule, new Deadline(LocalDateTime.of(2020, 3, 22, 22, 0)));
        scheduleSvc.addEvent(schedule, new Deadline(LocalDateTime.of(2020, 3, 26, 9, 15)));
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

    	var schedule = scheduleSvc.getSchedule("test");
    	scheduleSvc.addEvent(schedule, deadline);
    	
    	Assertions.assertTrue(scheduleSvc.getSchedule("test").getEvents().contains(deadline));
    	Assertions.assertTrue(scheduleSvc.getSchedule("test").getEvents().contains(deadlineCopy));
    }
    
    @Test
    public void testAddEvent2() {
    	Activity recurring = new Activity("a","b","c",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
    	Activity recurringCopy = new Activity("a","b","c",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);

        var schedule = scheduleSvc.getSchedule("test");
    	scheduleSvc.addEvent(schedule, recurring);

        Assertions.assertTrue(scheduleSvc.getSchedule("test").getEvents().contains(recurring));
        Assertions.assertTrue(scheduleSvc.getSchedule("test").getEvents().contains(recurringCopy));
    }
    
    @Test
    public void testAddEvent3() {
    	Activity nonRecurring = new Activity("a","b","c",defaultStartDate, defaultTimeInterval);
    	Activity nonRecurringCopy = new Activity("a","b","c",defaultStartDate, defaultTimeInterval);

        var schedule = scheduleSvc.getSchedule("test");
        scheduleSvc.addEvent(schedule, nonRecurring);

        Assertions.assertTrue(scheduleSvc.getSchedule("test").getEvents().contains(nonRecurring));
        Assertions.assertTrue(scheduleSvc.getSchedule("test").getEvents().contains(nonRecurringCopy));
    }
    
    
    @Test
    public void testRemoveEvent1() {
    	Deadline deadline = new Deadline("remove", "", "", defaultEndDateTime, defaultStartDateTime, null);
    	Deadline deadlineCopy = new Deadline("remove", "", "", defaultEndDateTime, defaultStartDateTime, null);

        var schedule = scheduleSvc.getSchedule("test");
        scheduleSvc.removeEvent(schedule, deadline);

        Assertions.assertFalse(scheduleSvc.getSchedule("test").getEvents().contains(deadline));
        Assertions.assertFalse(scheduleSvc.getSchedule("test").getEvents().contains(deadlineCopy));
    }
    
    @Test
    public void testRemoveEvent2() {
    	Activity recurring = new Activity("remove","","",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
    	Activity recurringCopy = new Activity("remove","","",defaultTimeInterval,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);

        var schedule = scheduleSvc.getSchedule("test");
        scheduleSvc.removeEvent(schedule, recurring);

        Assertions.assertFalse(scheduleSvc.getSchedule("test").getEvents().contains(recurring));
        Assertions.assertFalse(scheduleSvc.getSchedule("test").getEvents().contains(recurringCopy));
    	
    }
    
    @Test
    public void testRemoveEvent3() {
    	Activity nonRecurring = new Activity("remove","","",defaultStartDate, defaultTimeInterval);
    	Activity nonRecurringCopy = new Activity("remove","","",defaultStartDate, defaultTimeInterval);

        var schedule = scheduleSvc.getSchedule("test");
        scheduleSvc.removeEvent(schedule, nonRecurring);

        Assertions.assertFalse(scheduleSvc.getSchedule("test").getEvents().contains(nonRecurring));
        Assertions.assertFalse(scheduleSvc.getSchedule("test").getEvents().contains(nonRecurringCopy));
    }
    
    
    
}
