package com.bookingsystem.view.panes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bookingsystem.model.tablemodel.EquipmentTableModel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;

public class UIBookingSystemEquipmentPanel extends JPanel {

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat(
            "HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat(
            "dd.MM.yy", Locale.ENGLISH);

    /**
     *
     */
    // private final UIBookingSystemBookingEquipmentViewPanel
    // bookingSystemEquipmentViewPanel;
    private final UIBookingSystemEquipmentControlPanel bookingSystemEquipmentControlPanel;
    // private final UIBookingSystemJTableBookings bookingSystemJTable;
    // private BookingTableModel model;

    private final JTable bookingSystemEquipmentTable;
    private EquipmentTableModel equipmentTableModel;

    public UIBookingSystemEquipmentPanel() {
        // bookingSystemJTable = new
        // UIBookingSystemJTableBookings(bookingSystemModel);

        bookingSystemEquipmentTable = new JTable(equipmentTableModel);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // bookingSystemAdminViewPanel = new UIBookingSystemAdminViewPanel();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.9;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane jScrollPane = new JScrollPane(bookingSystemEquipmentTable);
        gbc.gridheight = 1;
        this.add(jScrollPane, gbc);

        bookingSystemEquipmentControlPanel = new UIBookingSystemEquipmentControlPanel();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = .5;
        gbc.weighty = .1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(bookingSystemEquipmentControlPanel, gbc);
//
//        JScrollPane jScrollPane1 = new JScrollPane();// bookingSystemAdminViewPanel);
//        gbc.gridheight = 2;
//        gbc.gridy = 0;
//        gbc.gridx = 1;
//        gbc.weightx = .8;
//        gbc.weighty = 1;
//        gbc.anchor = GridBagConstraints.PAGE_START;
//        gbc.fill = GridBagConstraints.BOTH;
//        this.add(jScrollPane1, gbc);
    }

    public void setJTableModel(EquipmentTableModel equipmentTableModel) {
        this.equipmentTableModel = equipmentTableModel;
        this.bookingSystemEquipmentTable.setModel(this.equipmentTableModel);
        bookingSystemEquipmentTable.getColumn("Equipment ID").setMinWidth(90);
        bookingSystemEquipmentTable.getColumn("Equipment ID").setMaxWidth(90);
        bookingSystemEquipmentTable.getColumn("Equipment ID")
                .setPreferredWidth(90);
        bookingSystemEquipmentTable.getColumn("Usage").setMinWidth(50);
        bookingSystemEquipmentTable.getColumn("Usage").setMaxWidth(50);
        bookingSystemEquipmentTable.getColumn("Usage").setPreferredWidth(50);
    }

    public JTable getBookingSystemEquipmentTable() {
        return bookingSystemEquipmentTable;
    }

    public EquipmentTableModel getTableModel() {
        return this.equipmentTableModel;
    }

    public void changeSelection(int row) {
        bookingSystemEquipmentTable.changeSelection(row, 0, false, false);
        bookingSystemEquipmentTable.repaint();
    }

    public int selectedRowCount() {
        return bookingSystemEquipmentTable.getSelectedRowCount();
    }

    public int rowViewIndexToModel(int row) {
        return bookingSystemEquipmentTable.convertRowIndexToModel(row);
    }

    public Object getValueAt(int row, int column) {
        return bookingSystemEquipmentTable.getValueAt(row, column);
    }

    public int returnRowIndexForValue(final int j) {
        for (int i = 0; i <= bookingSystemEquipmentTable.getRowCount(); i++)
            if (bookingSystemEquipmentTable.getValueAt(i, 0).equals(j))
                return i;
        return -1;
    }

    public List<Integer> getSelectedRows() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < bookingSystemEquipmentTable.getSelectedRows().length; i++) {
            integerList.add(rowViewIndexToModel(bookingSystemEquipmentTable
                    .getSelectedRows()[i]));
        }
        return integerList;
    }

    public int getSelectedRow() {
        return bookingSystemEquipmentTable.getSelectedRow();
    }

    public UIBookingSystemEquipmentControlPanel getBookingSystemEquipmentControlPanel() {
        return this.bookingSystemEquipmentControlPanel;
    }

}
