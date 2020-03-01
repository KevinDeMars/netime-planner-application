/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateEventForm
 * Class Description: This class represents a form for creating an event.
 */


package edu.baylor.csi3471.netime_planner.gui;


import java.text.SimpleDateFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class CreateEventForm<T> extends Form<T>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static String[] labelNames = {"Title","Category","Location","Description","End Date (MM/dd/yyyy)", "End Time (hh:mm) PM/AM"};
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	protected static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
	
	protected JTextField titleField = new JTextField();
	protected JComboBox<String> categoryBox = new JComboBox<String>();
	protected JTextField locationField = new JTextField();
	protected JTextArea descriptionArea = new JTextArea(2, 30);
	{
		descriptionArea.setLineWrap(true);
		descriptionArea.setAutoscrolls(true);
	}
	protected JScrollPane scrollPane = new JScrollPane(descriptionArea);
	{
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	protected JFormattedTextField endDateField = new JFormattedTextField(dateFormat);
	protected JFormattedTextField endTimeField = new JFormattedTextField(timeFormat);
	
	public CreateEventForm(String[] labelNames) {
		super(labelNames);
	}

}
