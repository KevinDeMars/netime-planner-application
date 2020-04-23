package edu.baylor.csi3471.netime_planner.gui.command;

import edu.baylor.csi3471.netime_planner.gui.MainWindow;
import edu.baylor.csi3471.netime_planner.gui.screens.ViewScheduleScreen;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;

public abstract class OpenScheduleCommand implements Command {
    private final MainWindow window;

    public OpenScheduleCommand(MainWindow window) {
        this.window = window;
    }

    protected abstract Schedule getSchedule();

    protected abstract String getTabName();

    @Override
    public void execute() {
        var screen = new ViewScheduleScreen(getSchedule());
        window.addTab(getTabName(), screen.getPanel());
    }
}
