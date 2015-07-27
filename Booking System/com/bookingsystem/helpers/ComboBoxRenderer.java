package com.bookingsystem.helpers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComboBoxRenderer extends DefaultListCellRenderer {
    /**
     *
     */
    private static final long serialVersionUID = 2371466419500199586L;
    ArrayList<String> tooltips;// = new ArrayList<String>();

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        JComponent comp = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        if (-1 < index && null != value && null != tooltips) {
            list.setToolTipText(tooltips.get(index));

        }
        return comp;
    }

    public void addTooltip(String toolTip) {
        this.tooltips.add(toolTip);
    }

    public void removeToolTip(int index) {
        this.tooltips.remove(index);
    }

    public void replaceToolTip(int index, String replacementString) {
        this.tooltips.remove(index);
        this.tooltips.add(index, replacementString);
    }

    public void setTooltips(ArrayList<String> tooltips) {
        if (tooltips == null) {
            System.out.println("null");
        } else {
            this.tooltips = tooltips;
            //System.out.println(this.tooltips);
            // System.out.println(this.tooltips.toString());
        }
    }

}
