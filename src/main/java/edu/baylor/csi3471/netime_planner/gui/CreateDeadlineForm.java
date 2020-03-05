/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateDeadlineForm
 * Class Description: This class represents a form for creating a deadline.
 */


package edu.baylor.csi3471.netime_planner.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.jdatepicker.DateModel;

import edu.baylor.csi3471.netime_planner.models.Deadline;

public class CreateDeadlineForm extends CreateEventForm<Deadline> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CreateDeadlineForm() {
		super(labelNames);
		this.frame.setTitle("Create Deadline");
		titleField.setColumns(10);
		categoryBox.addItem("Miscellaneous");
		
		this.components = new Component[] {titleField, categoryBox, locationField, scrollPane, endDatePicker, endTimeField};
		
		this.createGUI();
		
		enableSubmitButtonListener = e -> {
			submitButton.setEnabled(false);
			if (endDatePicker.getJFormattedTextField().getText().contentEquals("")) {
				return;
			}
			if (endTimeField.getText().contentEquals("")) {
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
		LocalTime t = (LocalTime) endTimeField.getValue();
		DateModel<?> endDateModel = endDatePicker.getJDateInstantPanel().getModel();
		int year = endDateModel.getYear();
		int month = endDateModel.getMonth();
		int dayOfMonth = endDateModel.getDay();
		LocalDateTime endDateTime = LocalDateTime.of(year, month, dayOfMonth, t.getHour(), t.getMinute());
		LocalDateTime startDateTime = LocalDateTime.now();
		
		return new Deadline(name, description, location, endDateTime, startDateTime, null);
	}

}
