package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.LoginWindow;
import edu.baylor.csi3471.netime_planner.gui.MainWindow;
import edu.baylor.csi3471.netime_planner.services.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        configureLogger();
        configureServices();
        setLookAndFeel();
        SwingUtilities.invokeLater(Main::showLoginWindow);
    }

    private static void configureLogger() {
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
    }

    private static void configureServices() {
        var mgr = ServiceManager.getInstance();
        mgr.addService(LoginVerificationService.class, new LocalLoginVerificationService());
        mgr.addService(GroupService.class, new GroupService());
        mgr.addService(ScheduleService.class, new ScheduleService());
        mgr.addService(UserService.class, new UserService());
    }

    private static void setLookAndFeel() {
        // make it look fancy
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Can't change look and feel", e);
        }
    }

    private static void showLoginWindow() {
        var window = new LoginWindow();
        window.addLoginEventListener(Main::afterLogin);
        window.setVisible(true);
    }

    private static void afterLogin(String username) {
        new MainWindow(username).setVisible(true);
    }
}