package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;

public interface DeadlineDAO extends DAO<Deadline> {
    int numSchedulesReferencedBy(Deadline d);
}
