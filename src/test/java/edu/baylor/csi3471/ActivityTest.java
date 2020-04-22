package edu.baylor.csi3471;

import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest extends Activity {
	private static final int MAX_WEEKS = 52;

	private static final TimeInterval defaultTime = new TimeInterval(LocalTime.of(12, 0),LocalTime.of(12, 1));
	private static final TimeInterval defaultTime2 = new TimeInterval(LocalTime.of(23, 0),LocalTime.of(23, 1));
	private static final LocalDate defaultStartDate = LocalDate.of(2020, 1, 1);
	private static final LocalDate defaultEndDate = LocalDate.of(2020, 12, 31);
	
	private static Set<DayOfWeek> weekDaySet(DayOfWeek...days){
		return new HashSet<>(Arrays.asList(days));
	}
	
	private static final Activity recurring1 = new Activity("","","",defaultTime,weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
	private static final Activity recurring2 = new Activity("","","",defaultTime,weekDaySet(DayOfWeek.TUESDAY),defaultStartDate,defaultEndDate,1);
	
	private static final Activity recurring3 = new Activity("","","",defaultTime,weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,2);
	private static final Activity recurring4 = new Activity("","","",defaultTime,weekDaySet(DayOfWeek.MONDAY),defaultStartDate.plusWeeks(1),defaultEndDate,2);
	
	private static final Activity recurring5 = new Activity("","","",defaultTime2,weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,1);
	
	private static final Activity nonRecurring1 = new Activity("","","",defaultStartDate, defaultTime);
	private static final Activity nonRecurring2 = new Activity("","","",defaultStartDate.plusDays(1), defaultTime);
	private static final Activity nonRecurring3 = new Activity("","","",defaultStartDate.plusWeeks(1), defaultTime);
	private static final Activity nonRecurring4 = new Activity("","","",defaultStartDate, defaultTime2);

	@Test
	public void testGetNextWeekday() {
		runTestGetNextWeekday(recurring1, defaultStartDate, LocalDate.of(2020, 1, 6)); // Wed. 1/1 -> Mon. 1/6
		runTestGetNextWeekday(recurring1, LocalDate.of(2020, 1, 6), LocalDate.of(2020, 1, 13)); // Mon. 1/6 -> Mon. 1/13
		runTestGetNextWeekday(recurring1, LocalDate.of(2020, 1, 5), LocalDate.of(2020, 1, 6)); // Sun. 1/5 -> Mon. 1/6
		runTestGetNextWeekday(recurring1, LocalDate.of(2020, 1, 7), LocalDate.of(2020, 1, 13)); // Tues. 1/7 -> Mon. 1/13

		runTestGetNextWeekday(recurring3, LocalDate.of(2020, 1, 6), LocalDate.of(2020, 1, 20)); // Mon. 1/6 -> Mon. 1/20
		runTestGetNextWeekday(recurring4, LocalDate.of(2020, 1, 13), LocalDate.of(2020, 1, 27)); // Mon. 1/13 -> Mon. 1/27
		runTestGetNextWeekday(recurring4, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 13)); // Wed. 1/1 -> Mon. 1/13

		runTestGetNextWeekday(recurring3, LocalDate.of(2020, 1, 13), LocalDate.of(2020, 1, 20)); // Mon. 1/13 -> Mon. 1/20
		runTestGetNextWeekday(recurring3, LocalDate.of(2020, 1, 14), LocalDate.of(2020, 1, 20)); // Tues. 1/14 -> Mon. 1/20

		// Every Wednesday, starting on Jan 1
		var activity = new Activity("", "", "", defaultTime, EnumSet.of(DayOfWeek.WEDNESDAY), LocalDate.of(2020, 1, 1), null, 1);

		runTestGetNextWeekday(activity, LocalDate.of(2019, 12, 25), LocalDate.of(2020, 1, 1));

		runTestGetNextWeekday(nonRecurring1, LocalDate.of(2020,1,1), null);
		runTestGetNextWeekday(nonRecurring2, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2));
	}

	private void runTestGetNextWeekday(Activity a, LocalDate curDay, LocalDate expectedNext) {
		assertEquals(expectedNext, a.getNextOccurringDay(curDay).orElse(null));
	}

	
	//The below tests are for the confictsWith method.
	
	@Test
	public void testConflictsWith1() {
		assertTrue(recurring1.conflictsWith(recurring1));
	}
	
	@Test
	public void testConflictsWith2() {
		assertFalse(recurring1.conflictsWith(recurring2));
	}
	
	@Test
	public void testConflictsWith3() {
		assertTrue(recurring1.conflictsWith(recurring1));
	}
	
	private void assertConflicts(Activity a1, Activity a2) {
		assertTrue(a1.conflictsWith(a2));
		assertTrue(a2.conflictsWith(a1));
	}
	
	//This repeatedly asserts that the activities do not conflict.
	private void assertDoesntConflict(Activity a1, Activity a2) {
		assertFalse(a2.conflictsWith(a1));
		assertFalse(a1.conflictsWith(a2));
	}
	
	@Test
	public void testConflictsWith4() {
		assertDoesntConflict(recurring1, recurring2);
	}
	
	@Test
	public void testConflictsWith5() {
		assertDoesntConflict(recurring3, recurring4);
	}
	
	@Test
	public void testConflictsWith6() {
		assertConflicts(recurring1, recurring3);
	}
	
	@Test
	public void testConflictsWith7() {
		// Doesn't conflict: Wed. 1/1 for nonrecurring and Mondays for recurring
		assertDoesntConflict(recurring1, nonRecurring1);
	}
	
	@Test
	public void testConflictsWith8() {
		assertDoesntConflict(recurring2, nonRecurring1);
	}
	
	@Test
	public void testConflictsWith9() {
		assertConflicts(nonRecurring1, nonRecurring1);
	}
	
	@Test
	public void testConflictsWith10() {
		assertDoesntConflict(nonRecurring1, nonRecurring2);
	}
	
	@Test
	public void testConflictsWith11() {
		assertDoesntConflict(nonRecurring1, nonRecurring3);
	}
	
	@Test
	public void testConflictsWith12() {
		assertDoesntConflict(nonRecurring1, nonRecurring4);
	}
	
	@Test
	public void testConflictsWith13() {
		assertDoesntConflict(recurring1, nonRecurring4);
	}
	
	@Test
	public void testConflictsWith14() {
		assertDoesntConflict(recurring2, nonRecurring4);
	}
	
	@Test
	public void testConflictsWith15() {
		assertDoesntConflict(recurring1, recurring5);
	}
	
	
	//The below tests are for the occursOnDay method.
	public void repeatedOccursOnDayTest(Activity activity, LocalDate date, int numOfWeeks) {
		for (int i = 0; i < numOfWeeks; i++) {
			assertTrue(activity.occursOnDay(date.plusWeeks(i)));
		}
	}
	
	public void repeatedDoesNotOccurOnDayTest(Activity activity, LocalDate date, int numOfWeeks) {
		for (int i = 0; i < numOfWeeks; i++) {
			assertFalse(activity.occursOnDay(date.plusWeeks(i)));
		}
	}
	
	@Test
	public void testOccursOnDay1() {
		repeatedDoesNotOccurOnDayTest(recurring1, defaultStartDate, MAX_WEEKS);
	}
	
	@Test
	public void testOccursOnDay2() {
		repeatedOccursOnDayTest(recurring1, defaultStartDate.plusDays(5), MAX_WEEKS);
	}
	
	@Test
	public void testOccursOnDay3() {
		repeatedDoesNotOccurOnDayTest(recurring2, defaultStartDate, MAX_WEEKS);
	}
	
	@Test
	public void testOccursOnDay4() {
		repeatedOccursOnDayTest(recurring2, defaultStartDate.plusDays(6), MAX_WEEKS);
	}
	
	@Test
	public void testOccursOnDay5() {
		assertTrue(nonRecurring1.occursOnDay(defaultStartDate));
	}
	
	@Test
	public void testOccursOnDay6() {
		assertFalse(nonRecurring1.occursOnDay(defaultStartDate.plusDays(1)));
	}
	
	@Test
	public void testOccursOnDay7() {
		assertFalse(nonRecurring2.occursOnDay(defaultStartDate));
	}
	
	@Test
	public void testOccursOnDay8() {
		assertTrue(nonRecurring2.occursOnDay(defaultStartDate.plusDays(1)));
	}
}
