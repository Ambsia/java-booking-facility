package com.bookingsystem.model.tablemodel;

import com.bookingsystem.model.Equipment;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 02/07/2015
 */
public class EquipmentTableModel extends AbstractTableModel implements
        Iterable<Equipment> {
    private static final String[] columnNames = {"Equipment ID", " Name",
            "Description", "Usage"};

    private static final int COLUMN_ID = 0;
    private static final int COLUMN_NAME = 1;
    private static final int COLUMN_DESC = 2;
    private static final int COLUMN_EQU_USAGE = 3;

    private final List<Equipment> equipmentList;

    public EquipmentTableModel(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return equipmentList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (equipmentList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Equipment equipment = equipmentList.get(rowIndex);
        Object returnValue;

        switch (columnIndex) {
            case COLUMN_ID:
                returnValue = equipment.getEquipmentID();
                break;
            case COLUMN_NAME:
                returnValue = equipment.getEquipmentName();
                break;
            case COLUMN_DESC:
                returnValue = equipment.getEquipmentDescription();
                break;
            case COLUMN_EQU_USAGE:
                returnValue = equipment.getEquipmentUsage();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Equipment equipment = equipmentList.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_ID:
                equipment.setEquipmentID((int) value);
                break;
            case COLUMN_NAME:
                equipment.setEquipmentName((String) value);
                break;
            case COLUMN_DESC:
                equipment.setEquipmentDescription((String) value);
                break;
            case COLUMN_EQU_USAGE:
                equipment.setEquipmentUsage((int) value);
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);

    }

    public void clearEquipmentList() {
        this.equipmentList.clear();
        this.fireTableDataChanged();
    }

    public void addEquipment(Equipment equipment) {
        this.equipmentList.add(equipment);
        this.fireTableDataChanged();
    }

    public void addEquipmentList(List<Equipment> equipmentList) {
        for (Equipment equipment : equipmentList) {
            this.equipmentList.add(equipment);
            this.fireTableDataChanged();
        }
    }

    public Equipment getEquipment(int id) {
        for (Equipment equipment : this.equipmentList) {
            if (equipment.getEquipmentID() == id) {
                return equipment;
            }
        }
        return null;
    }

    public void removeRows(List<Integer> indices) {
        Collections.sort(indices);
        for (int i = indices.size() - 1; i >= 0; i--) {
            this.equipmentList.remove(indices.get(i).intValue());
            fireTableRowsDeleted(indices.get(i), indices.get(i));
        }
        fireTableDataChanged();
    }

    @Override
    public Iterator<Equipment> iterator() {
        return equipmentList.iterator();
    }

}
