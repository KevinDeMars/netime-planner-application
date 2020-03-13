package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.gui.ViewScheduleScreen;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("NETime Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new ViewScheduleScreen().getPanel());
        pack();
    }
}
