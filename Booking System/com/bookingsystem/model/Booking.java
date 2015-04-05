package  com.bookingsystem.model;

import java.security.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


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
	private Equipment bookingEquipment;

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

	public java.sql.Time getBookingStartTimeInSQLFormat()  {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getBookingStartTime());

		java.sql.Time sqlTime=new java.sql.Time(calendar.getTime().getTime());
		return sqlTime;
	}

	public java.sql.Time getBookingCollectionTimeInSQLFormat()  {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getBookingCollectionTime());

		java.sql.Time sqlTime=new java.sql.Time(calendar.getTime().getTime());
		return sqlTime;
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

	public void setBookingEquipment(Equipment bookingEquipment) {
		this.requiredEquipment = bookingEquipment;
	}
}
