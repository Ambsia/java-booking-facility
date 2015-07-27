package com.bookingsystem.view.controls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemMenuBarLoader extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = -755878926887770109L;
    private JMenuItem importMenuOption;
    private JMenuItem exportMenuOption;
    private JMenuItem exitProgramOption;
    private JMenu jMenu;
    private JMenuItem[] jMenuItems = {};

    public UIBookingSystemMenuBarLoader() {

        importMenuOption = new JMenuItem("Import", new ImageIcon("import.png"));
        exportMenuOption = new JMenuItem("Export", new ImageIcon("export.png"));
        exitProgramOption = new JMenuItem("Exit", new ImageIcon("exit.png"));
        jMenuItems = new JMenuItem[] {importMenuOption,exportMenuOption,exitProgramOption};
        jMenu = new JMenu("File");

        importMenuOption.setMnemonic(KeyEvent.VK_O);
        importMenuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        exportMenuOption.setMnemonic(KeyEvent.VK_S);
        exportMenuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        exitProgramOption.setMnemonic(KeyEvent.VK_X);
        exitProgramOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        jMenu.add(importMenuOption);
        jMenu.add(exportMenuOption);
        jMenu.addSeparator();
        jMenu.add(exitProgramOption);
        this.add(jMenu);
    }

    public void addImportOptionListener(ActionListener al) {
        for (JMenuItem jMenuItem : jMenuItems) {
            jMenuItem.addActionListener(al);
        }
    }

    // --Commented out by Inspection START (21/06/2015 00:50):
    // public void addExitOptionListener(ActionListener al) {
    // exitMenuOption.addActionListener(al);
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:50)

}
