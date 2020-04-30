package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.gui.windows.LoginWindow;
import edu.baylor.csi3471.netime_planner.gui.windows.MainWindow;
import edu.baylor.csi3471.netime_planner.models.persistence.*;
import edu.baylor.csi3471.netime_planner.models.persistence.impl.*;
import edu.baylor.csi3471.netime_planner.services.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
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

        try (InputStream dbConfig = new FileInputStream("database.properties")) {
            var properties = new Properties();
            properties.load(dbConfig);
            String url = properties.getProperty("url");



            Connection conn = DriverManager.getConnection(url, properties);
            mgr.addService(Connection.class, conn);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,"Could not open database configuration file", ex);
            System.exit(1);
        }
        catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Could not connect to database", ex);
            System.exit(1);
        }

        mgr.addService(ActivityDAO.class, new ActivityDbDAO());
        mgr.addService(DeadlineDAO.class, new DeadlineDbDAO());
        mgr.addService(EventDAO.class, new EventDAO());
        mgr.addService(GroupDAO.class, new GroupDbDAO());
        mgr.addService(ScheduleDAO.class, new ScheduleDbDAO());
        mgr.addService(UserDAO.class, new UserDbDAO());
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
        var user = ServiceManager.getInstance().getService(UserService.class).getUserInfo(username).get();
        new MainWindow(user).setVisible(true);
    }
}