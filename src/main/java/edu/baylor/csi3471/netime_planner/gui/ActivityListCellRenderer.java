package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;

import javax.swing.*;
import java.awt.*;

public class ActivityListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        if (value instanceof Activity) {
            value = ((Activity)value).timeString();
        }

        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //To change body of generated methods, choose Tools | Templates.

    }

}