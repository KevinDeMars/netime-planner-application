package edu.baylor.csi3471;

import edu.baylor.csi3471.netime_planner.Main;
import edu.baylor.csi3471.netime_planner.models.persistence.*;
import edu.baylor.csi3471.netime_planner.models.persistence.impl.*;
import edu.baylor.csi3471.netime_planner.services.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceConfiguration {
    private static final Logger LOGGER = Logger.getLogger(ServiceConfiguration.class.getName());

    public static void configureServices() {
        var mgr = ServiceManager.getInstance();
        mgr.addService(LoginVerificationService.class, new LocalLoginVerificationService());
        mgr.addService(GroupService.class, new GroupService());
        mgr.addService(ScheduleService.class, new ScheduleService());
        mgr.addService(UserService.class, new UserService());

        try (InputStream dbConfig = Main.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (dbConfig == null) {
                LOGGER.log(Level.SEVERE,"Could not open database configuration file");
                System.exit(1);
            }

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
}
