package com.bookingsystem.view.controls;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemMenuBarLoader extends JMenuBar {

    JMenuItem importMenuOption = new JMenuItem();
    JMenuItem exitMenuOption = new JMenuItem();

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
