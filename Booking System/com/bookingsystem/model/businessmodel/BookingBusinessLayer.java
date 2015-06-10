package com.bookingsystem.model.businessmodel;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

/**
 * Author: [Alex] on [$Date]
 */
public class BookingBusinessLayer extends BusinessLayer implements Iterable<Booking> {


    private final List<Booking> bookings;
    private final List<Booking> archivedBookings;
    private int currentIndexOfBookingInList;

    public BookingBusinessLayer() {
        bookings = new ArrayList<>();
        archivedBookings = new ArrayList<>();
        currentIndexOfBookingInList = -1;
    }

    public void populateBookingListOnLoad() {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                bookings.clear();
                archivedBookings.clear();
                getDatabaseConnector().createNewCallableStatement("{CALL spGetAllBookings}");
                try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    while (rs.next()) {
                        Booking booking;
                        booking = new Booking(rs.getInt(1), rs.getString(2).trim(), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7).trim(), new Equipment(rs.getString(8)));
                        booking.setBookingCompleted(rs.getBoolean(9));
                        if(booking.isBeforeToday() || booking.getBookingCompleted()) {
                            this.archivedBookings.add(booking);
                        } else {
                            this.bookings.add(booking);
                        }
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }

    public void insertBooking(Booking booking) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spInsertBooking(?,?,?,?,?,?,?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setString(1, booking.getBookingDay());
                    callableStatement.setDate(2, convertFromJAVADateToSQLDate(booking.getBookingDate()));
                    callableStatement.setTime(3, booking.getBookingStartTimeInSQLFormat());
                    callableStatement.setTime(4, booking.getBookingCollectionTimeInSQLFormat());
                    callableStatement.setString(5, booking.getBookingLocation());
                    callableStatement.setString(6, booking.getBookingHolder());
                    callableStatement.setString(7, booking.getRequiredEquipment().getEquipmentName());
                    callableStatement.setBoolean(8,booking.getBookingCompleted());
                    callableStatement.registerOutParameter(9, Types.INTEGER);
                    getDatabaseConnector().execute();
                    booking.setBookingID(callableStatement.getInt(9));
                    System.out.println(booking.getBookingID());
                    if(booking.isBeforeToday()) {
                        this.archivedBookings.add(booking);
                    } else {
                        this.bookings.add(booking);
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }
    
    public void insertBookings(ArrayList<Booking> bookingList) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spInsertBooking(?,?,?,?,?,?,?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                for(int i = 0;i<bookingList.size();i++) {
                    callableStatement.setString(1, bookingList.get(i).getBookingDay());
	                    callableStatement.setDate(2, convertFromJAVADateToSQLDate(bookingList.get(i).getBookingDate()));
	                    callableStatement.setTime(3, bookingList.get(i).getBookingStartTimeInSQLFormat());
	                    callableStatement.setTime(4, bookingList.get(i).getBookingCollectionTimeInSQLFormat());
	                    callableStatement.setString(5, bookingList.get(i).getBookingLocation());
	                    callableStatement.setString(6, bookingList.get(i).getBookingHolder());
	                    callableStatement.setString(7, bookingList.get(i).getRequiredEquipment().getEquipmentName());
                        callableStatement.setBoolean(8,bookingList.get(i).getBookingCompleted());
                        callableStatement.registerOutParameter(9, Types.INTEGER);
	                    getDatabaseConnector().execute();
	                    bookingList.get(i).setBookingID(callableStatement.getInt(9));
                    if(bookingList.get(i).isBeforeToday()) {
                        this.archivedBookings.add(bookingList.get(i));
                    } else {
                        this.bookings.add(bookingList.get(i));
                    }
	                
                }
                } catch (SQLException e) {
                	MessageBox.errorMessageBox("There was an issue while we were trying to insert that booking into the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }

    public ArrayList<Booking> findBookings(Booking bookingInformationKnown) { //show in a separate dialog, all users to save them to file
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spFindBooking(?,?,?,?,?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    ArrayList<Booking> foundBookings = new ArrayList<>();
                    callableStatement.setString(1, bookingInformationKnown.getBookingDay());
                    callableStatement.setDate(2, getDateSqlStatement(bookingInformationKnown));
                    callableStatement.setTime(3, bookingInformationKnown.getBookingStartTimeInSQLFormat());
                    callableStatement.setTime(4, bookingInformationKnown.getBookingCollectionTimeInSQLFormat());
                    callableStatement.setString(5, bookingInformationKnown.getBookingLocation());
                    callableStatement.setString(6, bookingInformationKnown.getBookingHolder());
                    callableStatement.setString(7, bookingInformationKnown.getRequiredEquipment().getEquipmentName());
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        while (rs.next()) {
                            foundBookings.add(new Booking(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getTime(5), rs.getString(6), rs.getString(7), new Equipment(rs.getString(8))));
                        }
                    }
                    getDatabaseConnector().closeConnection();
                    return foundBookings;
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to find that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return null;
    }

    public void modifyBooking(int bookingID, Booking newBooking) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spModifyBooking(?,?,?,?,?,?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    correctCurrentIndexWithID(bookingID);
                    callableStatement.setInt(1, bookingID);
                    callableStatement.setString(2, newBooking.getBookingDay());
                    callableStatement.setDate(3, convertFromJAVADateToSQLDate(newBooking.getBookingDate()));
                    callableStatement.setTime(4, newBooking.getBookingStartTimeInSQLFormat());
                    callableStatement.setTime(5, newBooking.getBookingCollectionTimeInSQLFormat());
                    callableStatement.setString(6, newBooking.getBookingLocation());
                    callableStatement.setString(7, newBooking.getBookingHolder());
                    callableStatement.setString(8, newBooking.getRequiredEquipment().getEquipmentName());
                    getDatabaseConnector().execute();
                    addBookingToListAtAGivenPosition(newBooking);
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to modify that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public void completeBooking(int bookingIDCurrentlyBeingProcessed) {
        getDatabaseConnector().openConnection();
        if(getDatabaseConnector().isConnected()) {
            if(!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spCompleteBooking(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    correctCurrentIndexWithID(bookingIDCurrentlyBeingProcessed);
                    callableStatement.setInt(1, bookings.get(this.currentIndexOfBookingInList).getBookingID());
                    getDatabaseConnector().execute();
                    archivedBookings.add(this.bookings.get(this.currentIndexOfBookingInList));
                    removeBookingFromList();
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to complete that booking in the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
        }
    }
    
    public List<Integer> isRoomFree(Booking booking) {
    	List<Integer> listOfIntegers = new ArrayList<>();
    	getDatabaseConnector().openConnection();
    	if(getDatabaseConnector().isConnected()) {
    		if(!getDatabaseConnector().isConnectionClosed()) {
    			getDatabaseConnector().createNewCallableStatement("{CALL spCheckIfRoomIsFree(?,?,?)}");
    			try (CallableStatement  callableStatement = getDatabaseConnector().getCallableStatement()) {
    				callableStatement.setString(1,booking.getBookingLocation());
    				callableStatement.setDate(2, convertFromJAVADateToSQLDate(booking.getBookingDate()));
    				callableStatement.setTime(3, booking.getBookingStartTimeInSQLFormat());
    				try (ResultSet rs = getDatabaseConnector().executeQuery()) {
    					while(rs.next()) {
    						listOfIntegers.add(rs.getInt(1));
    					}
    				}
    			} catch (SQLException e) {
    				MessageBox.errorMessageBox(("There was an issue whilst trying to see if a room was free!"));
    			}
    				
    			}
    		
    	}
    
    	return listOfIntegers;
    
    }

    private void correctCurrentIndexWithID(int idToFind) {
        for (int i = 0; i<bookings.size();i++) {
            if (bookings.get(i).getBookingID() == idToFind) {
                this.currentIndexOfBookingInList = i;
            }
        }
    }

    public void removeBooking(int bookingID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spRemoveBooking(?)}");
                try {
                    getDatabaseConnector().getCallableStatement().setInt(1, bookingID);
                    getDatabaseConnector().execute();
                    correctCurrentIndexWithID(bookingID);
                    removeBookingFromList();
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to remove that booking from the database!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    private void removeBookingFromList() {
        if (this.currentIndexOfBookingInList >= 0 && bookings.size() > 0) {
            this.bookings.remove(this.currentIndexOfBookingInList);
        } else {
            MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
        }
    }

    private void addBookingToListAtAGivenPosition(Booking newBooking) {
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

        return bookingDateString.equals(stringWhenNoDateHasBeenEntered) ? convertFromJAVADateToSQLDate(date) : convertFromJAVADateToSQLDate(bookingInformationKnown.getBookingDate());
    }

}