package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingBusinessLayer extends BusinessLayer implements Iterable<Booking> {

	private final ArrayList<Booking> bookings;
	private int currentIndexOfBookingInList;

	public BookingBusinessLayer() {
		bookings = new ArrayList<>();
		currentIndexOfBookingInList = -1;
	}

	public void populateBookingListOnLoad() {
		bookings.clear();
		try {
			getDatabaseConnector().openConnection();
			getDatabaseConnector().createNewCallableStatement("{CALL spGetAllBookings}");
			ResultSet rs = getDatabaseConnector().executeQuery();
			while (rs.next()) {
				bookings.add(new Booking(rs.getInt(1), rs.getString(2).trim(), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7).trim(), new Equipment(rs.getString(8))));
			}
			getDatabaseConnector().closeConnection();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void insertBooking(Booking booking) {
		try {
			getDatabaseConnector().openConnection();
			getDatabaseConnector().createNewCallableStatement("{CALL spInsertBooking(?,?,?,?,?,?,?,?)}");
			try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement();) 
			{
				callableStatement.setString(1, booking.getBookingDay());
				callableStatement.setDate(2, convertFromJAVADateToSQLDate(booking.getBookingDate()));
				callableStatement.setTime(3,booking.getBookingStartTimeInSQLFormat());
				callableStatement.setTime(4,booking.getBookingCollectionTimeInSQLFormat());
				callableStatement.setString(5,booking.getBookingLocation());
				callableStatement.setString(6,booking.getBookingHolder());
				callableStatement.setString(7,booking.getRequiredEquipment().GetEquipmentName());
				callableStatement.registerOutParameter(8,Types.INTEGER);
				getDatabaseConnector().execute();
				booking.setBookingID(callableStatement.getInt(8));
				this.bookings.add(booking);
			}
			getDatabaseConnector().closeConnection();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}
	
	public void insertBookings(ArrayList<Booking> bookingList) {
		try {
		getDatabaseConnector().openConnection();
		getDatabaseConnector().createNewCallableStatement("{CALL spInsertBooking(?,?,?,?,?,?,?,?)}");
		try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement();) 
		{
			for( int i =0; i<bookingList.size();i++){
				callableStatement.setString(1, bookingList.get(i).getBookingDay());
				callableStatement.setDate(2, convertFromJAVADateToSQLDate(bookingList.get(i).getBookingDate()));
				callableStatement.setTime(3,bookingList.get(i).getBookingStartTimeInSQLFormat());
				callableStatement.setTime(4,bookingList.get(i).getBookingCollectionTimeInSQLFormat());
				callableStatement.setString(5,bookingList.get(i).getBookingLocation());
				callableStatement.setString(6,bookingList.get(i).getBookingHolder());
				callableStatement.setString(7,bookingList.get(i).getRequiredEquipment().GetEquipmentName());
				callableStatement.registerOutParameter(8,Types.INTEGER);
				getDatabaseConnector().execute();
				bookingList.get(i).setBookingID(callableStatement.getInt(8));
				this.bookings.add(bookingList.get(i));
			}
		}
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public ArrayList<Booking> findBookings(Booking bookingInformationKnown) { //show in a separate dialog, all users to save them to file
		ArrayList<Booking> foundBookings = new ArrayList<>();
		try {
			getDatabaseConnector().openConnection();
			getDatabaseConnector().createNewCallableStatement("{CALL spFindBooking(?,?,?,?,?,?,?)}");
			try(CallableStatement callableStatement = getDatabaseConnector().getCallableStatement();) {
				callableStatement.setString(1, bookingInformationKnown.getBookingDay());
				callableStatement.setDate(2, getDateSqlStatement(bookingInformationKnown));
				callableStatement.setTime(3,bookingInformationKnown.getBookingStartTimeInSQLFormat());
				callableStatement.setTime(4,bookingInformationKnown.getBookingCollectionTimeInSQLFormat());
				callableStatement.setString(5,bookingInformationKnown.getBookingLocation());
				callableStatement.setString(6, bookingInformationKnown.getBookingHolder());
				callableStatement.setString(7,bookingInformationKnown.getRequiredEquipment().GetEquipmentName());
				try (ResultSet rs = getDatabaseConnector().executeQuery();) {
					while (rs.next()) {
						foundBookings.add(new Booking(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7), new Equipment(rs.getString(8))));
					}
					getDatabaseConnector().closeConnection();
				}
			}
			return foundBookings;
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to find that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
		return null;
	}

	public void modifyBooking(int bookingID,Booking newBooking) {
		try {
			getDatabaseConnector().openConnection();
			getDatabaseConnector().createNewCallableStatement("{CALL spModifyBooking(?,?,?,?,?,?,?,?)}");
			try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement();) {
				callableStatement.setInt(1, bookingID);
				callableStatement.setString(2, newBooking.getBookingDay());
				callableStatement.setDate(3, convertFromJAVADateToSQLDate(newBooking.getBookingDate()));
				callableStatement.setTime(4, newBooking.getBookingStartTimeInSQLFormat());
				callableStatement.setTime(5, newBooking.getBookingCollectionTimeInSQLFormat());
				callableStatement.setString(6, newBooking.getBookingLocation());
				callableStatement.setString(7, newBooking.getBookingHolder());
				callableStatement.setString(8, newBooking.getRequiredEquipment().GetEquipmentName());
				getDatabaseConnector().execute();
				addBookingToListAtAGivenPosition(newBooking);
			}
			getDatabaseConnector().closeConnection();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to modify that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void removeBooking(int bookingID) {
		try {
			getDatabaseConnector().openConnection();
			
			getDatabaseConnector().createNewCallableStatement("{CALL spRemoveBooking(?)}");
			getDatabaseConnector().getCallableStatement().setInt(1,bookingID);
			getDatabaseConnector().execute();
			removeBookingFromList();
			
			getDatabaseConnector().closeConnection();
			
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to remove that booking from the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	void removeBookingFromList() {
		if (this.currentIndexOfBookingInList >= 0 && bookings.size() > 0) {
			this.bookings.remove(this.currentIndexOfBookingInList);
		} else {
			MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
		}
	}

	void addBookingToListAtAGivenPosition(Booking newBooking){
		if (currentIndexOfBookingInList >= 0 && currentIndexOfBookingInList <= bookings.size()) {
			this.bookings.add(currentIndexOfBookingInList, newBooking);
		}
	}

	public void setCurrentIndexOfBookingInList(int indexOfBookingInList) {
		try {
			if (indexOfBookingInList >= 0 && indexOfBookingInList <= bookings.size()) {
				this.currentIndexOfBookingInList = indexOfBookingInList;
			}
		} catch (IndexOutOfBoundsException e) {
			MessageBox.errorMessageBox("Big problems......");
		}
	}

	@Override
	public Iterator<Booking> iterator() {
		return bookings.iterator();
	}


	Date getDateSqlStatement(Booking bookingInformationKnown) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm", Locale.ENGLISH);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date d = null;
		try {
			 d = sdf.parse("00/00/0000");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		java.util.Date bookingDate = bookingInformationKnown.getBookingDate();
		java.util.Date todayDate = Calendar.getInstance().getTime();

		String bookingDateString = simpleDateFormat.format(bookingDate);
		String todayDateString = simpleDateFormat.format(todayDate);

		return bookingDateString.equals(todayDateString) ? convertFromJAVADateToSQLDate(d) : convertFromJAVADateToSQLDate(bookingInformationKnown.getBookingDate());
	}

}