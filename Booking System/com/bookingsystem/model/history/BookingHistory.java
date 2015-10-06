package com.bookingsystem.model.history;

import com.bookingsystem.helpers.JSONObjectDecoder;
import com.bookingsystem.helpers.Pair;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 13/08/2015
 */
public class BookingHistory {
    private List<Pair<Booking, Log>> equipmentLogPairList;

    public BookingHistory() {
        equipmentLogPairList = new ArrayList<>();
    }

    public void populatePair(List<Log> logList) {
        for (int i = 0; i<logList.size();i++) {
            equipmentLogPairList.add(new Pair<Booking, Log>(JSONObjectDecoder.fetchBooking(logList.get(i).getIdPlayedWith()), logList.get(i)));
        }
    }

    public void addPair(Booking b, Log l) {
        equipmentLogPairList.add(new Pair<Booking, Log>(b,l));
    }

    public void removePair(Booking b, Log l) {
        equipmentLogPairList.stream().filter(bookingLogPair -> bookingLogPair.getLeftItem() == b && bookingLogPair.getRightItem() == l).forEach(equipmentLogPairList::remove);
    }

    public Booking getBooking(Log l) {
        Booking b = null;
        for (Pair<Booking, Log> bookingLogPair : equipmentLogPairList) {
            if (bookingLogPair.getRightItem() == l) {
                b = bookingLogPair.getLeftItem();
            }
        }
        return b;
    }
}

