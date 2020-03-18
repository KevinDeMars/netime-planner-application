package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.MainWindow;
import edu.baylor.csi3471.netime_planner.models.Controller;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // make it look fancy
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            System.out.println("Can't change look and feel: " + e.getLocalizedMessage());
        }

        SwingUtilities.invokeLater(Main::showMainWindow);
    }

    static void showMainWindow() {
        var controller = new Controller();
        new MainWindow(controller).setVisible(true);
    }
}