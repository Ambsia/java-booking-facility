package com.bookingsystem.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;



public class Booking {
	private int bookingID;
	private String bookingName;
	private String bookingType;
	private Time bookingTime;
	private Date bookingDate;
	
	private List<Equipment> requiredEquipment;
	private String bookingHolder;

	
	public Booking(int bookingID, String bookingName, String bookingType, Time bookingTime,
				   Date bookingDate, List<Equipment> requiredEquipment,
				   String bookingHolder) {
		this.bookingID = bookingID;
		this.bookingName = bookingName;
		this.bookingType = bookingType;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.requiredEquipment = requiredEquipment; // this list will be passed when the booking is made;
		this.bookingHolder = bookingHolder;
	}
	
	public boolean Validation() {
		return (this.bookingName.isEmpty() || this.bookingType.isEmpty() || 
			    this.bookingTime.isEmpty() || this.bookingDate.isEmpty() || 
				this.bookingHolder.isEmpty());
	}

}
