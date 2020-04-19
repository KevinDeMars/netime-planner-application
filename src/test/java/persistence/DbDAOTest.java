package persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DbDAOTest<T extends DomainObject> {
    private static final Logger LOGGER = Logger.getLogger(DbDAOTest.class.getName());

    @BeforeAll
    static void configureDb() {
        var mgr = ServiceManager.getInstance();
        try (InputStream dbConfig = DbDAOTest.class.getClassLoader().getResourceAsStream("database.test.properties")) {
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
    }

    abstract DatabaseDAO<T> getDAO();
    abstract T makeNewRow();
    abstract void changeRow(T row);

    @Test
    void testAddRemove() {
        var dao = getDAO();
        var row = makeNewRow();
        dao.save(row);
        var inDb = dao.findById(row.getId());
        assertTrue(inDb.isPresent());
        assertEquals(inDb.get(), row);
        dao.delete(row);
        assertFalse(dao.findById(row.getId()).isPresent());
    }

    @Test
    void testUpdate() {
        var dao = getDAO();
        var row = makeNewRow();
        dao.save(row);
        var inDb = dao.findById(row.getId());
        assertTrue(inDb.isPresent());
        assertEquals(inDb.get(), row);

        changeRow(row);
        dao.save(row);
        inDb = dao.findById(row.getId());

        assertTrue(inDb.isPresent());
        assertEquals(row, inDb.get());

        dao.delete(row);
    }
}
