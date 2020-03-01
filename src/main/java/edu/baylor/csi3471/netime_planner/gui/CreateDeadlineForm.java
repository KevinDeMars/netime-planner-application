/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateDeadlineForm
 * Class Description: This class represents a form for creating a deadline.
 */


package edu.baylor.csi3471.netime_planner.gui;

import java.awt.Component;

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
		
		this.components = new Component[] {titleField, categoryBox, locationField, scrollPane, endDateField, endTimeField};
		
		this.createGUI();
	}

	@Override
	protected Deadline createValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
