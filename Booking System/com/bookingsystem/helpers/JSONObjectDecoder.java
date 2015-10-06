package com.bookingsystem.helpers;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;

import java.util.Date;

/**
 * Created by Alex on 13/08/2015
 */
public class JSONObjectDecoder {

    public static Booking fetchBooking(int idPlayedWith) {
        return new Booking(1, "Friday", new Date(), new Date(), new Date(),
                "", "", new Equipment("equipt"));
    }

}
