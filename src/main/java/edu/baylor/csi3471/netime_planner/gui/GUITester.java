package edu.baylor.csi3471.netime_planner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import edu.baylor.csi3471.netime_planner.models.Activity;
import edu.baylor.csi3471.netime_planner.models.Deadline;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.util.DateUtils;

public class GUITester {
	
	private static final TimeInterval defaultTime = new TimeInterval(LocalTime.of(12, 0),LocalTime.of(12, 1));
	private static final TimeInterval defaultTime2 = new TimeInterval(LocalTime.of(23, 0),LocalTime.of(23, 1));
	private static final LocalDate defaultStartDate = LocalDate.of(2020, 1, 1);
	private static final LocalDate defaultEndDate = LocalDate.of(2020, 12, 31);
	
	private static final LocalDateTime defaultDateTime = LocalDateTime.of(defaultStartDate, LocalTime.of(12, 0));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deadline deadline = new Deadline("name", "description", "location", defaultDateTime, null, null);

		CreateDeadlineForm d = (CreateDeadlineForm) CreateEventForm.createForm(deadline);
		
//		CreateDeadlineForm d = new CreateDeadlineForm();
//		
//		d.prefillForm(deadline);
		
		d.setVisible(true);
		d.setSubmissionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Submitted");
				
			}
			
		});
		
		Activity activity = new Activity("name","description","location",defaultTime,DateUtils.weekDaySet(DayOfWeek.MONDAY),defaultStartDate,defaultEndDate,3);
		
//		CreateActivityForm a = new CreateActivityForm();
//		a.prefillForm(activity);
		
		CreateActivityForm a = (CreateActivityForm) CreateEventForm.createForm(activity);
		a.setVisible(true);
		a.setSubmissionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Submitted");
			}
			
		});
	}

}
