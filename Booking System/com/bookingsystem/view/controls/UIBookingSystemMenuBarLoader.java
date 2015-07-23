package com.bookingsystem.view.controls;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemMenuBarLoader extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -755878926887770109L;
	private JMenuItem importMenuOption = new JMenuItem();

	public UIBookingSystemMenuBarLoader() {
		importMenuOption = new JMenuItem("Import");
		JMenu menu;
		menu = new JMenu("File");
		menu.add(importMenuOption);
		this.add(menu);
	}

	public void addImportOptionListener(ActionListener al) {
		importMenuOption.addActionListener(al);
	}

	// --Commented out by Inspection START (21/06/2015 00:50):
	// public void addExitOptionListener(ActionListener al) {
	// exitMenuOption.addActionListener(al);
	// }
	// --Commented out by Inspection STOP (21/06/2015 00:50)

}
