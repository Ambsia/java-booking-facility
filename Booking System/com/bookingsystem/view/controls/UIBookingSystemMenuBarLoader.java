package com.bookingsystem.view.controls;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemMenuBarLoader extends JMenuBar {

    /**
	 * 
	 */
	private static final long serialVersionUID = -755878926887770109L;
	private JMenuItem importMenuOption = new JMenuItem();
    private JMenuItem exitMenuOption = new JMenuItem();

    public UIBookingSystemMenuBarLoader() {
        importMenuOption = new JMenuItem("Import");
        exitMenuOption = new JMenuItem("Exit");
        JMenu menu;
        menu = new JMenu("File");
        menu.add(importMenuOption);
        menu.addSeparator();
        menu.add(exitMenuOption);
        this.add(menu);
    }

    public void addImportOptionListener(ActionListener al) {
        importMenuOption.addActionListener(al);
    }

    public void addExitOptionListener(ActionListener al) {
        exitMenuOption.addActionListener(al);
    }


}
