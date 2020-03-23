package edu.baylor.csi3471.netime_planner.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.util.DateUtils;
import edu.baylor.csi3471.netime_planner.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;

public class ViewScheduleScreen {
    private Controller controller;
    private JPanel mainPanel;
    private JLabel todaysDateLabel;
    private JButton calculateFreeTimeButton;
    private JButton shareButton;
    private JButton toDoListButton;
    private JButton setWorkTimesButton;
    private JButton addActivityButton;
    private JButton addDeadlineButton;
    private ViewScheduleTable table;
    private JButton saveButton;

    public ViewScheduleScreen(Controller controller) {
        this.controller = controller;

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
        saveButton.addActionListener(this::save);
    }

    private void createUIComponents() {
        table = new ViewScheduleTable(controller);

        table.setRowHeight(60);


        System.out.println("creating table");
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void showTodoList(ActionEvent e) {
        System.out.println("Show to-do list");
    }

    private void share(ActionEvent e) {
        System.out.println("Share");
    }

    private void calculateFreeTime(ActionEvent e) {
        System.out.println("Calculate free time");
    }

    private void setWorkTimes(ActionEvent e) {
        System.out.println("Set work times");
    }

    private void addActivity(ActionEvent e) {
        var form = new CreateActivityForm();
        form.setSubmissionListener(actionEvent -> {
            controller.addEvent(form.getCreatedValue());
        });
        form.setVisible(true);
    }

    private void addDeadline(ActionEvent e) {
        var form = new CreateDeadlineForm();
        form.setSubmissionListener(ev -> {
            controller.addEvent(form.getCreatedValue());
        });
        form.setVisible(true);
    }

    private void save(ActionEvent e) {
        if (controller.saveLocally())
            JOptionPane.showMessageDialog(mainPanel, "Saved Successfully", "Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(mainPanel, "Could not save " + StringUtils.usernameToDataFile(controller.getUser().getName()),
                    "Error saving", JOptionPane.ERROR_MESSAGE);
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
        mainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1, true, false));
        todaysDateLabel = new JLabel();
        todaysDateLabel.setText("Label");
        mainPanel.add(todaysDateLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(455, 16), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(455, 428), null, 0, false));
        table.setFillsViewportHeight(true);
        scrollPane1.setViewportView(table);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        mainPanel.add(panel1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(455, 62), null, 0, false));
        calculateFreeTimeButton = new JButton();
        calculateFreeTimeButton.setText("Calculate Free Time");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(calculateFreeTimeButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        shareButton = new JButton();
        shareButton.setText("Share This Schedule");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(shareButton, gbc);
        toDoListButton = new JButton();
        toDoListButton.setText("Generate To-Do List");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(toDoListButton, gbc);
        setWorkTimesButton = new JButton();
        setWorkTimesButton.setText("Set Work Times");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(setWorkTimesButton, gbc);
        addActivityButton = new JButton();
        addActivityButton.setText("Add Activity");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(addActivityButton, gbc);
        addDeadlineButton = new JButton();
        addDeadlineButton.setText("Add Deadline");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(addDeadlineButton, gbc);
        saveButton = new JButton();
        saveButton.setText("Manually Save");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(saveButton, gbc);
        final TodoListSidePanel todoListSidePanel1 = new TodoListSidePanel();
        mainPanel.add(todoListSidePanel1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }


}
