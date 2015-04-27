package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingBusinessLayer extends BusinessLayer implements Iterable<Booking> {

	private ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues;
	ArrayList<Booking> bookings;
	private String databaseConnectionString;
	private int currentIndexOfBookingInList;
	public BookingBusinessLayer() {
		bookings = new ArrayList<Booking>();
		returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
		databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
		currentIndexOfBookingInList = -1;
	}

	public void populateBookingListOnLoad() {
		bookings.clear();
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("EXECUTE  spGetAllBookings");
			while (rs.next()) {
				bookings.add(new Booking(rs.getInt(1), rs.getString(2).trim(), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7).trim(), new Equipment(rs.getString(8))));
			}con.close();
			stmt.close();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void insertBooking(Booking booking) {
		try (Connection connection = DriverManager.getConnection(databaseConnectionString);
		     CallableStatement callableStatement = connection.prepareCall("{CALL spInsertBooking(?,?,?,?,?,?,?,?)}"))
		{
			callableStatement.setString(1, booking.getBookingDay());
			callableStatement.setDate(2, convertFromJAVADateToSQLDate(booking.getBookingDate()));
			callableStatement.setTime(3,booking.getBookingStartTimeInSQLFormat());
			callableStatement.setTime(4,booking.getBookingCollectionTimeInSQLFormat());
			callableStatement.setString(5,booking.getBookingLocation());
			callableStatement.setString(6,booking.getBookingHolder());
			callableStatement.setString(7,booking.getRequiredEquipment().GetEquipmentName());
			callableStatement.registerOutParameter(8,Types.INTEGER);
			callableStatement.execute();

			booking.setBookingID(callableStatement.getInt(8));

			this.bookings.add(booking);
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public ArrayList<Booking> findBookings(Booking bookingInformationKnown) { //show in a separate dialog, all users to save them to file
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();

			ResultSet rs;

			rs = stmt.executeQuery("EXECUTE spFindBooking '" + bookingInformationKnown.getBookingDay() + "','" + getDateSqlStatement(bookingInformationKnown) + "'," +
					"'" + bookingInformationKnown.getBookingStartTimeInSQLFormat() + "','" + bookingInformationKnown.getBookingCollectionTimeInSQLFormat() + "'," +
					"'" + bookingInformationKnown.getBookingLocation() + "'," + "'" + bookingInformationKnown.getBookingHolder() + "'," +
					"'" + bookingInformationKnown.getRequiredEquipment().GetEquipmentName() + "'");

			ArrayList<Booking> foundBookings = new ArrayList<Booking>();
			while (rs.next()) {
				foundBookings.add(new Booking(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7), new Equipment(rs.getString(8))));
			}

			con.close();
			stmt.close();
			return foundBookings;
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to find that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
		return null;
	}

	public void modifyBooking(int bookingID,Booking newBooking) {
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();
			System.out.println(newBooking.toString());
			int j = stmt.executeUpdate("EXECUTE spModifyBooking '" + bookingID + "','" + newBooking.getBookingDay() + "','" + getDateSqlStatement(newBooking) + "'," +
					"'" + newBooking.getBookingStartTimeInSQLFormat() + "','" + newBooking.getBookingCollectionTimeInSQLFormat() + "'," +
					"'" + newBooking.getBookingLocation() + "'," + "'" + newBooking.getBookingHolder() + "'," +
					"'" + newBooking.getRequiredEquipment().GetEquipmentName() + "'");
			removeBookingFromList();
			addBookingToListAtAGivenPosition(newBooking);
			con.close();
			stmt.close();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to modify that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void removeBooking(int bookingID) {
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();
			stmt.execute("EXECUTE spRemoveBooking'" + bookingID + "'");
			removeBookingFromList();
			con.close();
			stmt.close();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to remove that booking from the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void removeBookingFromList() {
		if (this.currentIndexOfBookingInList >= 0 && bookings.size() > 0) {
			this.bookings.remove(this.currentIndexOfBookingInList);
		} else {
			MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
		}
	}

	public void addBookingToListAtAGivenPosition(Booking newBooking){
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


	public String getDateSqlStatement(Booking bookingInformationKnown) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm", Locale.ENGLISH);

		java.util.Date bookingDate = bookingInformationKnown.getBookingDate();
		java.util.Date todayDate = Calendar.getInstance().getTime();

		String bookingDateString = simpleDateFormat.format(bookingDate);
		String todayDateString = simpleDateFormat.format(todayDate);

		ResultSet rs;

		String dateSqlStatement = bookingDateString.equals(todayDateString) ? "" : convertFromJAVADateToSQLDate(bookingInformationKnown.getBookingDate()).toString();

		return dateSqlStatement;
	}

}