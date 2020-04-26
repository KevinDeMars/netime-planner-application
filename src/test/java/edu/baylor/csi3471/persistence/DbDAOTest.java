package edu.baylor.csi3471.persistence;

import edu.baylor.csi3471.ServiceConfiguration;
import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DbDAOTest<T extends DomainObject> {
    private static final Logger LOGGER = Logger.getLogger(DbDAOTest.class.getName());

    @BeforeAll
    static void configure() {
        ServiceConfiguration.configureServices();
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
