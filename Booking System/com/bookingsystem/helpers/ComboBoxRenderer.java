package com.bookingsystem.helpers;

import com.bookingsystem.model.Equipment;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

public class ComboBoxRenderer extends DefaultListCellRenderer {
    /**
     *
     */
    private static final long serialVersionUID = 2371466419500199586L;
    ArrayList<String> tooltips;// = new ArrayList<String>();

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        value = ((Equipment) value).fakeToString();

        if (-1 < index && null != value && null != tooltips) {
            list.setToolTipText(tooltips.get(index));
        }
        JComponent comp = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);
        return comp;
    }

    public void addTooltip(String toolTip) {
        this.tooltips.add(toolTip);
    }

    public void removeToolTip(int index) {
        this.tooltips.remove(index);
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
