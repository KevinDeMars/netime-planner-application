package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.EventVisitor;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.util.Optional;

public class EventDAO implements DAO<Event> {
    private final DeadlineDAO deadlineDao = ServiceManager.getInstance().getService(DeadlineDAO.class);
    private final ActivityDAO activityDao = ServiceManager.getInstance().getService(ActivityDAO.class);

    @Override
    public Optional<Event> findById(int id) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO: This function doesn't really make sense
    }

    @Override
    public void delete(Event obj) {
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                deadlineDao.delete(d);
            }

            @Override
            public void visit(Activity a) {
                activityDao.delete(a);
            }
        };
        obj.acceptVisitor(visitor);
    }

    @Override
    public void save(Event obj) {
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                deadlineDao.save(d);
            }

            @Override
            public void visit(Activity a) {
                activityDao.save(a);
            }
        };
        obj.acceptVisitor(visitor);
    }

    public int numSchedulesReferencedBy(Event e) {
        int[] result = new int[1];
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                result[0] = deadlineDao.numSchedulesReferencedBy(d);
            }

            @Override
            public void visit(Activity a) {
                result[0] = activityDao.numSchedulesReferencedBy(a);
            }
        };
        e.acceptVisitor(visitor);
        return result[0];
    }
}
