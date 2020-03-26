package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.Controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class ViewScheduleTable extends JTable {
    private Controller controller;

    public ViewScheduleTable(Controller controller) {
       super(new ViewScheduleTableModel(controller,LocalDate.now()));
       var renderer = new ViewScheduleTableCellRenderer(this);
       setDefaultRenderer(List.class, renderer);

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

    protected void cellDoubleClicked() {
        var val = getModel().getValueAt(getSelectedRow(), getSelectedColumn());
        System.out.println(val);
    }

}
