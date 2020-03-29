package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.LoginWindow;
import edu.baylor.csi3471.netime_planner.gui.MainWindow;
import edu.baylor.csi3471.netime_planner.models.Controller;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private Controller controller = new Controller();

    public static void main(String[] args) {
        // Configure loggers
        try {
            InputStream configFile = Main.class.getClassLoader().getResourceAsStream("logger.properties");
            LogManager.getLogManager().readConfiguration(configFile);
            if (configFile != null) {
                configFile.close();
            }
        } catch (IOException ex) {
            LOGGER.warning("Could not open configuration file");
            LOGGER.warning("Logging not configured (console output only)");
        }

        new Main().run();
    }

    public void run() {
        // make it look fancy
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Can't change look and feel", e);
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