package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.EventVisitor;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;

import java.util.Optional;

public class EventDbDAO extends DatabaseDAO<Event>{
    //private Event e;
    private final DeadlineDbDAO deadlineDao = new DeadlineDbDAO();
    private final ActivityDbDAO activityDao = new ActivityDbDAO();

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
    protected void doUpdate(Event obj) {
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                deadlineDao.doUpdate(d);
            }

            @Override
            public void visit(Activity a) {
                activityDao.doUpdate(a);
            }
        };
        obj.acceptVisitor(visitor);
    }

    @Override
    protected void doInsert(Event obj) {
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                deadlineDao.doInsert(d);
            }

            @Override
            public void visit(Activity a) {
                activityDao.doInsert(a);
            }
        };
        obj.acceptVisitor(visitor);
    }

    int numSchedulesReferencedBy(Event e) {
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
        return result[0];
    }
}
