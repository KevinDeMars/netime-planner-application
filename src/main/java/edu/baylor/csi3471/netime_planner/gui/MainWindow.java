package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.Controller;

import javax.swing.*;

public class MainWindow extends JFrame {
    private Controller controller;

    public MainWindow(Controller controller) {
        this.controller = controller;
        setTitle("NETime Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new ViewScheduleScreen(controller).getPanel());
        pack();
    }
}
