package edu.baylor.csi3471.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.DeadlineDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.ScheduleDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.impl.UserDbDAO;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDbDAOTest extends DbDAOTest<User> {

    @Override
    DatabaseDAO<User> getDAO() {
        return new UserDbDAO();
    }

    @Override
    User makeNewRow() {
        var u = new User();
        u.setName("test" + Math.random());
        u.setEmail("test@" + Math.random());
        u.setPasswordHash(new char[]{'a'});
        return u;
    }

    @Override
    void changeRow(User row) {
        row.setName("test" + Math.random());
    }

    @Test
    void testUpdateWithDeadline() {
        var dao = getDAO();
        var user = makeNewRow();
        dao.save(user);
        var deadline = new Deadline("aa", "aa", null, LocalDateTime.now(), null, null);
        user.getSchedule().addEvent(deadline);
        dao.save(user);

        var inDb = dao.findById(user.getId()).get();
        assertTrue(inDb.getSchedule().getEvents().contains(deadline));

        user.getSchedule().getEvents().clear();
        dao.save(user);
        inDb = dao.findById(user.getId()).get();
        assertFalse(inDb.getSchedule().getEvents().contains(deadline));
        var deadlineDAO = ServiceManager.getInstance().getService(DeadlineDAO.class);
        assertFalse(deadlineDAO.findById(deadline.getId()).isPresent());

        var scheduleDAO = ServiceManager.getInstance().getService(ScheduleDAO.class);
        dao.delete(user);
        assertFalse(scheduleDAO.findById(user.getSchedule().getId()).isPresent());
    }
}
