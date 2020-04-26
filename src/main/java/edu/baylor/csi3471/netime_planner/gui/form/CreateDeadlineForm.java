/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateDeadlineForm
 * Class Description: This class represents a form for creating a deadline.
 */


package edu.baylor.csi3471.netime_planner.gui.form;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import org.jdatepicker.DateModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.swing.JTextField;

public class CreateDeadlineForm extends CreateEventForm<Deadline> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String[] labelNames = {"Title","Category","Location","Description","End Date*", "End Time (hh:mm) PM/AM*", "Hours Spent"};
	
	public JTextField hoursSpentField = new JTextField("0");
	
	public CreateDeadlineForm() {
		super(labelNames);
		this.frame.setTitle("Create Deadline");
		titleField.setColumns(10);
		categoryBox.addItem("Miscellaneous");
		
		this.components = new Component[] {titleField, categoryBox, locationField, scrollPane, endDatePicker, endTimeField, hoursSpentField};
		
		this.createGUI();
		

		enableSubmitButtonListener = e -> {
			submitButton.setEnabled(false);
			try{
				LocalTime.parse(endTimeField.getText(), timeFormatter);
			}
			catch(DateTimeParseException e2) {
				return;
			}
			submitButton.setEnabled(true);
		};
		endDatePicker.addActionListener(enableSubmitButtonListener);
		endTimeField.addActionListener(enableSubmitButtonListener);
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				enableSubmitButtonListener.actionPerformed(null);
			}
		});
	}

	@Override
	protected Deadline createValue() {
		String name = titleField.getText();
		String description = descriptionArea.getText();
		String location = locationField.getText();
		LocalTime t = LocalTime.parse(endTimeField.getText().trim(), timeFormatter);
		DateModel<?> endDateModel = endDatePicker.getJDateInstantPanel().getModel();
		int year = endDateModel.getYear();
		int month = endDateModel.getMonth() + 1; //seems to create months one off
		int dayOfMonth = endDateModel.getDay();
		LocalDateTime endDateTime = LocalDateTime.of(year, month, dayOfMonth, t.getHour(), t.getMinute());
		LocalDateTime startDateTime = LocalDateTime.now();
		
		Deadline output = new Deadline(name, description, location, endDateTime, startDateTime, null);
		output.setHoursSpent(Integer.parseInt(hoursSpentField.getText().trim()));
		
		return output;
	}
	
	public void prefillForm(Event event) {
		
		Deadline deadline = (Deadline) event;
		
		this.titleField.setText(deadline.getName());
		this.descriptionArea.setText(deadline.getDescription().orElse(""));
		this.locationField.setText(deadline.getLocation().orElse(""));
		
		DateModel<?> endDateModel = endDatePicker.getJDateInstantPanel().getModel();
		endDateModel.setDate(deadline.getDueDatetime().getYear(), deadline.getDueDatetime().getMonthValue() - 1, deadline.getDueDatetime().getDayOfMonth());
		endDateModel.setSelected(true);
		
		this.endTimeField.setText(timeFormatter.format(deadline.getDueDatetime().toLocalTime()));
		
		this.hoursSpentField.setText(deadline.getHoursSpent() + "");
		
	}

}
