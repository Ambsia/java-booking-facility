package  com.bookingsystem.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Booking  {
	private int bookingID;
	private String bookingDay;
	private String bookingLocation;
	private String bookingTime;
	private String bookingDate;
	
	private Equipment requiredEquipment;
	private String bookingHolder;

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

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
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

	public Booking(int bookingID, String bookingDay, String bookingDate, String bookingTime,
				   String bookingLocation, String bookingHolder, Equipment requiredEquipment
				   ) {
		this.bookingID = bookingID;
		this.bookingDay = bookingDay;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.bookingLocation = bookingLocation;
		this.bookingHolder = bookingHolder;
		this.requiredEquipment = requiredEquipment; // this list will be passed when the booking is made
	}
	
	public boolean Validation() {
		try {
			DateFormat format = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
			Date dateConvert = format.parse(this.bookingDate);

		} catch (ParseException e) {

		}
		return (this.bookingDay.isEmpty() || this.bookingLocation.isEmpty() ||
			    this.bookingTime.isEmpty() || this.bookingDate.isEmpty() || 
				this.bookingHolder.isEmpty());
	}

	@Override
	public String toString() {
		return "Booking{" +
				"bookingID=" + bookingID +
				", bookingDay='" + bookingDay + '\'' +
				", bookingLocation='" + bookingLocation + '\'' +
				", bookingTime='" + bookingTime + '\'' +
				", bookingDate='" + bookingDate + '\'' +
				", requiredEquipment=" + requiredEquipment +
				", bookingHolder='" + bookingHolder + '\'' +
				'}';
	}
}
