package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.models.EventDoubleClickHandler;
import edu.baylor.csi3471.netime_planner.util.DateUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ViewScheduleTable extends JTable {
    private Controller controller;
    private List<EventDoubleClickHandler> dcHandlers = new ArrayList<>();

    public ViewScheduleTable(Controller controller) {
       super(new ViewScheduleTableModel(controller, DateUtils.getLastSunday()));
       System.out.println(controller.getMaxSize());
       this.setRowHeight(controller.getMaxSize()*60);

       setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                //var table = (ViewScheduleTable) mouseEvent.getSource();
                //Point point = mouseEvent.getPoint();
                //int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && getSelectedRow() != -1) {
                    cellDoubleClicked();
                }
            }
        });
    }

    public void addEventDoubleClickHandler(EventDoubleClickHandler e) {
        dcHandlers.add(e);
    }

    protected void cellDoubleClicked() {
        var events = getModel().getEventsAt(getSelectedRow(), getSelectedColumn());
        if (events.size() == 1) {
            dcHandlers.forEach(h -> h.eventDoubleClicked(events.get(0)));
        }
        else if (events.size() > 0) {
            dcHandlers.forEach(h -> h.multipleEventsDoubleClicked(events));
        }
    }

    @Override
    public ViewScheduleTableModel getModel() {
        return (ViewScheduleTableModel) super.getModel();
    }
}
