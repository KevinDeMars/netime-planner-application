/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateActivityForm
 * Class Description: This class represents a form for creating an activity.
 */



package edu.baylor.csi3471.netime_planner.gui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import edu.baylor.csi3471.netime_planner.models.Activity;

public class CreateActivityForm extends CreateEventForm<Activity>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] weekDayNames = {"Sun","Mon","Tues","Wed","Thur","Fri","Sat"};

	protected static String[] labelNames = {"Title","Category","Location","Description","Start Date (MM/dd/yyyy)",
			"End Date (MM/dd/yyyy)","Start Time (hh:mm) PM/AM", "End Time (hh:mm) PM/AM", "Weekdays"};
	
	protected JFormattedTextField startDateField = new JFormattedTextField(dateFormat);
	protected JFormattedTextField startTimeField = new JFormattedTextField(timeFormat);

	protected JCheckBox[] weekDayBoxes = new JCheckBox[weekDayNames.length];
	{
		for (int i = 0; i < weekDayNames.length; i++) {
			weekDayBoxes[i] = new JCheckBox(weekDayNames[i]);
		}
	}
	
	protected JPanel checkBoxPanel = new JPanel(new GridLayout(1, 7));
	{
		for (int i = 0; i < weekDayNames.length; i++) {
			checkBoxPanel.add(weekDayBoxes[i]);
		}
	}
	
	public CreateActivityForm() {
		super(labelNames);
		this.frame.setTitle("Create Activity");
		titleField.setColumns(10);
		categoryBox.addItem("Miscellaneous");
		
		this.components = new Component[] {titleField, categoryBox, locationField, scrollPane, startDateField, endDateField, startTimeField, endTimeField, checkBoxPanel};
		
		this.createGUI();
	}
	
	@Override
	protected Activity createValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
