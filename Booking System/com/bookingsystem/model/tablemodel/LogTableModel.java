package com.bookingsystem.model.tablemodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import com.bookingsystem.model.Log;

/**
 * Author: [Alex]
 */
public class LogTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = -1125627184403170209L;
    private static final int COLUMN_NO = 0;
    private static final int COLUMN_TIMESTAMP = 1;
    private static final int COLUMN_CLASS = 2;
    private static final int COLUMN_EVENT = 3;
    private static final int COLUMN_ID_MODIFIED = 4;

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat(
            "dd/MM/yy - HH:mm", Locale.ENGLISH);

    private final String[] columnNames = {"ID", "Timestamp", "Class", "Event",
            "ID Modified"};
    private final List<Log> logList;

    public LogTableModel(List<Log> logList) {
        super();
        this.logList = logList;
    }

    @Override
    public int getRowCount() {
        return logList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Log log = logList.get(rowIndex);
        Object returnValue;
        switch (columnIndex) {
            case COLUMN_NO:
                returnValue = log.getLogID();
                break;
            case COLUMN_EVENT:
                returnValue = log.getEventLogged();
                break;
            case COLUMN_CLASS:
                returnValue = log.getClassEvent();
                break;
            case COLUMN_TIMESTAMP:
                returnValue = BOOKING_TIME_FORMAT.format(log
                        .getDateAndTimeOfEvent());
                break;
            case COLUMN_ID_MODIFIED:
                returnValue = log.getIdPlayedWith();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (logList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    public void clearArchiveList() {
        this.logList.clear();
    }

    public void addLogList(List<Log> logList) {
        for (Log log : logList) {
            this.logList.add(log);
            this.fireTableDataChanged();
        }
    }

}
