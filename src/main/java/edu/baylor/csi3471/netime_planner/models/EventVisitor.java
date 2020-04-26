package edu.baylor.csi3471.netime_planner.models;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;

public abstract class EventVisitor {
    public void visit(Event e) {}
    public void visit(Deadline d) {}
    public void visit(Activity a) {}
}
