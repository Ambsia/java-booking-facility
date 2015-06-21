package com.bookingsystem.controller;

import com.bookingsystem.controller.handler.*;
import com.bookingsystem.model.businessmodel.*;
import com.bookingsystem.model.tablemodel.AccountTableModel;
import com.bookingsystem.model.tablemodel.ArchiveTableModel;
import com.bookingsystem.model.tablemodel.BookingTableModel;
import com.bookingsystem.model.tablemodel.LogTableModel;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UILoginPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemAdminControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemArchiveControlPanel;
import com.bookingsystem.view.panelparts.controlpanes.UIBookingSystemBookingControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemAdminPanel;
import com.bookingsystem.view.panes.UIBookingSystemArchivePanel;
import com.bookingsystem.view.panes.UIBookingSystemBookingPanel;
import com.bookingsystem.view.panes.UIBookingSystemTabbedPane;
import org.apache.commons.collections.IteratorUtils;

public class BookingSystemController {
    // account not instantiated until logged in or created!
    @SuppressWarnings("unchecked")
	public BookingSystemController(BookingSystemUILoader view, BookingBusinessLayer bookingBusinessLayer, AccountBusinessLayer accountBusinessLayer, LoggerBusinessLayer loggerBusinessLayer, AccountManagementBusinessLayer accountManagementBusinessLayer, ArchiveBusinessLayer archiveBusinessLayer) {
        view.showLoginPanel();
        
        UIBookingSystemTabbedPane bookingSystemTabbedPane = view.getBookingSystemTabbedPane();
        //
        UIBookingSystemArchivePanel bookingArchivePanel = bookingSystemTabbedPane.getBookingSystemArchive();
        UIBookingSystemBookingPanel bookingSystemPanel = bookingSystemTabbedPane.getBookingSystemPanel();
        UIBookingSystemAdminPanel bookingSystemAdminPanel = bookingSystemTabbedPane.getBookingSystemAdminPanel();
        //
        UIBookingSystemAdminControlPanel bookingSystemAdminControlPanel = bookingSystemAdminPanel.getBookingSystemAdminControlPanel();
        UIBookingSystemArchiveControlPanel bookingSystemArchiveControlPanel = view.getBookingSystemTabbedPane().getBookingSystemArchive().getUiBookingSystemArchiveControlPanel();
        UIBookingSystemBookingControlPanel bookingSystemControlPanel = bookingSystemTabbedPane.getBookingSystemPanel().getBookingSystemControlPanel();
        //
        UILoginPanel loginPanel = view.getLoginPanel();
        //Create All Business Lists Now//
        bookingBusinessLayer.populateBookingListOnLoad();
        accountManagementBusinessLayer.getAllAccounts();
        //Create All Business Lists Now//
        //
        //All Table Models Here//
        BookingTableModel bookingTableModel = new BookingTableModel(IteratorUtils.toList(bookingBusinessLayer.iterator())); // will a
        ArchiveTableModel archiveTableModel = new ArchiveTableModel(IteratorUtils.toList(bookingBusinessLayer.getArchivedBookings().iterator()));
        LogTableModel logTableModel = new LogTableModel(IteratorUtils.toList(loggerBusinessLayer.iterator()));
		AccountTableModel accountTableModel = new AccountTableModel(IteratorUtils.toList(accountManagementBusinessLayer.iterator()));
        //All Table Models Here\\
        //
        //Send Table Models Here//
        bookingSystemPanel.setJTableModel(bookingTableModel);
        bookingArchivePanel.setJTableModel(archiveTableModel);
        bookingSystemAdminPanel.setJTableModel(accountTableModel);
        bookingSystemAdminPanel.getBookingSystemAdminViewPanel().setJTableModel(logTableModel);
        //Send Table Models Here\\
        //
        
        Handler handler = new Handler(accountBusinessLayer,accountManagementBusinessLayer,bookingBusinessLayer,loggerBusinessLayer,view, archiveBusinessLayer);
        LoginHandler loginHandler = new LoginHandler(handler);
        BookingHandler bookingHandler = new BookingHandler(handler);
        AccountHandler accountHandler = new AccountHandler(handler);
        ArchiveHandler archiveHandler = new ArchiveHandler(handler);
        ///
        loginPanel.addSubmitListener(loginHandler);
        loginPanel.addClearListener(loginHandler);
        ///
        bookingSystemControlPanel.addListeners(bookingHandler);
        //
        view.getMenuBarLoader().addImportOptionListener(bookingHandler);
        bookingSystemAdminControlPanel.addListeners(accountHandler);
        //
        bookingSystemArchiveControlPanel.addListeners(archiveHandler);
    }
}
