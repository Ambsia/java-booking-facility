package com.bookingsystem.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 08/02/2015.
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
