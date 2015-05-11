package com.bookingsystem.controller.handler;

import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;

import java.awt.event.ActionListener;

/**
 * Created by Alex on 04/05/2015.
 */
public class Handler {
    //we will initially call handler passing all business logic over, then we will start calling specific logic classes
    private AccountBusinessLayer accountBusinessLayer;
    private  AccountManagementBusinessLayer accountManagementBusinessLayer;
    private BookingBusinessLayer bookingBusinessLayer;
    private  LoggerBusinessLayer loggerBusinessLayer;
    private BookingSystemUILoader view;

    public Handler(AccountBusinessLayer accountBusinessLayer, AccountManagementBusinessLayer accountManagementBusinessLayer,
                   BookingBusinessLayer bookingBusinessLayer, LoggerBusinessLayer loggerBusinessLayer, BookingSystemUILoader view) {
        this.accountBusinessLayer = accountBusinessLayer;
        this.accountManagementBusinessLayer = accountManagementBusinessLayer;
        this.bookingBusinessLayer = bookingBusinessLayer;
        this.loggerBusinessLayer = loggerBusinessLayer;
        this.view = view;
    }

    public BookingSystemUILoader getView() {
        return view;
    }

    public AccountBusinessLayer getAccountBusinessLayer() {
        return accountBusinessLayer;
    }

    public AccountManagementBusinessLayer getAccountManagementBusinessLayer() {
        return accountManagementBusinessLayer;
    }

    public BookingBusinessLayer getBookingBusinessLayer() {
        return bookingBusinessLayer;
    }

    public LoggerBusinessLayer getLoggerBusinessLayer() {
        return loggerBusinessLayer;
    }



    //will be the abstract class for all handlers, implementing interfaces and generic methods here

    //all business logic delegated from here
    //all view controls also delegated from here

}
