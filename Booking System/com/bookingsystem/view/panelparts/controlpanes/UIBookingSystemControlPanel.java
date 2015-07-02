package com.bookingsystem.view.panelparts.controlpanes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Alex on 24/05/2015
 */
public abstract class UIBookingSystemControlPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3799165896581222861L;
	private final ArrayList<JButton> controlButtonList;
    private String[] buttonNames;
    private Dimension buttonDimension;
    private int columnns = 3;

    UIBookingSystemControlPanel() {
        controlButtonList = new ArrayList<>();
        buttonNames = new String[]{};
        buttonDimension = new Dimension();
    }

    void createControlPanel() {
        if(buttonNames.length != 0 && buttonDimension != null) {
            for (int buttonNo = 0, colsPassed = 0, rowsPassed = 0; buttonNo < buttonNames.length; buttonNo++) {
                JPanel jPanel = new JPanel();
                JButton jButton = new JButton(buttonNames[buttonNo]);
                jButton.setPreferredSize(buttonDimension);
                if (colsPassed == columnns) { rowsPassed++; colsPassed = 0; }
                addControlToPanel(jPanel, colsPassed++, rowsPassed);
                controlButtonList.add(jButton);
                jPanel.add(controlButtonList.get(buttonNo));
            }
        }
    }

    void setButtonDimension(Dimension dimension) {
        this.buttonDimension = dimension;
    }

    void setButtonNames(String[] buttonNames) {
        this.buttonNames = buttonNames;
    }

    void addControlToPanel(Component component, int gridX, int gridY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(component, gbc);
    }

    public void addListeners(ActionListener al) {
        for (JButton button : controlButtonList) {
            button.addActionListener(al);
        }
    }

    public ArrayList<JButton> getControlButtonList() {
        return  controlButtonList;
    }
    public void setColumnns(int columnns) {
        this.columnns = columnns;
    }
}
