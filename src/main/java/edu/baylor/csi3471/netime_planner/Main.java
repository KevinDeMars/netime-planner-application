package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.ViewScheduleScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::showGui);
    }

    static void showGui() {
        // TODO: Probably should make a "main window" class at some point
        var window = new JFrame();
        window.setTitle("NETime Planner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new ViewScheduleScreen().getPanel());
        window.pack();
        window.setVisible(true);
    }
}
