import edu.baylor.csi3471.netime_planner.models.Activity;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityTest extends Activity {
	
	private static final int MAX_WEEKS = 52;
	
	private static final TimeInterval defaultTime = new TimeInterval(LocalTime.of(12, 0),LocalTime.of(12, 1));
	private static final TimeInterval defaultTime2 = new TimeInterval(LocalTime.of(23, 0),LocalTime.of(23, 1));
	private static final LocalDate defaultStartDate = LocalDate.of(2020, 1, 1);
	private static final LocalDate defaultEndDate = LocalDate.of(2020, 12, 31);
	
	private static Set<DayOfWeek> weekDaySet(DayOfWeek...days){
		HashSet<DayOfWeek> output = new HashSet<DayOfWeek>();
		for (DayOfWeek day : days) {
			output.add(day);
		}
		return output;
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
	
	
	//The below tests are for the confictsWith method.
	
	@Test
	public void testConflictsWith1() {
		Assertions.assertTrue(recurring1.conflictsWith(recurring1, 1));
	}
	
	@Test
	public void testConflictsWith2() {
		Assertions.assertFalse(recurring1.conflictsWith(recurring2, 1));
	}
	
	@Test
	public void testConflictsWith3() {
		Assertions.assertTrue(recurring1.conflictsWith(recurring1, 2));
	}
	
	//This repeatedly asserts that the activities conflict.
	private void repeatedConflicting(Activity a1, Activity a2, int weeks) {
		for (int i = 1; i <= weeks; i++) {
			Assertions.assertTrue(a1.conflictsWith(a2, i));
			Assertions.assertTrue(a2.conflictsWith(a1, i));
		}
	}
	
	//This repeatedly asserts that the activities do not conflict.
	private void repeatedNonConflicting(Activity a1, Activity a2, int weeks) {
		for (int i = 1; i <= weeks; i++) {
			Assertions.assertFalse(a1.conflictsWith(a2, i));
			Assertions.assertFalse(a2.conflictsWith(a1, i));
		}
	}
	
	@Test
	public void testConflictsWith4() {
		repeatedNonConflicting(recurring1, recurring2, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith5() {
		repeatedNonConflicting(recurring3, recurring4, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith6() {
		repeatedConflicting(recurring1, recurring3, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith7() {
		repeatedConflicting(recurring1, nonRecurring1, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith8() {
		repeatedNonConflicting(recurring2, nonRecurring1, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith9() {
		repeatedConflicting(nonRecurring1, nonRecurring1, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith10() {
		repeatedNonConflicting(nonRecurring1, nonRecurring2, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith11() {
		repeatedNonConflicting(nonRecurring1, nonRecurring3, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith12() {
		repeatedNonConflicting(nonRecurring1, nonRecurring4, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith13() {
		repeatedNonConflicting(recurring1, nonRecurring4, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith14() {
		repeatedNonConflicting(recurring2, nonRecurring4, MAX_WEEKS);
	}
	
	@Test
	public void testConflictsWith15() {
		repeatedNonConflicting(recurring1, recurring5, MAX_WEEKS);
	}
	
	
	//The below tests are for the occursOnDay method.
	public void repeatedOccursOnDayTest(Activity activity, LocalDate date, int numOfWeeks) {
		for (int i = 0; i < numOfWeeks; i++) {
			Assertions.assertTrue(activity.occursOnDay(date.plusWeeks(i)));
		}
	}
	
	public void repeatedDoesNotOccurOnDayTest(Activity activity, LocalDate date, int numOfWeeks) {
		for (int i = 0; i < numOfWeeks; i++) {
			Assertions.assertFalse(activity.occursOnDay(date.plusWeeks(i)));
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
		Assertions.assertTrue(nonRecurring1.occursOnDay(defaultStartDate));
	}
	
	@Test
	public void testOccursOnDay6() {
		Assertions.assertFalse(nonRecurring1.occursOnDay(defaultStartDate.plusDays(1)));
	}
	
	@Test
	public void testOccursOnDay7() {
		Assertions.assertFalse(nonRecurring2.occursOnDay(defaultStartDate));
	}
	
	@Test
	public void testOccursOnDay8() {
		Assertions.assertTrue(nonRecurring2.occursOnDay(defaultStartDate.plusDays(1)));
	}
}
