package edu.baylor.csi3471.netime_planner.models;

public abstract class EventVisitor {
    void visit(Event e) {}
    void visit(Deadline d) {}
    void visit(Activity a) {}
}
