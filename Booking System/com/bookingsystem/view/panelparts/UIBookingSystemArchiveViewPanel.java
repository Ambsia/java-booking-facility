package com.bookingsystem.view.panelparts;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Created by Alex on 24/05/2015.
 */
public class UIBookingSystemArchiveViewPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6827303413910281383L;
	private JTextArea jTextArea;
    private JScrollPane jScrollPane;
    public UIBookingSystemArchiveViewPanel() {
        setLayout(new GridBagLayout());
        jTextArea = new JTextArea();
        jTextArea.setBackground(this.getBackground());
        Font font = new Font("Verdana", Font.BOLD, 12);
        jTextArea.setFont(font);
        jScrollPane = new JScrollPane(jTextArea);



        addControlToPanel(jScrollPane);
    }

    private void addControlToPanel(Component component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(component, gbc);
    }

    public void setJTextArea(String textArea) {
        this.jTextArea.setText(textArea);
    }


}
