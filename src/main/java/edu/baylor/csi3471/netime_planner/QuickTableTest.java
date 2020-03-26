package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.ViewScheduleScreen;
import edu.baylor.csi3471.netime_planner.models.Controller;

import javax.swing.*;

public class QuickTableTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuickTableTest::openScheduleView);
    }

    private static void openScheduleView() {
        var controller = new Controller();
        controller.init("cooluser", true);
        var window = new JFrame("Quick Schedule Debug");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setContentPane(new ViewScheduleScreen(controller).getPanel());
        window.pack();
        window.setVisible(true);
    }
}
