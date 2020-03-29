package edu.baylor.csi3471.netime_planner.models;

public abstract class EventVisitor {
    public void visit(Event e) {}
    public void visit(Deadline d) {}
    public void visit(Activity a) {}
}
