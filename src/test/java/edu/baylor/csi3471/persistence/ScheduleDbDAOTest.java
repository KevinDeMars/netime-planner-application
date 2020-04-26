package edu.baylor.csi3471.persistence;

import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.persistence.ActivityDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.impl.ScheduleDbDAO;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleDbDAOTest extends DbDAOTest<Schedule> {
    @Override
    DatabaseDAO<Schedule> getDAO() {
        return new ScheduleDbDAO();
    }

    @Override
    Schedule makeNewRow() {
        var s = new Schedule();
        s.addEvent(new Deadline(LocalDateTime.of(2020, 4, 20, 4, 20)));
        return s;
    }

    @Override
    void changeRow(Schedule row) {
        Event e = (Event) row.getEvents().toArray()[0];
        e.setName("test" + Math.random());
    }

    @Test
    void testUpdateWithDeadline() {
        var dao = getDAO();
        var s = makeNewRow();
        dao.save(s);
        var workTime = new Activity("aa", "aaa", "aaaa",
                LocalDate.of(2020, 4, 20),
                new TimeInterval(LocalTime.of(4, 20), LocalTime.of(5, 20)));
        s.getWorkTimes().add(workTime);
        dao.save(s);

        dao.clearCache();
        var inDb = dao.findById(s.getId()).get();
        assertTrue(inDb.getWorkTimes().contains(workTime));

        s.getWorkTimes().clear();
        dao.save(s);
        dao.clearCache();
        inDb = dao.findById(s.getId()).get();
        assertFalse(inDb.getWorkTimes().contains(workTime));

        var activityDao = ServiceManager.getInstance().getService(ActivityDAO.class);
        assertFalse(activityDao.findById(workTime.getId()).isPresent());

        dao.delete(s);
        assertFalse(dao.findById(s.getId()).isPresent());
    }
}
