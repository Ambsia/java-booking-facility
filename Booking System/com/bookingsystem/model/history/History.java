package com.bookingsystem.model.history;

import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;

/**
 * Created by Alex on 13/08/2015
 */
public class History {

    public BookingHistory bookingHistory;
    public EquipmentHistory equipmentHistory;
    public AccountHistory accountHistory;

    public History(LoggerBusinessLayer loggerBusinessLayer) {
        bookingHistory = new BookingHistory();
        equipmentHistory = new EquipmentHistory();
        accountHistory = new AccountHistory();

//        bookingHistory.addRequiredLogs(loggerBusinessLayer.getBookingLogs());
//        equipmentHistory.addRequiredLogs(loggerBusinessLayer.getEquipmentLogs());
//        accountHistory.addRequiredLogs(loggerBusinessLayer.getAccountLogs());
    }


    public AccountHistory getAccountHistory() {
        return accountHistory;
    }

    public BookingHistory getBookingHistory() {
        return bookingHistory;
    }

    public EquipmentHistory getEquipmentHistory() {
        return equipmentHistory;
    }

}
