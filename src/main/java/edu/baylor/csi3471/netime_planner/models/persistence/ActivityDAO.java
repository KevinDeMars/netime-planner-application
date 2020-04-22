package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;

public interface ActivityDAO extends DAO<Activity> {
    int numSchedulesReferencedBy(Activity a);
}
