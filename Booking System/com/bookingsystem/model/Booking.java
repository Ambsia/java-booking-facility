package  com.bookingsystem.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public final class Booking  {
	private int bookingID;
	private String bookingDay;
	private String bookingLocation;
	private Date bookingStartTime;
	private Date bookingCollectionTime;
	private Date bookingDate;
	private int weeksRecurring;
	private boolean isRecurringBooking;
	private Equipment requiredEquipment;
	private String bookingHolder;
	private int bookingIndex;
	private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);

	// --Commented out by Inspection (21/06/2015 00:49):Log bookingLogger;

	private boolean bookingCompleted;

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

// --Commented out by Inspection START (21/06/2015 00:49):
//	public void setRequiredEquipment(Equipment requiredEquipment) {
//		this.requiredEquipment = requiredEquipment;
//	}
// --Commented out by Inspection STOP (21/06/2015 00:49)

	public String getBookingHolder() {
		return bookingHolder;
	}
	
	public void setIsRecurring(boolean isRecurringBooking) {
		this.isRecurringBooking = isRecurringBooking;
	}
	
	public boolean getIsRecurringBooking() {
		return this.isRecurringBooking;
	}
	
	
	public int getWeeksRecurring() {
		return this.weeksRecurring;
	}
	
	public void setWeeksRecurring(int weeksRecurring) {
		this.weeksRecurring = weeksRecurring;
	}

	public boolean getBookingCompleted() { return bookingCompleted; }

	public void setBookingCompleted(boolean bookingCompleted) { this.bookingCompleted = bookingCompleted; }

	public void setBookingHolder(String bookingHolder) {
		this.bookingHolder = bookingHolder;
	}

	public java.sql.Time getBookingStartTimeInSQLFormat()  {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getBookingStartTime());

		return new java.sql.Time(calendar.getTime().getTime());
	}

	public java.sql.Time getBookingCollectionTimeInSQLFormat()  {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getBookingCollectionTime());

		return new java.sql.Time(calendar.getTime().getTime());
	}

	public Booking(int bookingID, String bookingDay, Date bookingDate, Date bookingStartTime,
				Date bookingCollectionTime, String bookingLocation, String bookingHolder,
				Equipment requiredEquipment) {
		this.bookingID = bookingID;
		this.bookingDay = bookingDay;
		this.bookingDate = bookingDate;
		this.bookingStartTime = bookingStartTime;
		this.bookingCollectionTime = bookingCollectionTime;
		this.bookingLocation = bookingLocation;
		this.bookingHolder = bookingHolder;
		this.requiredEquipment = requiredEquipment; // this list will be passed when the booking is made
		this.bookingCompleted = false;
		this.bookingIndex =0;
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
				"bookingID=" + bookingID +
				", bookingDay='" + bookingDay + '\'' +
				", bookingDate='" + bookingDate + '\'' +
				", bookingStartTime='" + bookingStartTime + '\'' +
				", bookingCollectionTime='" + bookingCollectionTime + '\'' +
				", bookingLocation='" + bookingLocation + '\'' +
				", bookingHolder='" + bookingHolder + '\'' +
				", requiredEquipment=" + requiredEquipment + '\'' +
				", bookingCompleted=" + bookingCompleted +
				'}';
	}

	public void setBookingEquipment(Equipment bookingEquipment) {
		this.requiredEquipment = bookingEquipment;
	}

	public boolean isBeforeToday() {
		Calendar calendar = Calendar.getInstance();
		Date rightNow = calendar.getTime();
		if (bookingDate != null) {
			if (BOOKING_DATE_FORMAT.format(bookingDate).equals(BOOKING_DATE_FORMAT.format(rightNow))) {
				try {
					return BOOKING_TIME_FORMAT.parse(BOOKING_TIME_FORMAT.format(rightNow)).after(BOOKING_TIME_FORMAT.parse(BOOKING_TIME_FORMAT.format(bookingStartTime))) &&
							BOOKING_TIME_FORMAT.parse(BOOKING_TIME_FORMAT.format(rightNow)).after(BOOKING_TIME_FORMAT.parse(BOOKING_TIME_FORMAT.format(bookingCollectionTime)));
				} catch (ParseException e) {
					return false;
				}
			} else {
				return bookingDate.before(new Date());
			}
		} else {
			return false;
		}
	}

// --Commented out by Inspection START (21/06/2015 00:49):
//	public int getBookingIndex() {
//		return bookingIndex;
//	}
// --Commented out by Inspection STOP (21/06/2015 00:49)

	public void setBookingIndex(int bookingIndex) {
		this.bookingIndex = bookingIndex;
	}
	
	public String getDateToString() {
			Calendar date1 = Calendar.getInstance();
			date1.set(Calendar.AM_PM, Calendar.AM);
			date1.set(Calendar.DAY_OF_MONTH, 25);
			date1.set(Calendar.MONTH, 11);
			date1.set(Calendar.HOUR, 0);
			date1.set(Calendar.MINUTE, 0);
			date1.set(Calendar.SECOND, 0);
			date1.set(Calendar.MILLISECOND, 0);
			String date11;
			
			if (BOOKING_DATE_FORMAT.format(this.bookingDate).equals(BOOKING_DATE_FORMAT.format(date1.getTime()))) {
				date11 = "Unknown";
			} else {
				date11 = BOOKING_DATE_FORMAT.format(this.bookingDate);
			}
			return date11;
	    
	   }
	
	public String getTimeToString() {
	Calendar date1 = Calendar.getInstance();
	date1.set(Calendar.AM_PM, Calendar.AM);
	date1.set(Calendar.DAY_OF_MONTH, 25);
	date1.set(Calendar.MONTH, 11);
	date1.set(Calendar.HOUR, 0);
	date1.set(Calendar.MINUTE, 0);
	date1.set(Calendar.SECOND, 0);
	date1.set(Calendar.MILLISECOND, 0);
	String time;
	if (BOOKING_TIME_FORMAT.format(this.bookingStartTime).equals(BOOKING_TIME_FORMAT.format(date1.getTime()))) {
		time= "Unknown";
	} else {
		time= BOOKING_TIME_FORMAT.format(this.bookingStartTime) + "-" + BOOKING_TIME_FORMAT.format(this.bookingCollectionTime);
	}
	return time;
}
}
