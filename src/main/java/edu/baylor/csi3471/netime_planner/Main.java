package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.LoginWindow;
import edu.baylor.csi3471.netime_planner.gui.MainWindow;
import edu.baylor.csi3471.netime_planner.models.Controller;

import javax.swing.*;

public class Main {
    private Controller controller = new Controller();

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        // make it look fancy
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            System.out.println("Can't change look and feel: " + e.getLocalizedMessage());
        }

        SwingUtilities.invokeLater(this::showLoginWindow);
    }

    void showLoginWindow() {
        var window = new LoginWindow();
        window.addLoginEventListener(this::afterLogin);
        window.setVisible(true);
    }

    void afterLogin(String username, boolean offline) {
        controller.init(username, offline);
        new MainWindow(controller).setVisible(true);
    }
}