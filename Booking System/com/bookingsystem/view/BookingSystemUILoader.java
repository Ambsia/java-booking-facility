package com.bookingsystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.bookingsystem.view.controls.UIBookingSystemMenuBarLoader;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;


public class BookingSystemUILoader extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 3618696916355638648L;
    private final UILoginPanel loginPanel;
    private final UIBookingSystemMenuBarLoader menuBarLoader;
    private final UIBookingSystemTabbedPane bookingSystemTabbedPane;
    private Dimension size;

    public BookingSystemUILoader() {
        Dimension d = new Dimension(500, 250);
        this.setSize(d);
        this.setMinimumSize(d);
        loginPanel = new UILoginPanel();
        menuBarLoader = new UIBookingSystemMenuBarLoader();
        bookingSystemTabbedPane = new UIBookingSystemTabbedPane();
        setLayout(new BorderLayout());
        this.setTitle("LGS Booking System");

        try {
        	
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.synth.SynthLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setFocusable(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public UIBookingSystemMenuBarLoader getMenuBarLoader() {
        return this.menuBarLoader;
    }

    public void showLoginPanel() {
        this.add(loginPanel);
        loginPanel.setEnabled(true);
        this.setLocationRelativeTo(this);
        this.setVisible(true);
        this.pack();
    }

    public void showBookingSystemPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setJMenuBar(menuBarLoader);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        this.add(bookingSystemTabbedPane, gridBagConstraints);

        this.setPreferredSize(new Dimension(1250, 550));
        this.setMinimumSize(new Dimension(1225, 500));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public UIBookingSystemTabbedPane getBookingSystemTabbedPane() {
        return bookingSystemTabbedPane;
    }

    public UILoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void removeLoginPanel() {
        this.remove(loginPanel);
    }

    @Override
	public void setSize(Dimension size) {
        this.size = size;
    }

    @Override
	public Dimension getSize() {
        return size;
    }
}
