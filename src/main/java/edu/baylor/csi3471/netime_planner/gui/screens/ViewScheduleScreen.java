package edu.baylor.csi3471.netime_planner.gui.screens;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import edu.baylor.csi3471.netime_planner.gui.EventDoubleClickHandler;
import edu.baylor.csi3471.netime_planner.gui.TodoListSidePanel;
import edu.baylor.csi3471.netime_planner.gui.ViewScheduleTable;
import edu.baylor.csi3471.netime_planner.gui.controllers.ViewScheduleController;
import edu.baylor.csi3471.netime_planner.gui.form.CreateActivityForm;
import edu.baylor.csi3471.netime_planner.gui.form.CreateDeadlineForm;
import edu.baylor.csi3471.netime_planner.gui.form.CreateEventForm;
import edu.baylor.csi3471.netime_planner.gui.windows.WorktimesWindow;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.util.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class ViewScheduleScreen implements EventDoubleClickHandler {
    private static final Logger LOGGER = Logger.getLogger(ViewScheduleScreen.class.getName());
    private final ViewScheduleController controller;
    private final Schedule schedule;

    private JPanel mainPanel;
    private JLabel todaysDateLabel;
    private JButton calculateFreeTimeButton;
    private JButton shareButton;
    private JButton toDoListButton;
    private JButton setWorkTimesButton;
    private JButton addActivityButton;
    private JButton addDeadlineButton;
    private ViewScheduleTable table;
    private JButton editButton;
    private JButton removeButton;

    public ViewScheduleScreen(Schedule s) {
        this.schedule = s;
        controller = new ViewScheduleController(s);

        $$$setupUI$$$();
        var startDate = DateUtils.getLastSunday();
        var endDate = startDate.plusDays(6);
        var formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
        todaysDateLabel.setText(startDate.format(formatter) + " - " + endDate.format(formatter));

        toDoListButton.addActionListener(this::showTodoList);
        shareButton.addActionListener(this::share);
        calculateFreeTimeButton.addActionListener(this::calculateFreeTime);
        setWorkTimesButton.addActionListener(this::setWorkTimes);
        addActivityButton.addActionListener(this::addActivity);
        addDeadlineButton.addActionListener(this::addDeadline);
        removeButton.addActionListener(this::removeSelected);
        editButton.addActionListener(this::editSelected);

        table.addEventDoubleClickHandler(this);
    }

    private void createUIComponents() {
        table = new ViewScheduleTable(schedule, DateUtils.getLastSunday());
    }

    @Override
    public void eventDoubleClicked(Event e) {
        var form = CreateActivityForm.createForm(e);
        form.setSubmissionListener(actionEvent -> {
            Event newEvent = form.getCreatedValue();
            controller.changeEvent(e, newEvent);
            form.setVisible(false);
        });
        form.setVisible(true);
    }

    @Override
    public void multipleEventsDoubleClicked(List<Event> events) {
        LOGGER.info("Events double clicked");
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void showTodoList(ActionEvent e) {
        LOGGER.info("Show to-do list clicked");
    }

    private void share(ActionEvent e) {
        LOGGER.info("Share clicked");
    }

    private void calculateFreeTime(ActionEvent e) {
        LOGGER.info("Calculate free time clicked");
    }

    private void setWorkTimes(ActionEvent e) {
        new WorktimesWindow(schedule).setVisible(true);
    }

    private void addActivity(ActionEvent e) {
        var form = new CreateActivityForm();
        form.setSubmissionListener(actionEvent -> {
            controller.addEvent(form.getCreatedValue());
            form.setEnabled(false);
            form.setVisible(false);
        });
        form.setVisible(true);
    }

    private void addDeadline(ActionEvent e) {
        var form = new CreateDeadlineForm();
        form.setSubmissionListener(ev -> {
            controller.addEvent(form.getCreatedValue());
            form.setEnabled(false);
            form.setVisible(false);
        });
        form.setVisible(true);
    }

    private void editSelected(ActionEvent e) {
        List<Event> selected = table.getSelectedCell();
        if (selected.size() == 1) {
            Event ev = selected.get(0);
            var form = CreateEventForm.createForm(ev);
            form.setSubmissionListener(actionEvent -> {
                Event newEvent = form.getCreatedValue();
                controller.changeEvent(ev, newEvent);
                form.setVisible(false);
            });
            form.setVisible(true);
        }
    }

    private void removeSelected(ActionEvent e) {
        List<Event> selected = table.getSelectedCell();
        if (selected.size() == 1) {
            var ev = selected.get(0);
            controller.removeEvent(ev);
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        todaysDateLabel = new JLabel();
        todaysDateLabel.setText("Label");
        mainPanel.add(todaysDateLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table.setFillsViewportHeight(true);
        scrollPane1.setViewportView(table);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        mainPanel.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        shareButton = new JButton();
        shareButton.setText("Share This Schedule");
        panel2.add(shareButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        calculateFreeTimeButton = new JButton();
        calculateFreeTimeButton.setText("Calculate Free Time");
        panel2.add(calculateFreeTimeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        toDoListButton = new JButton();
        toDoListButton.setText("Generate To-Do List");
        panel2.add(toDoListButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        setWorkTimesButton = new JButton();
        setWorkTimesButton.setText("Set Work Times");
        panel2.add(setWorkTimesButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addDeadlineButton = new JButton();
        addDeadlineButton.setText("Add Deadline");
        panel2.add(addDeadlineButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addActivityButton = new JButton();
        addActivityButton.setText("Add Activity");
        panel2.add(addActivityButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit Selected");
        panel2.add(editButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setText("Remove Selected");
        panel2.add(removeButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Actions");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Editing");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final TodoListSidePanel todoListSidePanel1 = new TodoListSidePanel();
        mainPanel.add(todoListSidePanel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


}
