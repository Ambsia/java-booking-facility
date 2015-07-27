package com.bookingsystem.model;

import java.util.Date;

/**
 * Author: [Alex] on [$Date]
 */
public final class Log {

    private final String eventLogged;
    private final Date dateAndTimeOfEvent;
    private final String classEvent;
    private final int accountIDLoggedIn;
    private int logID;
    private int bookingIDInserted;
    private int bookingIDEdited;
    private int bookingIDDeleted;
    private int accountIDCreated;
    private int accountIDDeleted;
    private int idPlayedWith;
    public Log(String eventLogged, String classEvent, Date dateAndTimeOfEvent) {
        this.eventLogged = eventLogged;
        this.classEvent = classEvent;
        this.dateAndTimeOfEvent = dateAndTimeOfEvent;

        this.logID = 0;
        this.bookingIDDeleted = -1;
        this.bookingIDEdited = -1;
        this.bookingIDInserted = -1;
        this.accountIDCreated = -1;
        this.accountIDDeleted = -1;
        this.accountIDLoggedIn = -1;
        this.idPlayedWith = 0;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
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

    // --Commented out by Inspection START (21/06/2015 00:49):
    // public int getAccountIDLoggedIn() {
    // return accountIDLoggedIn;
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:49)

    public int getIdPlayedWith() {
        return idPlayedWith;
    }

    public void setIdPlayedWith(int idPlayedWith) {
        this.idPlayedWith = idPlayedWith;
    }

    public int getAccountIDDeleted() {
        return accountIDDeleted;
    }

    public void setAccountIDDeleted(int accountIDDeleted) {
        this.accountIDDeleted = accountIDDeleted;
    }

    public int getBookingIDInserted() {
        return bookingIDInserted;
    }

    public void setBookingIDInserted(int bookingIDInserted) {
        this.bookingIDInserted = bookingIDInserted;
    }

    public int getBookingIDEdited() {
        return bookingIDEdited;
    }

    public void setBookingIDEdited(int bookingIDEdited) {
        this.bookingIDEdited = bookingIDEdited;
    }

    public int getBookingIDDeleted() {
        return bookingIDDeleted;
    }

    public void setBookingIDDeleted(int bookingIDDeleted) {
        this.bookingIDDeleted = bookingIDDeleted;
    }

    public int getAccountIDCreated() {
        return accountIDCreated;
    }

    public void setAccountIDCreated(int accountIDCreated) {
        this.accountIDCreated = accountIDCreated;
    }

    @Override
    public String toString() {
        return "Log{" + "eventLogged='" + eventLogged + '\''
                + ", dateAndTimeOfEvent=" + dateAndTimeOfEvent
                + ", classEvent='" + classEvent + '\'' + ", idPlayedWith="
                + idPlayedWith + '}';
    }

}
