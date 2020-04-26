package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.gui.form.CreateEventForm;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.services.ScheduleService;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ViewScheduleTable extends JTable {
    private final List<EventDoubleClickHandler> dblClickHandlers = new ArrayList<>();
    private final Schedule schedule;

    public ViewScheduleTable(Schedule s, LocalDate date) {
        super(new ViewScheduleTableModel(s, date));

        this.schedule = s;
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        var popupMenu = new JPopupMenu();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && getSelectedRow() != -1) {
                    cellDoubleClicked();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = rowAtPoint(e.getPoint());
                    int col = columnAtPoint(e.getPoint());
                    popupMenu.removeAll();

                    var menuItem = new JMenuItem("Add deadline here");
                    menuItem.addActionListener(actionEvent -> addDeadlineHere(row, col));
                    popupMenu.add(menuItem);

                    menuItem = new JMenuItem("Add activity here");
                    menuItem.addActionListener(actionEvent -> addActivityHere(row, col));
                    popupMenu.add(menuItem);

                    var events = getModel().getEventsAt(row, col);
                    for (var event : events) {
                        menuItem = new JMenuItem("Edit " + event.getName());
                        menuItem.addActionListener(actionEvent -> editEvent(event));
                        popupMenu.add(menuItem);

                        menuItem = new JMenuItem("Delete " + event.getName());
                        menuItem.addActionListener(actionEvent -> deleteEvent(event));
                        popupMenu.add(menuItem);

                        menuItem = new JMenuItem("Copy " + event.getName());
                        menuItem.addActionListener(actionEvent -> copyEvent(event));
                        popupMenu.add(menuItem);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void editEvent(Event ev) {
        var form = CreateEventForm.createForm(ev);
        form.setSubmissionListener(actionEvent -> {
            Event newEvent = form.getCreatedValue();
            ServiceManager.getInstance().getService(ScheduleService.class)
                    .changeEvent(schedule, ev, newEvent);
            form.setVisible(false);
        });
        form.setVisible(true);
    }

    private void deleteEvent(Event e) {
        ServiceManager.getInstance().getService(ScheduleService.class)
                .removeEvent(schedule, e);
    }

    private void copyEvent(Event e) {
        // TODO
        Logger.getLogger(ViewScheduleTable.class.getName()).info("Copy Event");
    }

    private void addDeadlineHere(int row, int col) {
        // TODO
        Logger.getLogger(ViewScheduleTable.class.getName()).info("Add deadline here");
    }

    private void addActivityHere(int row, int col) {
        // TODO
        Logger.getLogger(ViewScheduleTable.class.getName()).info("Add activity here");
    }

    public void addEventDoubleClickHandler(EventDoubleClickHandler e) {
        dblClickHandlers.add(e);
    }

    protected void cellDoubleClicked() {
        var events = getModel().getEventsAt(getSelectedRow(), getSelectedColumn());
        if (events.size() == 1) {
            dblClickHandlers.forEach(h -> h.eventDoubleClicked(events.get(0)));
        }
        else if (events.size() > 0) {
            dblClickHandlers.forEach(h -> h.multipleEventsDoubleClicked(events));
        }
    }

    @Override
    public ViewScheduleTableModel getModel() {
        return (ViewScheduleTableModel) super.getModel();
    }

    public List<Event> getSelectedCell() {
        return getModel().getEventsAt(getSelectedRow(), getSelectedColumn());
    }
}
