package com.bookingsystem.view.panes;

import com.bookingsystem.model.tablemodel.ArchiveTableModel;
import com.bookingsystem.view.panelparts.UIBookingSystemArchiveViewPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemArchiveControlPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Alex on 24/05/2015
 */
public class UIBookingSystemArchivePanel extends JPanel {
    /**
     *
     */

    private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat(
            "HH:mm", Locale.ENGLISH);
    private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat(
            "dd.MM.yy", Locale.ENGLISH);
    private static final long serialVersionUID = -4584229791330483547L;
    private final JTable bookingSystemJTable;
    private final UIBookingSystemArchiveControlPanel uiBookingSystemArchiveControlPanel;
    private final UIBookingSystemArchiveViewPanel uiBookingSystemArchiveViewPanel;
    private ArchiveTableModel archiveTableModel;

    public UIBookingSystemArchivePanel() {
        bookingSystemJTable = new JTable(archiveTableModel);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        // bookingSystemJTable.getColumn("Booking ID").setMinWidth(0);
        // bookingSystemJTable.getColumn("Booking ID").setMaxWidth(0);
        // bookingSystemJTable.getColumn("Booking ID").setPreferredWidth(0);
        // bookingSystemJTable.getColumn("Day").setMaxWidth(85);
        // bookingSystemJTable.getColumn("Date").setMaxWidth(80);
        JScrollPane jScrollPane = new JScrollPane(bookingSystemJTable);
        gbc.gridheight = 2;
        this.add(jScrollPane, gbc);

        uiBookingSystemArchiveViewPanel = new UIBookingSystemArchiveViewPanel();
        gbc.gridheight = 1;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = .4;
        gbc.weighty = .9;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(uiBookingSystemArchiveViewPanel, gbc);

        uiBookingSystemArchiveControlPanel = new UIBookingSystemArchiveControlPanel();
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.weightx = .4;
        gbc.weighty = .1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(uiBookingSystemArchiveControlPanel, gbc);
    }

    public UIBookingSystemArchiveViewPanel getUiBookingSystemArchiveViewPanel() {
        return uiBookingSystemArchiveViewPanel;
    }

    public UIBookingSystemArchiveControlPanel getUiBookingSystemArchiveControlPanel() {
        return this.uiBookingSystemArchiveControlPanel;
    }

    public ArchiveTableModel getJTableModel() {
        return this.archiveTableModel;
    }

    // --Commented out by Inspection START (21/06/2015 00:50):
    // public void removeAllBookings() {
    // this.removeAll();
    // this.archiveTableModel.fireTableDataChanged();
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:50)

    public void setJTableModel(ArchiveTableModel archiveTableModel) {
        this.archiveTableModel = archiveTableModel;
        this.bookingSystemJTable.setModel(this.archiveTableModel);
        this.bookingSystemJTable.setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(
                archiveTableModel);
        bookingSystemJTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sorter.setComparator(2, new Comparator<Object>() {
            @Override
            public int compare(Object arg0, Object arg1) {
                try {
                    if (arg0.equals("Unknown") && !arg1.equals("Unknown")) {
                        return -1;
                    } else if (arg1.equals("Unknown")
                            && !arg0.equals("Unknown")) {
                        return 1;
                    } else if (arg0.equals("Unknown") && arg1.equals("Unknown")) {
                        return 0;
                    } else {
                        return BOOKING_DATE_FORMAT.parse((String) arg0)
                                .compareTo(
                                        (BOOKING_DATE_FORMAT
                                                .parse((String) arg1)));
                    }
                } catch (ParseException p) {
                    System.out.println("time");
                    return 0;
                }
            }
        });

        sorter.setComparator(3, new Comparator<Object>() {
            @Override
            public int compare(Object arg0, Object arg1) {
                Date d1;
                Date d2;

                if (arg0.equals("Unknown") && !arg1.equals("Unknown")) {
                    return -1;
                } else if (arg1.equals("Unknown") && !arg0.equals("Unknown")) {
                    return 1;
                } else if (arg0.equals("Unknown") && arg1.equals("Unknown")) {
                    return 0;
                } else {

                    String[] s1ARRAY = ((String) arg0).split("-"); // first
                    // argument
                    // split
                    String s1 = s1ARRAY[0];

                    String[] s2ARRAY = ((String) arg1).split("-"); // second
                    // argument
                    String s2 = s2ARRAY[0];

                    try {
                        d1 = BOOKING_TIME_FORMAT.parse(s1);
                        d2 = BOOKING_TIME_FORMAT.parse(s2);
                    } catch (ParseException e) {
                        Calendar date = Calendar.getInstance();
                        date.set(Calendar.AM_PM, Calendar.AM);
                        date.set(Calendar.HOUR, 0);
                        date.set(Calendar.MINUTE, 0);
                        date.set(Calendar.SECOND, 0);
                        date.set(Calendar.MILLISECOND, 0);
                        d1 = date.getTime();
                        d2 = date.getTime();
                    }

                    return d1.compareTo(d2);

                }
            }
        });

        sorter.setSortKeys(sortKeys);
        this.archiveTableModel.fireTableDataChanged();
    }
    // public ArrayList<String> getCurrentlySelectedRowAsStringArrayList() {
    // return bookingSystemJTable.getSelectedRowAsStringArrayList();
    // }
    //
    // public void addBookingToList(Booking booking) {
    // bookingSystemJTable.addRowToList(booking);
    // }

    // public void addBookingsToList(ArrayList<Booking> listOfBookings) {
    // ArrayList<Object> objectArrayList = new ArrayList<>();
    // for (Booking b : listOfBookings) {
    // objectArrayList.add(b);
    // }
    // bookingSystemJTable.addArrayOfRowsToList(objectArrayList);
    // }
    //
    // public Booking getBookingFromList(int bookingId) {
    // return (Booking) bookingSystemJTable.getRowFromList(bookingId);
    // }
    //
    // public int getIndexOfSelectedRow() {
    // return bookingSystemJTable.getSelectedRow();
    // }
    //
    // public int getIDOfSelectedRow() {
    // return bookingSystemJTable.getIDOfSelectedRow();
    // }
    //
    // public int getRowCountOfTable() {
    // return bookingSystemJTable.getRowCount();
    // }
    //
    // public void replaceBookingInList(Booking newBooking) {
    // bookingSystemJTable.replaceRowInList(newBooking);
    // }
    //
    // public void removeBookingFromTable() {
    // bookingSystemJTable.removeRowFromList();
    // }
    //
    // public void removeAllBookings() {
    // bookingSystemJTable.removeAllRowsFromList();
    // }

}
