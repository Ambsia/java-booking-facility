package com.bookingsystem.controller;

import com.bookingsystem.model.history.History;
import org.apache.commons.collections.IteratorUtils;

import com.bookingsystem.controller.handler.AccountHandler;
import com.bookingsystem.controller.handler.ArchiveHandler;
import com.bookingsystem.controller.handler.BookingHandler;
import com.bookingsystem.controller.handler.EquipmentHandler;
import com.bookingsystem.controller.handler.Handler;
import com.bookingsystem.controller.handler.LoginHandler;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
import com.bookingsystem.model.businessmodel.ArchiveBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.model.tablemodel.AccountTableModel;
import com.bookingsystem.model.tablemodel.ArchiveTableModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.model.tablemodel.EquipmentTableModel;
import com.bookingsystem.model.tablemodel.LogTableModel;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UILoginPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemAdminControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemArchiveControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemEquipmentControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemAdminPanel;
import com.bookingsystem.view.panes.UIBookingSystemArchivePanel;
import com.bookingsystem.view.panes.UIBookingSystemBookingPanel;
import com.bookingsystem.view.panes.UIBookingSystemEquipmentPanel;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;

public class BookingSystemController {
    // account not instantiated until logged in or created!
    @SuppressWarnings("unchecked")
    public BookingSystemController(BookingSystemUILoader view,
                                   BookingBusinessLayer bookingBusinessLayer,
                                   AccountBusinessLayer accountBusinessLayer,
                                   LoggerBusinessLayer loggerBusinessLayer,
                                   AccountManagementBusinessLayer accountManagementBusinessLayer,
                                   ArchiveBusinessLayer archiveBusinessLayer) {
        History history = new History(loggerBusinessLayer);

        view.showLoginPanel();

        UIBookingSystemTabbedPane bookingSystemTabbedPane = view
                .getBookingSystemTabbedPane();
        //
        UIBookingSystemArchivePanel bookingArchivePanel = bookingSystemTabbedPane
                .getBookingSystemArchive();
        UIBookingSystemBookingPanel bookingSystemPanel = bookingSystemTabbedPane
                .getBookingSystemPanel();
        UIBookingSystemAdminPanel bookingSystemAdminPanel = bookingSystemTabbedPane
                .getBookingSystemAdminPanel();
        UIBookingSystemEquipmentPanel bookingSystemEquipmentPanel = bookingSystemTabbedPane
                .getBookingSystemEquipmentPanel();
        //
        UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel = bookingSystemAdminPanel
                .getBookingSystemAdminControlPanel();
        UIBookingSystemArchiveControlPanel bookingSystemArchiveControlPanel = bookingArchivePanel
                .getUiBookingSystemArchiveControlPanel();
        UIBookingSystemBookingControlPanel bookingSystemControlPanel = bookingSystemPanel
                .getBookingSystemControlPanel();
        UIBookingSystemEquipmentControlPanel bookingSystemEquipmentControlPanel = bookingSystemEquipmentPanel
                .getBookingSystemEquipmentControlPanel();
        //
        UILoginPanel loginPanel = view.getLoginPanel();
        // Create All Business Lists Now//
        bookingBusinessLayer.getEquipments().populateEquipmentList();
        bookingBusinessLayer.populateBookingListOnLoad();
        accountManagementBusinessLayer.getAllAccounts();
        // Create All Business Lists Now//
        //
        // All Table Models Here//
        if (IteratorUtils.toList(bookingBusinessLayer.getEquipments()
                .iterator()) == null) {
        }
        EquipmentTableModel equipmentTableModel = new EquipmentTableModel(
                IteratorUtils.toList(bookingBusinessLayer.getEquipments()
                        .iterator()));
        BookingTableModel bookingTableModel = new BookingTableModel(
                IteratorUtils.toList(bookingBusinessLayer.iterator())); // will
        // a
        ArchiveTableModel archiveTableModel = new ArchiveTableModel(
                IteratorUtils.toList(bookingBusinessLayer.getArchivedBookings()
                        .iterator()));
        LogTableModel logTableModel = new LogTableModel(
                IteratorUtils.toList(loggerBusinessLayer.iterator()));
        AccountTableModel accountTableModel = new AccountTableModel(
                IteratorUtils.toList(accountManagementBusinessLayer.iterator()));
        // All Table Models Here\\
        //
        // Send Table Models Here//
        bookingSystemPanel.setJTableModel(bookingTableModel);
        bookingArchivePanel.setJTableModel(archiveTableModel);
        bookingSystemAdminPanel.setJTableModel(accountTableModel);
        bookingSystemAdminPanel.getBookingSystemAdminViewPanel()
                .setJTableModel(logTableModel);
        bookingSystemEquipmentPanel.setJTableModel(equipmentTableModel);
        // Send Table Models Here\\
        //

        Handler handler = new Handler(accountBusinessLayer,
                accountManagementBusinessLayer, bookingBusinessLayer,
                loggerBusinessLayer, view, archiveBusinessLayer,history);
        LoginHandler loginHandler = new LoginHandler(handler);
        BookingHandler bookingHandler = new BookingHandler(handler);
        AccountHandler accountHandler = new AccountHandler(handler);
        ArchiveHandler archiveHandler = new ArchiveHandler(handler);
        EquipmentHandler equipmentHandler = new EquipmentHandler(handler);
        // /
        loginPanel.addSubmitListener(loginHandler);
        loginPanel.addClearListener(loginHandler);
        // /
        bookingSystemControlPanel.addListeners(bookingHandler);
        //
        view.getMenuBarLoader().addImportOptionListener(bookingHandler);
        bookingSystemAdminControlPanel.addListeners(accountHandler);
        //
        bookingSystemArchiveControlPanel.addListeners(archiveHandler);
        //
        bookingSystemEquipmentControlPanel.addListeners(equipmentHandler);

        bookingSystemEquipmentControlPanel
                .getBookingSystemEquipmenttEditPanel()
                .addResetUsageActionListener(equipmentHandler);

    }
}
