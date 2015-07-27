package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.EquipmentTableModel;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemAddEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemEditEquipment;
import com.bookingsystem.view.dialogpanels.equipmentdialog.UIBookingSystemRemoveEquipment;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemEquipmentPanel;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 02/07/2015
 */
public class EquipmentHandler implements ActionListener {

    private final Handler handler;
    private final UIBookingSystemEquipmentControlPanel bookingSystemEquipmentControlPanel;
    private final UIBookingSystemAddEquipment bookingSystemAddEquipment;
    private final UIBookingSystemEditEquipment bookingSystemEditEquipment;
    private final UIBookingSystemRemoveEquipment bookingSystemRemoveEquipment;
    private final UIBookingSystemEquipmentPanel bookingSystemEquipmentPanel;
    private final EquipmentTableModel equipmentTableModel;
    private int currentEquipmentIDBeingHandled;

    public EquipmentHandler(Handler handler) {
        this.handler = handler;
        this.bookingSystemEquipmentPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemEquipmentPanel();
        this.equipmentTableModel = bookingSystemEquipmentPanel.getTableModel();
        this.currentEquipmentIDBeingHandled = -1;
        this.bookingSystemEquipmentControlPanel = this.bookingSystemEquipmentPanel.getBookingSystemEquipmentControlPanel();
        this.bookingSystemAddEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmenttAddPanel();
        this.bookingSystemEditEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmenttEditPanel();
        this.bookingSystemRemoveEquipment = this.bookingSystemEquipmentControlPanel.getBookingSystemEquipmentRemovePanel();
    }

    @Override
    public void actionPerformed(ActionEvent eventOccurred) {
        Log log = new Log(eventOccurred.getActionCommand(), this.getClass()
                .getSimpleName(), new Date());
        switch (eventOccurred.getActionCommand().toString()) {
            case "Refresh":
                handler.getBookingBusinessLayer().getEquipments().populateEquipmentList();
                equipmentTableModel.clearEquipmentList();
                equipmentTableModel.addEquipmentList(IteratorUtils.toList(handler.getBookingBusinessLayer().getEquipments().iterator()));
                handler.initialiseDialogs();
                handler.getLoggerBusinessLayer().insertLog(log);
                break;
            case "Import":
                JFileChooser jFileChooser = new JFileChooser();
                try {
                    if (jFileChooser.showOpenDialog(bookingSystemEquipmentPanel.getParent()) == JFileChooser.APPROVE_OPTION) if (jFileChooser.getSelectedFile().getName()
                            .endsWith(".xlsx")) {
                        File file = jFileChooser.getSelectedFile();
                        try (XSSFWorkbook workBook = (XSSFWorkbook) WorkbookFactory.create(file)) { // creates a smaller footprint
                            // when creating the file
                            // without a stream
                            int dialogResult = 0;
                            //System.out.println(workBook.getNumberOfSheets());
                            if (workBook.getNumberOfSheets() > 1) {
                                String[] sheetNumbers = new String[workBook.getNumberOfSheets()];
                                int i = 0;
                                while (i < workBook.getNumberOfSheets()) {
                                    sheetNumbers[i] = "" + workBook.getSheetAt(i)
                                            .getSheetName();
                                    i++;
                                }
                                dialogResult = JOptionPane.showOptionDialog(
                                        null,
                                        "Which sheet of the document would you like to import, check if you're not sure.",
                                        "Generate Spreadsheet",
                                        JOptionPane.PLAIN_MESSAGE,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null, sheetNumbers, "");
                            }
                            XSSFSheet sheet = workBook.getSheetAt(dialogResult);

                            int rows = sheet.getPhysicalNumberOfRows();
                            List < Equipment > equipmentList = new ArrayList < Equipment > ();
                            for (int r = 0; r < rows; r++) {
                                XSSFRow row = sheet.getRow(r);
                                if (row.toString() != "") {
                                    if (row.getCell((short) 0).toString() != "") {
                                        Equipment importedEquipment = new Equipment(
                                                row.getCell((short) 0)
                                                        .toString());
                                        importedEquipment.setEquipmentDescription(row.getCell((short) 1)
                                                .toString());
                                        if (handler.getBookingBusinessLayer()
                                                .getEquipments()
                                                .addEquipment(importedEquipment)) {
                                            equipmentTableModel.addEquipment(importedEquipment);
                                            equipmentList.add(importedEquipment);
                                            log.setIdPlayedWith(importedEquipment.getEquipmentID());

                                        } else {
                                            MessageBox.errorMessageBox("The equipment '" + importedEquipment.getEquipmentName() + "' already exists.");
                                        }
                                    }
                                }
                            }
                            handler.initialiseDialogs();
                        }
                    }
                } catch (Exception e) {

                }
                handler.getLoggerBusinessLayer().insertLog(log);
                break;

            case "Add":
                if (bookingSystemAddEquipment.showDialog() == 0) {
                    Equipment newEquipment = new Equipment(
                            bookingSystemAddEquipment.getEquipmentName());
                    if (newEquipment.verify()) {
                        newEquipment.setEquipmentDescription(bookingSystemAddEquipment.getEquipmentDescription());
                        if (handler.getBookingBusinessLayer().getEquipments()
                                .addEquipment(newEquipment)) {
                            equipmentTableModel.addEquipment(newEquipment);
                            log.setIdPlayedWith(newEquipment.getEquipmentID());
                            // handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel().getUIBookingSystemAddPanel().addEquipmentToComboBox(newEquipment);
                            // handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel().getUIBookingSystemFindPanel().addEquipmentToComboBox(newEquipment);
                            // handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel().getUIBookingSystemEditPanel().addEquipmentToComboBox(newEquipment);
                            List < Equipment > list = IteratorUtils.toList(handler.getBookingBusinessLayer().getEquipments()
                                    .iterator());
                            ArrayList < Equipment > test = (ArrayList < Equipment > ) list;
                           // System.out.println("size of list:" + list.size());
                            //handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel().getUIBookingSystemAddPanel().addEquipmentToComboBox(newEquipment);
                            handler.initialiseDialogs();
                        } else {
                            MessageBox.errorMessageBox("The equipment '" + newEquipment.getEquipmentName() + "' already exists.");
                        }
                    } else {
                        MessageBox.errorMessageBox("Equipment missing name.");
                    }
                }
                handler.getLoggerBusinessLayer().insertLog(log);
                break;
            case "Edit":
                if (bookingSystemEquipmentPanel.selectedRowCount() > 0) {
                    int modelRow = this.bookingSystemEquipmentPanel.rowViewIndexToModel(this.bookingSystemEquipmentPanel.getSelectedRow());
                   // System.out.println(bookingSystemEquipmentPanel.getValueAt(modelRow, 0));
                    this.currentEquipmentIDBeingHandled = (int) bookingSystemEquipmentPanel.getValueAt(modelRow, 0);
                    if (this.currentEquipmentIDBeingHandled != -1) {
                        Equipment oldEquipment = equipmentTableModel.getEquipment(this.currentEquipmentIDBeingHandled);
                        bookingSystemEditEquipment.setTxtBoxesText(oldEquipment);
                        if (this.bookingSystemEditEquipment.showDialog() == 0) {
                            Equipment newEquipment = new Equipment(
                                    this.bookingSystemEditEquipment.getEquipmentName());
                            newEquipment.setEquipmentDescription(this.bookingSystemEditEquipment.getEquipmentDescription());
                            newEquipment.setEquipmentID(this.currentEquipmentIDBeingHandled);
                            newEquipment.setEquipmentUsage(oldEquipment.getEquipmentUsage());
                            if (handler.getBookingBusinessLayer()
                                    .getEquipments()
                                    .editEquipment(oldEquipment.getEquipmentID(),
                                            newEquipment)) {
                                equipmentTableModel.setValueAt(
                                        this.currentEquipmentIDBeingHandled,
                                        modelRow, 0);
                                equipmentTableModel.setValueAt(
                                        newEquipment.getEquipmentName(), modelRow,
                                        1);
                                equipmentTableModel.setValueAt(
                                        newEquipment.getEquipmentDescription(),
                                        modelRow, 2);
                                equipmentTableModel.setValueAt(
                                        newEquipment.getEquipmentUsage(), modelRow,
                                        3);
                                handler.initialiseDialogs();
                                handler.getView().getBookingSystemTabbedPane()
                                        .getBookingSystemPanel().getJTableModel()
                                        .clearBookingList();
                                handler.getView().getBookingSystemTabbedPane()
                                        .getBookingSystemPanel()
                                        .getBookingSystemViewPanel()
                                        .removeAllProblems();
                                handler.getBookingBusinessLayer()
                                        .populateBookingListOnLoad();
                                handler.getView()
                                        .getBookingSystemTabbedPane()
                                        .getBookingSystemPanel()
                                        .getJTableModel()
                                        .addBookingList(
                                                IteratorUtils.toList(handler.getBookingBusinessLayer()
                                                        .iterator()));
                                log.setAccountIDCreated(newEquipment.getEquipmentID());
                            }
                        }
                    }
                } else {
                    MessageBox.errorMessageBox("Please select the equipment you would like to modify.");

                }
                handler.getLoggerBusinessLayer().insertLog(log);
                break;
            case "Reset Statistic":

                break;
            case "Remove":
                if (bookingSystemEquipmentPanel.selectedRowCount() > 0) {
                    int modelRow = this.bookingSystemEquipmentPanel.rowViewIndexToModel(this.bookingSystemEquipmentPanel.getSelectedRow());
                    this.currentEquipmentIDBeingHandled = (int) bookingSystemEquipmentPanel.getValueAt(modelRow, 0);
                    if (this.currentEquipmentIDBeingHandled != -1) {
                        if (this.bookingSystemRemoveEquipment.showDialog() == 0) {
                            int bookingsUsingEquipmentCount = handler.getBookingBusinessLayer()
                                    .getEquipments()
                                    .checkHowManyBookingsAreUsingEquipment(
                                            this.currentEquipmentIDBeingHandled);
                            int dialogOption = -1;
                            if (bookingsUsingEquipmentCount > 0) {
                                dialogOption = JOptionPane.showOptionDialog(
                                        null,
                                        "This equipment is being used for " + bookingsUsingEquipmentCount + " bookings, if you want to remove the equipment, all bookings using the equipment will be removed.",
                                        "Remove Equipment",
                                        JOptionPane.PLAIN_MESSAGE,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        new String[] {
                                                "Remove", "Cancel"
                                        },
                                        "Remove");
                            }
                            if (dialogOption == 0 || bookingsUsingEquipmentCount == 0) {
                                log.setBookingIDEdited(this.currentEquipmentIDBeingHandled);
                                handler.getBookingBusinessLayer()
                                        .getEquipments()
                                        .removeEquipment(
                                                this.currentEquipmentIDBeingHandled);
                                equipmentTableModel.removeRows(bookingSystemEquipmentPanel.getSelectedRows());
                            }
                        }
                    }
                }
                handler.initialiseDialogs();
                handler.getLoggerBusinessLayer().insertLog(log);
                break;

            default:
        }
    }
}