package com.bookingsystem.view.controls;

import com.bookingsystem.model.Log;
import com.bookingsystem.model.tablemodel.LogTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author: [Alex]
 */
public class UIBookingSystemJTableLogs extends UIBookingSystemJTable {
    private final LogTableModel logTableModel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    public UIBookingSystemJTableLogs(LogTableModel logTableModel) {
        super();
        this.logTableModel = logTableModel;
        this.setModel(this.logTableModel);
    }

    @Override
    public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
        for (Object object : arrayList) {
            addRowToList(object);
        }
    }

    @Override
    public void addRowToList(Object data) {
        Log log = (Log) data;
        logTableModel.addRow(new Object[]{log.getLogID(), log.getEventLogged(),
                log.getClassEvent(),
                dateFormat.format(log.getDateAndTimeOfEvent()),
                log.getIdPlayedWith()});
    }

    @Override
    public Object getRowFromList(int identifierOfData) {
        if (identifierOfData >= 0) {
            return new Log((String) logTableModel.getValueAt(identifierOfData, 0),
                    (String) logTableModel.getValueAt(identifierOfData, 1),
                    (Date) logTableModel.getValueAt(identifierOfData, 2));
        } else return null;
    }

    @Override
    public void replaceRowInList(Object rowData) {
    }

    @Override
    public void removeRowFromList() {
        if (this.getSelectedRow() < logTableModel.getRowCount() && this.getSelectedRow() >= 0) {
            logTableModel.removeRow(this.getSelectedRow());
        } else {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
    }

    @Override
    public void removeAllRowsFromList() {
        int rowCount = logTableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            logTableModel.removeRow(i);
        }
    }

}
