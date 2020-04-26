package edu.baylor.csi3471.netime_planner.gui.form;

import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.util.Formatters;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class WorktimeForm extends Form<Activity> {
    private static final String[] weekDayNames = {"Sun","Mon","Tues","Wed","Thur","Fri","Sat"};
    static final String[] labelNames = {"Start Date", "End Date","Start Time (hh:mm) PM/AM*", "End Time (hh:mm) PM/AM*", "Weekdays*",};

    protected JTextField startTimeField = new JTextField();
    protected JTextField endTimeField = new JTextField();

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

    public WorktimeForm() {
        super(labelNames);
        this.components = new Component[] {startDatePicker, endDatePicker, startTimeField, endTimeField, checkBoxPanel};
        createGUI();
        submitButton.setEnabled(false);

        ActionListener enableSubmitButtonListener = e -> {
            if (Arrays.stream(weekDayBoxes).anyMatch(AbstractButton::isSelected))
            {
                try {
                    createValue();
                    submitButton.setEnabled(true);
                }
                catch (DateTimeException ignored) {

                }
            }
        };

        startDatePicker.addActionListener(enableSubmitButtonListener);
        endDatePicker.addActionListener(enableSubmitButtonListener);
        startTimeField.addActionListener(enableSubmitButtonListener);
        endTimeField.addActionListener(enableSubmitButtonListener);
        for (JCheckBox box : weekDayBoxes) {
            box.addActionListener(enableSubmitButtonListener);
        }
    }

    @Override
    protected Activity createValue() {
        LocalTime t1, t2;
        t1 = LocalTime.parse(startTimeField.getText().trim(), Formatters.TWELVE_HOURS);
        t2 = LocalTime.parse(endTimeField.getText().trim(), Formatters.TWELVE_HOURS);
        TimeInterval time = new TimeInterval(t1, t2);

        DateModel<?> startDateModel = startDatePicker.getJDateInstantPanel().getModel();
        int year = startDateModel.getYear();
        int month = startDateModel.getMonth() + 1;//this is creating a result earlier by one month for some reason
        int dayOfMonth = startDateModel.getDay();
        LocalDate startDate = LocalDate.of(year, month, dayOfMonth);

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

        return new Activity("", null, null, time, days, startDate, endDate, weekInterval);
    }
}
