package edu.baylor.csi3471.netime_planner.gui.command;

import edu.baylor.csi3471.netime_planner.gui.MainWindow;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;

public class OpenUserScheduleCommand extends OpenScheduleCommand {
    private final User u;

    public OpenUserScheduleCommand(MainWindow window, User u) {
        super(window);
        this.u = u;
    }

    @Override
    protected Schedule getSchedule() {
        return u.getSchedule();
    }

    @Override
    protected String getTabName() {
        return u.getName() + "'s Schedule";
    }
}
