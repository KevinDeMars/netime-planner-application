package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.Event;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewScheduleTableCellRenderer implements TableCellRenderer, TableModelListener, ActionListener {
    private Map<Pair<Integer, Integer>, JPanel> panels = new HashMap<>();

    public ViewScheduleTableCellRenderer(ViewScheduleTable table) {
        var model = (ViewScheduleTableModel) table.getModel();
        model.addTableModelListener(this);
        updateAll(model);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!(value instanceof List)) {
            throw new IllegalStateException("Can't render a value that isn't a list");
        }

        return panels.get(new Pair<>(row, column));
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        var model = (ViewScheduleTableModel) e.getSource();
        /*
        switch (e.getType()) {
            case TableModelEvent.INSERT:
                break;
            case TableModelEvent.UPDATE:
                break;
            case TableModelEvent.DELETE:

        }
         */
        // For now, just remake all of the panels
        // TODO: Probably bad for performance

        updateAll(model);
    }

    private void updateAll(ViewScheduleTableModel model) {
        panels.values().forEach(Container::removeAll);
        for (int r = 0; r < model.getRowCount(); ++r) {
            for (int c = 1; c < model.getColumnCount(); ++c) {
                updatePanel(model, r, c);
            }
        }
    }

    private void updatePanel(ViewScheduleTableModel model, int r, int c) {
        var events = (List<Event>) model.getValueAt(r, c);
        if (!events.isEmpty()) {
            var pair = new Pair<>(r, c);
            var panel = panels.computeIfAbsent(pair, p -> new JPanel());
            for (var event : events) {
                var button = new JButton(event.getName());
                button.addActionListener(this);
                panel.add(button);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button pressed");
    }
}
