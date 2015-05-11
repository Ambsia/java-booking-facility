package com.bookingsystem.model;

import java.util.Calendar;
import java.util.Date;


public final class Booking {
    Log bookingLogger;
    private int bookingID;
    private String bookingDay;
    private String bookingLocation;
    private Date bookingStartTime;
    private Date bookingCollectionTime;
    private Date bookingDate;
    private Equipment requiredEquipment;
    private String bookingHolder;
    private Equipment bookingEquipment;

    public Booking(int bookingID, String bookingDay, Date bookingDate, Date bookingStartTime,
                   Date bookingCollectionTime, String bookingLocation, String bookingHolder,
                   Equipment requiredEquipment
    ) {
        this.bookingID = bookingID;
        this.bookingDay = bookingDay;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingCollectionTime = bookingCollectionTime;
        this.bookingLocation = bookingLocation;
        this.bookingHolder = bookingHolder;
        this.requiredEquipment = requiredEquipment; // this list will be passed when the booking is made
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingDay() {
        return bookingDay;
    }

    public void setBookingDay(String bookingDay) {
        this.bookingDay = bookingDay;
    }

    public String getBookingLocation() {
        return bookingLocation;
    }

    public void setBookingLocation(String bookingLocation) {
        this.bookingLocation = bookingLocation;
    }

    public Date getBookingCollectionTime() {
        return bookingCollectionTime;
    }

    public void setBookingCollectionTime(Date bookingCollectionTime) {
        this.bookingCollectionTime = bookingCollectionTime;
    }

    public Date getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(Date bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Equipment getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(Equipment requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    public String getBookingHolder() {
        return bookingHolder;
    }

    public void setBookingHolder(String bookingHolder) {
        this.bookingHolder = bookingHolder;
    }

    public java.sql.Time getBookingStartTimeInSQLFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getBookingStartTime());

        return new java.sql.Time(calendar.getTime().getTime());
    }

    public java.sql.Time getBookingCollectionTimeInSQLFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getBookingCollectionTime());

        return new java.sql.Time(calendar.getTime().getTime());
    }

    public boolean isValid() {
        return (!this.bookingDay.isEmpty() &&
                !this.bookingLocation.isEmpty() &&
                !this.bookingStartTime.toString().isEmpty() &&
                !this.bookingCollectionTime.toString().isEmpty() &&
                !this.bookingHolder.isEmpty());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID + "\n" +
                ", bookingDay='" + bookingDay + '\'' + "\n" +
                ", bookingDate='" + bookingDate + '\'' + "\n" +
                ", bookingStartTime='" + bookingStartTime + '\'' + "\n" +
                ", bookingCollectionTime='" + bookingCollectionTime + '\'' + "\n" +
                ", bookingLocation='" + bookingLocation + '\'' + "\n" +
                ", bookingHolder='" + bookingHolder + '\'' + "\n" +
                ", requiredEquipment=" + requiredEquipment +
                '}';
    }

    public void setBookingEquipment(Equipment bookingEquipment) {
        this.requiredEquipment = bookingEquipment;
    }
}
