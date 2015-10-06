package com.bookingsystem.view.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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
    private JMenuItem undoOption;
    private JMenu jMenuFile;
    private JMenu jMenuEdit;
    private JMenuItem[] jMenuItems = {};

    public UIBookingSystemMenuBarLoader() {
    	

        importMenuOption = new JMenuItem("Import");//, new ImageIcon("import.png"));
        exportMenuOption = new JMenuItem("Export");//, new ImageIcon("export.png"));
        exitProgramOption = new JMenuItem("Exit");//, new ImageIcon("exit.png"));
        undoOption = new JMenuItem("Undo");
        jMenuItems = new JMenuItem[] {importMenuOption,exportMenuOption,exitProgramOption, undoOption};
        jMenuFile = new JMenu("File");
        jMenuEdit = new JMenu("Edit");

        importMenuOption.setMnemonic(KeyEvent.VK_I);
        importMenuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        exportMenuOption.setMnemonic(KeyEvent.VK_E);
        exportMenuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exitProgramOption.setMnemonic(KeyEvent.VK_X);
        exitProgramOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        jMenuFile.add(importMenuOption);
        jMenuFile.add(exportMenuOption);
        jMenuFile.addSeparator();
        jMenuFile.add(exitProgramOption);
        this.add(jMenuFile);


        undoOption.setMnemonic(KeyEvent.VK_Z);
        undoOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

        jMenuEdit.add(undoOption);

        this.add(jMenuEdit);

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
