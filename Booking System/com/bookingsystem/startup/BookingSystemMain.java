package com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
import com.bookingsystem.model.businessmodel.ArchiveBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.EquipmentBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;

final class BookingSystemMain {

    private final BookingSystemUILoader view;

    private final BookingSystemController controller;

    private final BookingBusinessLayer bookingBusinessLayer;

    private final ArchiveBusinessLayer archiveBusinessLayer;

    private final EquipmentBusinessLayer equipments;

    private BookingSystemMain() {
        this.view = new BookingSystemUILoader();
        equipments = new EquipmentBusinessLayer();

        this.bookingBusinessLayer = new BookingBusinessLayer(equipments);
        LoggerBusinessLayer loggerBusinessLayer = new LoggerBusinessLayer();
        AccountBusinessLayer accountBusinessLayer = new AccountBusinessLayer();
        AccountManagementBusinessLayer accountManagementBusinessLayer = new AccountManagementBusinessLayer();
        this.archiveBusinessLayer = new ArchiveBusinessLayer();
        this.controller = new BookingSystemController(view,
                bookingBusinessLayer, accountBusinessLayer,
                loggerBusinessLayer, accountManagementBusinessLayer,
                archiveBusinessLayer);
    }

    public static void main(String[] args) {
        new BookingSystemMain();
    }

    public BookingSystemUILoader getView() {
        return view;
    }

    public BookingSystemController getController() {
        return controller;
    }

    public BookingBusinessLayer getModel() {
        return bookingBusinessLayer;
    }
}
