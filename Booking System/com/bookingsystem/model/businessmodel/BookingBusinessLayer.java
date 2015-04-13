package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingBusinessLayer implements Iterable<Booking> {

	private ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues;
	ArrayList<Booking> bookings;
	private String databaseConnectionString;

	public BookingBusinessLayer() {
		bookings = new ArrayList<Booking>();
		returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
		databaseConnectionString = returnSpecifiedPropertyValues.getDatabaseConnectionString();
	}

	public void populateBookingListOnLoad() {
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("EXECUTE  spGetAllBookings");
			while (rs.next()) {
				bookings.add(new Booking(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7), new Equipment(rs.getString(8))));
			}
			con.close();
			stmt.close();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public void insertBooking(Booking booking) {
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();
			System.out.println(booking);
			int g = stmt.executeUpdate("EXECUTE spInsertBooking '" + booking.getBookingDay() + "','" + convertFromJAVADateToSQLDate(booking.getBookingDate()) + "'," +
					"'" + booking.getBookingStartTimeInSQLFormat() + "','" + booking.getBookingCollectionTimeInSQLFormat() + "'," +
					"'" + booking.getBookingLocation() + "'," + "'" + booking.getBookingHolder() + "'," +
					"'" + booking.getRequiredEquipment().GetEquipmentName() + "'");


			System.out.println(g);
			bookings.add(booking);
			con.close();
			stmt.close();
		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}
	}

	public ArrayList<Booking> findBookings(Booking bookingInformationKnown) {
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

	public void modifyBooking(int id,Booking newBooking) {
		Statement stmt;
		try {
			Connection con = DriverManager.getConnection(databaseConnectionString);
			stmt = con.createStatement();

			 stmt.execute("EXECUTE spModifyBooking '" + id + "','" + newBooking.getBookingDay() + "','" + getDateSqlStatement(newBooking) + "'," +
					"'" + newBooking.getBookingStartTimeInSQLFormat() + "','" + newBooking.getBookingCollectionTimeInSQLFormat() + "'," +
					"'" + newBooking.getBookingLocation() + "'," + "'" + newBooking.getBookingHolder() + "'," +
					"'" + newBooking.getRequiredEquipment().GetEquipmentName() + "'");




		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to modify that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
		}

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

	public void removeBooking(Booking booking) {

	}

	@Override
	public Iterator<Booking> iterator() {
		return bookings.iterator();
	}


	public static java.sql.Date convertFromJAVADateToSQLDate(
			java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}


}