package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingBusinessLayer extends BusinessLayer implements
		Iterable<Booking> {

	private final List<Booking> bookings;
	private final List<Booking> archivedBookings;
	private int currentIndexOfBookingInList;
	private EquipmentBusinessLayer equipments;

	public BookingBusinessLayer(EquipmentBusinessLayer equipments) {
		bookings = new ArrayList<>();
		archivedBookings = new ArrayList<>();
		currentIndexOfBookingInList = -1;
		this.equipments = equipments;
	}

	public void populateBookingListOnLoad() {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				bookings.clear();
				archivedBookings.clear();
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spGetAllBookings}");
				try (ResultSet rs = getDatabaseConnector().executeQuery()) {
					while (rs.next()) {
						Booking booking = null;
						try {
							booking = new Booking(rs.getInt(1),
									rs.getString(2), rs.getDate(3),
									rs.getTime(4), rs.getTime(5),
									rs.getString(6), rs.getString(7),
									equipments.getEqiupment(rs.getInt(8)));
							booking.setBookingCompleted(rs.getBoolean(9));
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (booking != null) {
							if (booking.getBookingCompleted()) {
								this.archivedBookings.add(booking);
							} else {
								this.bookings.add(booking);
							}
						}
					}
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
			}
			getDatabaseConnector().closeConnection();
		}
	}

	public void insertBooking(Booking booking) {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spInsertBooking(?,?,?,?,?,?,?,?,?)}");
				try (CallableStatement callableStatement = getDatabaseConnector()
						.getCallableStatement()) {
					callableStatement.setString(1, booking.getBookingDay());
					callableStatement.setDate(2,
							convertFromJAVADateToSQLDate(booking
									.getBookingDate()));
					callableStatement.setTime(3,
							booking.getBookingStartTimeInSQLFormat());
					callableStatement.setTime(4,
							booking.getBookingCollectionTimeInSQLFormat());
					callableStatement
							.setString(5, booking.getBookingLocation());
					callableStatement.setString(6, booking.getBookingHolder());
					callableStatement.setInt(7, booking.getRequiredEquipment()
							.getEquipmentID());
					callableStatement.setBoolean(8,
							booking.getBookingCompleted());
					callableStatement.registerOutParameter(9, Types.INTEGER);
					getDatabaseConnector().execute();
					System.out.println(booking.getRequiredEquipment()
							.getEquipmentID());
					equipments.increaseEquipmentUsage(booking
							.getRequiredEquipment().getEquipmentID());
					booking.setBookingID(callableStatement.getInt(9));
					if (booking.isBeforeToday()) {
						this.archivedBookings.add(booking);
					} else {
						this.bookings.add(booking);
					}
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
			}
			getDatabaseConnector().closeConnection();
		}
	}

	public void insertBookings(ArrayList<Booking> bookingList) {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spInsertBooking(?,?,?,?,?,?,?,?,?)}");
				try (CallableStatement callableStatement = getDatabaseConnector()
						.getCallableStatement()) {
					for (Booking aBookingList : bookingList) {
						callableStatement.setString(1,
								aBookingList.getBookingDay());
						callableStatement.setDate(2,
								convertFromJAVADateToSQLDate(aBookingList
										.getBookingDate()));
						callableStatement.setTime(3,
								aBookingList.getBookingStartTimeInSQLFormat());
						callableStatement.setTime(4, aBookingList
								.getBookingCollectionTimeInSQLFormat());
						callableStatement.setString(5,
								aBookingList.getBookingLocation());
						callableStatement.setString(6,
								aBookingList.getBookingHolder());
						callableStatement.setInt(7, aBookingList
								.getRequiredEquipment().getEquipmentID());
						callableStatement.setBoolean(8,
								aBookingList.getBookingCompleted());
						callableStatement
								.registerOutParameter(9, Types.INTEGER);
						getDatabaseConnector().execute();
						aBookingList.setBookingID(callableStatement.getInt(9));
						equipments.increaseEquipmentUsage(aBookingList
								.getRequiredEquipment().getEquipmentID());
						if (aBookingList.isBeforeToday()) {
							this.archivedBookings.add(aBookingList);
						} else {
							this.bookings.add(aBookingList);
						}
					}
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
			}
			getDatabaseConnector().closeConnection();
		}
	}

	public ArrayList<Booking> findBookings(Booking bookingInformationKnown) { // show
																				// in
																				// a
																				// separate
																				// dialog,
																				// all
																				// users
																				// to
																				// save
																				// them
																				// to
																				// file
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spFindBooking(?,?,?,?,?,?,?)}");
				try (CallableStatement callableStatement = getDatabaseConnector()
						.getCallableStatement()) {
					ArrayList<Booking> foundBookings = new ArrayList<>();
					callableStatement.setString(1,
							bookingInformationKnown.getBookingDay());
					callableStatement.setDate(2,
							getDateSqlStatement(bookingInformationKnown));
					callableStatement.setTime(3, bookingInformationKnown
							.getBookingStartTimeInSQLFormat());
					callableStatement.setTime(4, bookingInformationKnown
							.getBookingCollectionTimeInSQLFormat());
					callableStatement.setString(5,
							bookingInformationKnown.getBookingLocation());
					callableStatement.setString(6,
							bookingInformationKnown.getBookingHolder());
					callableStatement.setInt(7, bookingInformationKnown
							.getRequiredEquipment().getEquipmentID());
					System.out.println("here");
					try (ResultSet rs = getDatabaseConnector().executeQuery()) {
						while (rs.next()) {
							// System.out.println("id= " + rs.getInt(1) + "\n" +
							// "day= " + rs.getString(2) + "\n" + "date= " +
							// rs.getDate(3) + "\n" +
							// "datetime= " + rs.getTime(4) + "\n" + "gettime= "
							// + rs.getTime(5) + "\n"
							// + "location= " + rs.getString(6) + "\n" +
							// "holder= " + rs.getString(7) + "\n" +
							// "equipment= " + rs.getString(8) + "\n" +
							// "equipmentid= " + rs.getInt(10) + "\n" +
							// "equipment name= " + rs.getString(11) + "\n" +
							// "equipment description= " +
							// rs.getString(12) + "\n"
							// + "\n" + "equipment usage= " + rs.getInt(13) );
							// equipment.setEquipmentID(rs.getInt(10));
							// equipment.setEquipmentDescription(rs.getString(12));
							// equipment.setEquipmentUsage(rs.getInt(13));
							foundBookings
									.add(new Booking(
											rs.getInt(1),
											rs.getString(2),
											rs.getDate(3),
											rs.getTime(4),
											rs.getTime(5),
											rs.getString(6),
											rs.getString(7),
											equipments
													.getEqiupment(bookingInformationKnown
															.getRequiredEquipment()
															.getEquipmentID())));
						}
					}
					getDatabaseConnector().closeConnection();
					this.bookings.addAll(0, foundBookings);
					return foundBookings;
				} catch (SQLException e) {
					e.printStackTrace();
					MessageBox
							.errorMessageBox("There was an issue while we were trying to find that booking in the database!\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
			}
			getDatabaseConnector().closeConnection();
		}
		return null;
	}

	public void modifyBooking(Booking oldBooking, Booking newBooking) {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spModifyBooking(?,?,?,?,?,?,?,?)}");
				try (CallableStatement callableStatement = getDatabaseConnector()
						.getCallableStatement()) {
					correctCurrentIndexWithID(oldBooking.getBookingID());
					callableStatement.setInt(1, oldBooking.getBookingID());
					callableStatement.setString(2, newBooking.getBookingDay());
					callableStatement.setDate(3,
							convertFromJAVADateToSQLDate(newBooking
									.getBookingDate()));
					callableStatement.setTime(4,
							newBooking.getBookingStartTimeInSQLFormat());
					callableStatement.setTime(5,
							newBooking.getBookingCollectionTimeInSQLFormat());
					callableStatement.setString(6,
							newBooking.getBookingLocation());
					callableStatement.setString(7,
							newBooking.getBookingHolder());
					callableStatement.setInt(8, newBooking
							.getRequiredEquipment().getEquipmentID());
					getDatabaseConnector().execute();
					System.out.println(newBooking.getRequiredEquipment()
							.getEquipmentID()
							+ "|new | old|"
							+ oldBooking.getRequiredEquipment()
									.getEquipmentID());
					if (newBooking.getRequiredEquipment().getEquipmentID() != oldBooking
							.getRequiredEquipment().getEquipmentID()) {
						System.out.println("mmmm");
						equipments.decreaseEquipmentUsage(oldBooking
								.getRequiredEquipment().getEquipmentID());
						equipments.increaseEquipmentUsage(newBooking
								.getRequiredEquipment().getEquipmentID());
					}
					addBookingToListAtAGivenPosition(newBooking);
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox("There was an issue while we were trying to modify that booking in the database.\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
				getDatabaseConnector().closeConnection();
			}
		}
	}

	public void completeBooking(int bookingIDCurrentlyBeingProcessed) {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spCompleteBooking(?)}");
				try (CallableStatement callableStatement = getDatabaseConnector()
						.getCallableStatement()) {
					correctCurrentIndexWithID(bookingIDCurrentlyBeingProcessed);
					callableStatement.setInt(1,
							bookings.get(this.currentIndexOfBookingInList)
									.getBookingID());
					getDatabaseConnector().execute();
					archivedBookings.add(this.bookings
							.get(this.currentIndexOfBookingInList));
					removeBookingFromList();
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox("There was an issue while we were trying to complete that booking in the database!\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
			}
		}
	}

	public List<Integer> isRoomFree(Booking booking) {
		List<Integer> listOfIntegers = new ArrayList<>();
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spCheckIfRoomIsFree(?,?,?,?)}");
				try (CallableStatement callableStatement = getDatabaseConnector()
						.getCallableStatement()) {
					callableStatement
							.setString(1, booking.getBookingLocation());
					callableStatement.setDate(2,
							convertFromJAVADateToSQLDate(booking
									.getBookingDate()));
					callableStatement.setTime(3,
							booking.getBookingStartTimeInSQLFormat());
					callableStatement.setInt(4, booking.getBookingID());
					try (ResultSet rs = getDatabaseConnector().executeQuery()) {
						while (rs.next()) {
							System.out.println(rs.getInt(1));
							listOfIntegers.add(rs.getInt(1));
						}
					}
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox(("There was an issue whilst trying to see if a room was free!"));
				}
			}
		}
		return listOfIntegers;
	}

	private void correctCurrentIndexWithID(int idToFind) {
		for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i).getBookingID() == idToFind) {
				this.currentIndexOfBookingInList = i;
			}
		}
	}

	public void removeBooking(Booking booking) {
		getDatabaseConnector().openConnection();
		if (getDatabaseConnector().isConnected()) {
			if (getDatabaseConnector().isConnectionClosed()) {
				getDatabaseConnector().createNewCallableStatement(
						"{CALL spRemoveBooking(?)}");
				try {
					getDatabaseConnector().getCallableStatement().setInt(1,
							booking.getBookingID());
					getDatabaseConnector().execute();
					equipments.decreaseEquipmentUsage(booking
							.getRequiredEquipment().getEquipmentID());
					correctCurrentIndexWithID(booking.getBookingID());
					removeBookingFromList();
				} catch (SQLException e) {
					MessageBox
							.errorMessageBox("There was an issue while we were trying to remove that booking from the database!\n"
									+ "Does this make any sense to you.."
									+ e.toString() + "?");
				}
				getDatabaseConnector().closeConnection();
			}
		}
	}

	private void removeBookingFromList() {
		if (this.currentIndexOfBookingInList >= 0 && bookings.size() > 0) {
			this.bookings.remove(this.currentIndexOfBookingInList);
		} else {
			MessageBox
					.errorMessageBox("Nothing selected, or there is nothing to delete.");
		}
	}

	private void addBookingToListAtAGivenPosition(Booking newBooking) {
		if (currentIndexOfBookingInList >= 0
				&& currentIndexOfBookingInList <= bookings.size()) {
			this.bookings.add(currentIndexOfBookingInList, newBooking);
		}
	}

	public EquipmentBusinessLayer getEquipments() {
		return equipments;
	}

	@Override
	public Iterator<Booking> iterator() {
		return bookings.iterator();
	}

	public List<Booking> getArchivedBookings() {
		return this.archivedBookings;
	}

	Date getDateSqlStatement(Booking bookingInformationKnown) {

		java.util.Date date = new java.util.Date();
		date.setTime(0);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
		java.util.Date bookingDate = bookingInformationKnown.getBookingDate();

		String bookingDateString = dateFormat.format(bookingDate);
		String stringWhenNoDateHasBeenEntered = dateFormat.format(date);

		return bookingDateString.equals(stringWhenNoDateHasBeenEntered) ? convertFromJAVADateToSQLDate(date)
				: convertFromJAVADateToSQLDate(bookingInformationKnown
						.getBookingDate());
	}

}