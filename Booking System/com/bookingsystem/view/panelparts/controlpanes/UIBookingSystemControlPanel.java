package com.bookingsystem.view.panelparts.controlpanes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Created by Alex on 24/05/2015.
 */
public abstract class UIBookingSystemControlPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3799165896581222861L;
	private ArrayList<JButton> controlButtonList;
    private String[] buttonNames;
    private Dimension buttonDimension;

    public UIBookingSystemControlPanel() {
        controlButtonList = new ArrayList<>();
        buttonNames = new String[]{};
        buttonDimension = new Dimension();
    }

    protected void createControlPanel() {
        if(buttonNames.length != 0 && buttonDimension != null) {
            for (int buttonNo = 0, colsPassed = 0, rowsPassed = 0; buttonNo < buttonNames.length; buttonNo++) {
                JPanel jPanel = new JPanel();
                JButton jButton = new JButton(buttonNames[buttonNo]);
                jButton.setPreferredSize(buttonDimension);
                if (colsPassed == 3) { rowsPassed++; colsPassed = 0; }
                addControlToPanel(jPanel, colsPassed++, rowsPassed);
                controlButtonList.add(jButton);
                jPanel.add(controlButtonList.get(buttonNo));
            }
        }
    }

    protected void setButtonDimension(Dimension dimension) {
        this.buttonDimension = dimension;
    }

    protected void setButtonNames(String[] buttonNames) {
        this.buttonNames = buttonNames;
    }

    protected void addControlToPanel(Component component, int gridX, int gridY) {
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
}
