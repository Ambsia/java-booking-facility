package  com.bookingsystem.model;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public final class Booking  {
	private int bookingID;
	private String bookingDay;
	private String bookingLocation;
	private Date bookingStartTime;
	private Date bookingCollectionTime;
	private Date bookingDate;
	
	private Equipment requiredEquipment;
	private String bookingHolder;
	
	Logger bookingLogger;

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
		bookingLogger = new Logger("Booking Instantiated", null);
	}

	
	public boolean isValid() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		return (this.bookingDay.isEmpty() || this.bookingLocation.isEmpty() ||
			    simpleDateFormat.format(this.bookingStartTime).isEmpty() ||
				simpleDateFormat.format(this.bookingCollectionTime).isEmpty() ||
				this.bookingHolder.isEmpty()) ;
	}

	@Override
	public String toString() {
		return "Booking{" +
				"bookingID=" + bookingID +
				", bookingDay='" + bookingDay + '\'' +
				", bookingLocation='" + bookingLocation + '\'' +
				", bookingStartTime='" + bookingStartTime + '\'' +
				", bookingCollectionTime='" + bookingCollectionTime + '\'' +
				", bookingDate='" + bookingDate + '\'' +
				", requiredEquipment=" + requiredEquipment +
				", bookingHolder='" + bookingHolder + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Booking booking = (Booking) o;

		if (bookingID != booking.bookingID) return false;
		if (bookingDate != null ? !bookingDate.equals(booking.bookingDate) : booking.bookingDate != null) return false;
		if (bookingDay != null ? !bookingDay.equals(booking.bookingDay) : booking.bookingDay != null) return false;
		if (bookingHolder != null ? !bookingHolder.equals(booking.bookingHolder) : booking.bookingHolder != null)
			return false;
		if (bookingLocation != null ? !bookingLocation.equals(booking.bookingLocation) : booking.bookingLocation != null)
			return false;
		if (bookingLogger != null ? !bookingLogger.equals(booking.bookingLogger) : booking.bookingLogger != null)
			return false;
		if (bookingStartTime != null ? !bookingStartTime.equals(booking.bookingStartTime) : booking.bookingStartTime != null) return false;
		if (bookingCollectionTime != null ? !bookingCollectionTime.equals(booking.bookingCollectionTime) : booking.bookingCollectionTime != null) return false;
		if (requiredEquipment != null ? !requiredEquipment.equals(booking.requiredEquipment) : booking.requiredEquipment != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = bookingID;
		result = 31 * result + (bookingDay != null ? bookingDay.hashCode() : 0);
		result = 31 * result + (bookingLocation != null ? bookingLocation.hashCode() : 0);
		result = 31 * result + (bookingStartTime != null ? bookingStartTime.hashCode() : 0);
		result = 31 * result + (bookingDate != null ? bookingDate.hashCode() : 0);
		result = 31 * result + (requiredEquipment != null ? requiredEquipment.hashCode() : 0);
		result = 31 * result + (bookingHolder != null ? bookingHolder.hashCode() : 0);
		result = 31 * result + (bookingLogger != null ? bookingLogger.hashCode() : 0);
		return result;
	}
}
