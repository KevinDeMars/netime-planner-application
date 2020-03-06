/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateEventForm
 * Class Description: This class represents a form for creating an event.
 */

//Use Doxygen comments that IntelliJ uses
package edu.baylor.csi3471.netime_planner.gui;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public abstract class CreateEventForm<T> extends Form<T>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static String[] labelNames = {"Title","Category","Location","Description","End Date*", "End Time (hh:mm) PM/AM*"};
	
	protected static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
	
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
	protected JDatePickerImpl endDatePicker;
	{
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		endDatePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	}
	
	protected JTextField endTimeField = new JTextField();
	
	protected JPanel chooseStartDatePane = new JPanel(new FlowLayout());
	
	protected ActionListener enableSubmitButtonListener;
	
	protected void performActionOnMouseRelease(JFormattedTextField field, ActionListener aListener, ActionEvent a) {
		field.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			    aListener.actionPerformed(a);
			}
		});
	}
	
	public CreateEventForm(String[] labelNames) {
		super(labelNames);
		this.submitButton.setEnabled(false);
	}

}
