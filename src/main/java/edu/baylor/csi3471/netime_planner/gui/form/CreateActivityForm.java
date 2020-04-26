
/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: CreateActivityForm
 * Class Description: This class represents a form for creating an activity.
 */



package edu.baylor.csi3471.netime_planner.gui.form;

import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class CreateActivityForm extends CreateEventForm<Activity>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] weekDayNames = {"Sun","Mon","Tues","Wed","Thur","Fri","Sat"};

	static final String[] labelNames = {"Title","","","","Category","Location","Description","Start Date*",
			"End Date*","Start Time (hh:mm) PM/AM*", "End Time (hh:mm) PM/AM*", "Weekdays*", "Auto-Append"};
	
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

	private JCheckBox autoAppendCheckbox = new JCheckBox();
	{
		autoAppendCheckbox.addActionListener(e -> {
			boolean usingAutoAppend = autoAppendCheckbox.isSelected();
			if (usingAutoAppend) {
				recurringBox.setSelected(false);
				recurringBox.setEnabled(false);
				weekIntervalComboBox.setVisible(false);
				weekIntervalPanel.setVisible(false);
				checkBoxPanel.setVisible(false);
				startDatePicker.setVisible(false);
				startTimeField.setVisible(false);
				endTimeField.setVisible(false);
				endDatePicker.setVisible(false);
				labels[7].setVisible(false); // Date
				labels[8].setVisible(false); // End Date
				labels[9].setVisible(false); // Start time
				labels[10].setVisible(false); // End time
				labels[11].setVisible(false); // Weekdays
			}
			else {
				recurringBox.setEnabled(true);
				boolean recurring = recurringBox.isSelected();
				weekIntervalComboBox.setVisible(recurring);
				weekIntervalPanel.setVisible(recurring);
				checkBoxPanel.setVisible(recurring);
				startDatePicker.setVisible(true);
				endDatePicker.setVisible(recurring);
				startTimeField.setVisible(true);
				endTimeField.setVisible(true);
				labels[7].setVisible(true); // Date
				labels[8].setVisible(recurring); // End Date
				labels[9].setVisible(true); // Start time
				labels[10].setVisible(true); // End time
				labels[11].setVisible(recurring); // Weekdays
			}

		});
	}
	
	public CreateActivityForm() {
		super(labelNames);
		this.frame.setTitle("Create Activity");
		titleField.setColumns(10);
		categoryBox.addItem("Miscellaneous");
		
		this.components = new Component[] {titleField, recurringBox, weekIntervalComboBox, weekIntervalPanel, categoryBox, locationField, scrollPane, startDatePicker, endDatePicker, startTimeField, endTimeField, checkBoxPanel, autoAppendCheckbox};
		
		this.createGUI();
		recurringBox.getActionListeners()[0].actionPerformed(null);

		enableSubmitButtonListener = e -> {
			if (autoAppendCheckbox.isSelected()) {
				submitButton.setEnabled(true);
				return;
			}

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

			if (recurringBox.isSelected()) {
				if (weekIntervalComboBox.getSelectedIndex() == 1) {
					if (weekIntervalField.getText().contentEquals("")) {
						return;
					}

					try{
						Integer weekIntervalFieldValue = Integer.parseInt(weekIntervalField.getText());
						if (weekIntervalFieldValue < 1) {
							return;
						}
					}
					catch(NumberFormatException e1) {
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
					return;
				}
			}

			submitButton.setEnabled(true);
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
		autoAppendCheckbox.addActionListener(enableSubmitButtonListener);
	}
	
	
	@Override
	protected Activity createValue() {
		Activity output;
		String name = titleField.getText();
		String description = descriptionArea.getText();
		String location = locationField.getText();

		if (autoAppendCheckbox.isSelected()) {
			LocalDateTime startTime = LocalDateTime.now(); //TODO: = findFreeTime(...)
			var endTime = startTime.plusHours(1);
			return new Activity(name, description, location, startTime.toLocalDate(),
					new TimeInterval(startTime.toLocalTime(), endTime.toLocalTime()));
		}

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
					int dayOfWeekValue = i;
					if (dayOfWeekValue == 0)
						dayOfWeekValue = 7;
					days.add(DayOfWeek.of(dayOfWeekValue));
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
	
	
	public void prefillForm(Event event) {
		
		Activity activity = (Activity) event;
		
		this.titleField.setText(activity.getName());
		this.descriptionArea.setText(activity.getDescription().orElse(""));
		this.locationField.setText(activity.getLocation().orElse(""));
		DateModel<?> startDateModel = startDatePicker.getJDateInstantPanel().getModel();
		startDateModel.setDate(activity.getStartDate().getYear(), activity.getStartDate().getMonthValue() - 1, activity.getStartDate().getDayOfMonth());
		startDateModel.setSelected(true);
		
		if (activity.getEndDate().isPresent()) {
			LocalDate endDate = activity.getEndDate().orElseThrow();
			DateModel<?> endDateModel = endDatePicker.getJDateInstantPanel().getModel();
			endDateModel.setDate(endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());
			endDateModel.setSelected(true);
		}
		
		this.startTimeField.setText(timeFormatter.format(activity.getTime().getStart()));
		this.endTimeField.setText(timeFormatter.format(activity.getTime().getEnd()));
		
		if (activity.getWeekInterval().isPresent()) {
			this.recurringBox.doClick();
			
			if (activity.getWeekInterval().get() >= 2) {
				this.weekIntervalComboBox.setSelectedIndex(1);
				this.weekIntervalField.setText(Integer.toString(activity.getWeekInterval().get()));
			}
			else {
				this.weekIntervalComboBox.setSelectedIndex(0);
			}
			
			for (DayOfWeek day : activity.getDaysOfWeek()) {
				this.weekDayBoxes[day.getValue()%7].doClick();
			}
			
		}

	}
	

}
