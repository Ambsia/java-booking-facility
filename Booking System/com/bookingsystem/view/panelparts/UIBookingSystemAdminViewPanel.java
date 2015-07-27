package com.bookingsystem.view.panelparts;

import com.bookingsystem.model.tablemodel.LogTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminViewPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -5656361363227035414L;
    private final JTable bookingSystemJTableLogs;
    private final JScrollPane jScrollPane;
    private LogTableModel logTableModel;

    public UIBookingSystemAdminViewPanel() {
        bookingSystemJTableLogs = new JTable(logTableModel);
        setLayout(new GridBagLayout());
        jScrollPane = new JScrollPane(bookingSystemJTableLogs);
        addControlToPanel(jScrollPane);
    }

    private void addControlToPanel(Component component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(component, gbc);
    }

    public LogTableModel getJTableModel() {
        return this.logTableModel;
    }

    public void setJTableModel(LogTableModel logTableModel) {
        this.logTableModel = logTableModel;
        this.bookingSystemJTableLogs.setModel(this.logTableModel);
        this.bookingSystemJTableLogs.setAutoCreateRowSorter(true);
        this.logTableModel.fireTableDataChanged();
    }

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public int selectedRowCount() {
    // return bookingSystemJTableLogs.getSelectedRowCount();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    // --Commented out by Inspection START (21/06/2015 00:49):
    // int rowViewIndexToModel(int row) {
    // return bookingSystemJTableLogs.convertRowIndexToModel(row);
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public Object getValueAt(int row, int column) {
    // return logTableModel.getValueAt(row,column);
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public List<Integer> getSelectedRows() {
    // List<Integer> integerList = new ArrayList<>();
    // for (int i = 0; i<bookingSystemJTableLogs.getSelectedRows().length;i++) {
    // integerList.add(rowViewIndexToModel(bookingSystemJTableLogs.getSelectedRows()[i]));
    // }
    // return integerList;
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public int getSelectedRow() {
    // return bookingSystemJTableLogs.getSelectedRow();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    // public void addLogToList(Log log) {
    // bookingSystemJTableLogs.addRowToList(log);
    // }
    //
    // public void addLogsToList(ArrayList<Log> listOfLogs) {
    // ArrayList<Object> objectArrayList = new ArrayList<>();
    // for (Log log : listOfLogs) {
    // objectArrayList.add(log);
    // }
    // bookingSystemJTableLogs.addArrayOfRowsToList(objectArrayList);
    // }
    //
    // public Log getLogFromList(int logId) {
    // return (Log) bookingSystemJTableLogs.getRowFromList(logId);
    // }
    //
    // public int getIndexOfSelectedRow() {
    // return bookingSystemJTableLogs.getSelectedRow();
    // }
    //
    // public int getIDOfSelectedRow() {
    // return bookingSystemJTableLogs.getIDOfSelectedRow();
    // }
    //
    // public int getRowCountOfTable() {
    // return bookingSystemJTableLogs.getRowCount();
    // }
    //
    // public void removeLogFromTable() {
    // bookingSystemJTableLogs.removeRowFromList();
    // }
    //
    // public void removeAllLogs() {
    // bookingSystemJTableLogs.removeAllRowsFromList();
    // }
}
