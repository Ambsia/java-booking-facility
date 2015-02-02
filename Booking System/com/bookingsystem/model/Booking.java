package  com.bookingsystem.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Booking {
	private int bookingID;
	private String bookingName;
	private String bookingType;
	private String bookingTime;
	private String bookingDate;
	
	private List<Equipment> requiredEquipment;
	private String bookingHolder;

	
	public Booking(int bookingID, String bookingName, String bookingType, String bookingTime,
				   String bookingDate, List<Equipment> requiredEquipment,
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
		try {
			DateFormat format = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
			Date dateConvert = format.parse(this.bookingDate);

		} catch (ParseException e) {

		}
		return (this.bookingName.isEmpty() || this.bookingType.isEmpty() || 
			    this.bookingTime.isEmpty() || this.bookingDate.isEmpty() || 
				this.bookingHolder.isEmpty());
	}

	public int getBookingID() {
		return bookingID;
	}

	public String getBookingType() {
		return bookingType;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public List<Equipment> getRequiredEquipment() {
		return requiredEquipment;
	}

	public String getBookingHolder() {
		return bookingHolder;
	}

	public void setBookingName(String bookingName) {
		this.bookingName = bookingName;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public void setRequiredEquipment(List<Equipment> requiredEquipment) {
		this.requiredEquipment = requiredEquipment;
	}

	public void setBookingHolder(String bookingHolder) {
		this.bookingHolder = bookingHolder;
	}

	@Override
	public String toString() {
		return "Booking{" +
				"bookingID=" + bookingID +
				", bookingName='" + bookingName + '\'' +
				", bookingType='" + bookingType + '\'' +
				", bookingTime='" + bookingTime + '\'' +
				", bookingDate='" + bookingDate + '\'' +
				", requiredEquipment=" + requiredEquipment +
				", bookingHolder='" + bookingHolder + '\'' +
				'}';
	}
}
