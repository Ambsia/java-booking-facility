package com.bookingsystem.model;

import java.util.Date;

/**
 * Author: [Alex] on [$Date]
 */
public final class Log {

    private String eventLogged;
    private Date dateAndTimeOfEvent;
    private String classEvent;

    private int bookingIDInserted;
    private int bookingIDEdited;
    private int bookingIDDeleted;
    private int accountIDCreated;
    private int accountIDDeleted;

    Date today;

    public Log(String eventLogged, String classEvent, Date dateAndTimeOfEvent) {
        today = new Date();
        this.eventLogged = eventLogged;
        this.classEvent = classEvent;
        this.dateAndTimeOfEvent = dateAndTimeOfEvent;

        this.bookingIDDeleted = -1;
        this.bookingIDEdited = -1;
        this.bookingIDInserted = -1;
        this.accountIDCreated = -1;
        this.accountIDDeleted = -1;
    }

    public Date getDateAndTimeOfEvent() {
        return dateAndTimeOfEvent;
    }

    public String getEventLogged() {
        return eventLogged;
    }

    public String getClassEvent() {
        return classEvent;
    }

    public void setAccountIDDeleted(int accountIDDeleted) {
        this.accountIDDeleted = accountIDDeleted;
    }

    public void setBookingIDInserted(int bookingIDInserted) {
        this.bookingIDInserted = bookingIDInserted;
    }

    public void setBookingIDEdited(int bookingIDEdited) {
        this.bookingIDEdited = bookingIDEdited;
    }

    public void setBookingIDDeleted(int bookingIDDeleted) {
        this.bookingIDDeleted = bookingIDDeleted;
    }

    public void setAccountIDCreated(int accountIDCreated) {
        this.accountIDCreated = accountIDCreated;
    }

    public int getAccountIDDeleted() {
        return accountIDDeleted;
    }

    public int getBookingIDInserted() {
        return bookingIDInserted;
    }

    public int getBookingIDEdited() {
        return bookingIDEdited;
    }

    public int getBookingIDDeleted() {
        return bookingIDDeleted;
    }

    public int getAccountIDCreated() {
        return accountIDCreated;
    }



}
