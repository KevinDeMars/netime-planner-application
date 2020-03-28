/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateActivityForm
 * Class Description: This class represents a form for creating an activity.
 */



package edu.baylor.csi3471.netime_planner.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import edu.baylor.csi3471.netime_planner.models.Activity;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;

public class CreateActivityForm extends CreateEventForm<Activity>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] weekDayNames = {"Sun","Mon","Tues","Wed","Thur","Fri","Sat"};

	protected static String[] labelNames = {"Title","","","","Category","Location","Description","Start Date*",
			"End Date*","Start Time (hh:mm) PM/AM*", "End Time (hh:mm) PM/AM*", "Weekdays*",};
	
	protected JTextField startTimeField = new JTextField();
	
	protected JDatePickerImpl startDatePicker;
	{
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		startDatePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	}

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
	
	protected JCheckBox recurringBox = new JCheckBox("Recurring");
	{
		recurringBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean visible;
				if (recurringBox.isSelected()) {
					visible = true;
					labels[7].setText("Start Date*");
				}
				else {
					visible = false;
					labels[7].setText("Date*");
				}
				weekIntervalComboBox.setVisible(visible);
				weekIntervalPanel.setVisible(visible);
				if (visible) {
					weekIntervalComboBox.actionPerformed(null);
				}
				checkBoxPanel.setVisible(visible);
				endDatePicker.setVisible(visible);
				labels[2].setVisible(visible);
				labels[3].setVisible(visible);
				labels[8].setVisible(visible);
				labels[11].setVisible(visible);
			}
			
		});
	}
	
	protected JFormattedTextField weekIntervalField = new JFormattedTextField(NumberFormat.getIntegerInstance());
	
	protected JPanel weekIntervalPanel = new JPanel(new FlowLayout());
	{
		weekIntervalPanel.add(new JLabel("Every"));
		weekIntervalField.setColumns(2);
		weekIntervalPanel.add(weekIntervalField);
		weekIntervalPanel.add(new JLabel("weeks."));
	}
	
	protected JComboBox<String> weekIntervalComboBox = new JComboBox<>();
	{
		weekIntervalComboBox.addItem("Every week");
		weekIntervalComboBox.addItem("Every \"x\" weeks");
		
		weekIntervalComboBox.addActionListener(e -> {
			if (weekIntervalComboBox.getSelectedIndex() == -1) {
				weekIntervalComboBox.setSelectedIndex(0);
			}

			if (weekIntervalComboBox.getSelectedIndex() == 0) {
				weekIntervalPanel.setVisible(false);
			}
			else {
				weekIntervalPanel.setVisible(true);
			}

		});
	}
	
	public CreateActivityForm() {
		super(labelNames);
		this.frame.setTitle("Create Activity");
		titleField.setColumns(10);
		categoryBox.addItem("Miscellaneous");
		
		this.components = new Component[] {titleField, recurringBox, weekIntervalComboBox, weekIntervalPanel, categoryBox, locationField, scrollPane, startDatePicker, endDatePicker, startTimeField, endTimeField, checkBoxPanel};
		
		this.createGUI();
		recurringBox.getActionListeners()[0].actionPerformed(null);

		enableSubmitButtonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitButton.setEnabled(false);
				if (startDatePicker.getJFormattedTextField().getText().contentEquals("")) {
					return;
				}
				try{
					LocalTime.parse(startTimeField.getText().trim(), timeFormatter);
				}
				catch(DateTimeParseException e1) {
					return;
				}
				try{
					LocalTime.parse(endTimeField.getText().trim(), timeFormatter);
				}
				catch(DateTimeParseException e2) {
					return;
				}
				if (weekIntervalComboBox.getSelectedIndex() == 1) {
					if (weekIntervalField.getText().contentEquals("")) {
						return;
					}

					if (weekIntervalComboBox.getSelectedIndex() == 1) {
						if (weekIntervalField.getText().contentEquals("")) {
							return;
						}
						if (((Long)weekIntervalField.getValue()) < 1) {
							return;
						}
					}
					boolean oneDayIsSelected = false;
					for (JCheckBox box : weekDayBoxes) {
						if (box.isSelected()) {
							oneDayIsSelected = true;
							break;
						}
					}
					if (!oneDayIsSelected) {
	
						if (((Integer)weekIntervalField.getValue()) < 1) {
							return;
						}
					}
				}
				submitButton.setEnabled(true);
			}
		};
		startDatePicker.addActionListener(enableSubmitButtonListener);
		endDatePicker.addActionListener(enableSubmitButtonListener);
		startTimeField.addActionListener(enableSubmitButtonListener);
		endTimeField.addActionListener(enableSubmitButtonListener);
		weekIntervalField.addActionListener(enableSubmitButtonListener);
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				enableSubmitButtonListener.actionPerformed(null);
			}
		});
		for (JCheckBox box : weekDayBoxes) {
			box.addActionListener(enableSubmitButtonListener);
		}
		recurringBox.addActionListener(enableSubmitButtonListener);
	}
	
	
	@Override
	protected Activity createValue() {
		Activity output;
		String name = titleField.getText();
		String description = descriptionArea.getText();
		String location = locationField.getText();

		LocalTime t1, t2;
		t1 = LocalTime.parse(startTimeField.getText().trim(), timeFormatter);
		t2 = LocalTime.parse(endTimeField.getText().trim(), timeFormatter);
		
		TimeInterval time = new TimeInterval(t1, t2);
		DateModel<?> startDateModel = startDatePicker.getJDateInstantPanel().getModel();
		int year = startDateModel.getYear();
		int month = startDateModel.getMonth() + 1;//this is creating a result earlier by one month for some reason
		int dayOfMonth = startDateModel.getDay();
		LocalDate startDate = LocalDate.of(year, month, dayOfMonth);
		
		if (recurringBox.isSelected()) {
			DateModel<?> endDateModel = endDatePicker.getJDateInstantPanel().getModel();
			year = endDateModel.getYear();
			month = endDateModel.getMonth() + 1;//same thing here
			dayOfMonth = endDateModel.getDay();
			LocalDate endDate = LocalDate.of(year, month, dayOfMonth);
			Set<DayOfWeek> days = new HashSet<>();
			for (int i = 0; i < weekDayBoxes.length; i++) {
				if (weekDayBoxes[i].isSelected()) {//was using 0 instead of i
					System.out.println(DayOfWeek.of((i)%7));
					days.add(DayOfWeek.of((i)%7+1));//dont need to subtract 1 here
				}
			}
			int weekInterval = 1;
			if (weekIntervalComboBox.getSelectedIndex() == 1) {
				weekInterval = (int) (long) weekIntervalField.getValue();//incorrect casting

			}
			
			output = new Activity(name, description, location, time, days, startDate, endDate, weekInterval);
			
		}
		else {
			output = new Activity(name, description, location, startDate, time);
		}
		return output;
	}
	
	

}
