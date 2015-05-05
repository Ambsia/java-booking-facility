package com.bookingsystem.controller.handler;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.dialogpanels.bookingdialog.*;
import com.bookingsystem.view.panelparts.UIBookingSystemControlPanel;
import com.bookingsystem.view.panes.UIBookingSystemPanel;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class BookingHandler implements ActionListener {

	private final UIBookingSystemAddPanel bookingSystemAddPanel;
	private final UIBookingSystemFindPanel bookingSystemFindPanel;
	private final UIBookingSystemEditPanel bookingSystemEditPanel;
	private final UIBookingSystemRemovePanel bookingSystemRemovePanel;
	private final UIBookingSystemShowBookingsFound bookingSystemShowBookingsFound;
	private final UIBookingSystemPanel bookingSystemPanel;

	private static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
	private static final DateFormat BOOKING_DATE_FORMAT_2 = new SimpleDateFormat("dd.MMM.yy", Locale.ENGLISH);
	private static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

	private final List<Integer> listOfBadBookingIDs;
	private int bookingIDCurrentlyBeingProcessed;

	private final Handler handler;

	public BookingHandler(Handler handler) {
		this.handler = handler;

		UIBookingSystemControlPanel bookingSystemControlPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel().getBookingSystemControlPanel();
		this.bookingSystemAddPanel = bookingSystemControlPanel.getUIBookingSystemAddPanel();
		this.bookingSystemFindPanel = bookingSystemControlPanel.getUIBookingSystemFindPanel();
		this.bookingSystemEditPanel = bookingSystemControlPanel.getUIBookingSystemEditPanel();
		this.bookingSystemRemovePanel = bookingSystemControlPanel.getUIBookingSystemRemovePanel();
		this.bookingSystemShowBookingsFound = bookingSystemControlPanel.getUIBookingSystemShowBookingsFound();
		this.bookingSystemPanel = handler.getView().getBookingSystemTabbedPane().getBookingSystemPanel();

		listOfBadBookingIDs = new ArrayList<>();
		bookingIDCurrentlyBeingProcessed = 1;
	}

	@Override
	public void actionPerformed(ActionEvent eventOccurred) {
		Log log = new Log(eventOccurred.getActionCommand(), this.getClass().getSimpleName(), new Date());
		switch (eventOccurred.getActionCommand()) {
			case "Import":
				JFileChooser jFileChooser = new JFileChooser();
				File file;
				FileInputStream fileInputStream;
				XSSFWorkbook workBook;
				XSSFSheet sheet;
				XSSFRow row;
				int rows;
				Booking importedBooking;
				try {
					int returnVal = jFileChooser.showOpenDialog(bookingSystemPanel);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						if (jFileChooser.getSelectedFile().getName()
								.endsWith(".xlsx")) {
							file = jFileChooser.getSelectedFile();
							System.out.println("Opening: " + file.getName() + "."
									+ "\n");
							fileInputStream = new FileInputStream(file);
							workBook = (XSSFWorkbook) WorkbookFactory
									.create(new PushbackInputStream(fileInputStream));

							sheet = workBook.getSheetAt(1);
							rows = sheet.getPhysicalNumberOfRows();
							for (int r = 1; r < rows; r++) {
								row = sheet.getRow(r);
								if (row.toString() != "") {
									if (row.getCell((short) 0).toString() != "") {
										this.bookingIDCurrentlyBeingProcessed = r;

										importedBooking = new Booking(this.bookingIDCurrentlyBeingProcessed,
												validateDayAsString(row.getCell((short)0).toString()),
												stringToDate(row.getCell((short) 1).toString()),
												stringToTime(row.getCell((short) 2).toString(), false),
												stringToTime(row.getCell((short) 2).toString(), true),
												row.getCell((short) 3).toString(),
												row.getCell((short) 4).toString(),
												new Equipment(row.getCell((short) 5).toString()));

										handler.getBookingBusinessLayer().insertBooking(importedBooking);
										bookingSystemPanel
												.addBookingToList(importedBooking);
									}
								}
							}
						} else {
							MessageBox.errorMessageBox(".xlsx spreadsheets are only accepted.");
							break;
						}
					} else {
						break;
					}
				} catch (Exception e) {
					handler.getLoggerBusinessLayer().exceptionCaused(log, e);
					MessageBox.errorMessageBox(e.toString());
				}
				populateBadBookingMessageBox();
				break;
			case "Search":
				try {
					if (bookingSystemFindPanel.showDialog() == 0) {
						bookingSystemShowBookingsFound.clearBookingsFromFoundList();
						this.bookingIDCurrentlyBeingProcessed = 1;
						Booking newBooking = convertStringArrayToBooking(bookingSystemFindPanel.getBookingStringArray());

						MessageBox.infoMessageBox("A search will be performed based on the given details.");

						bookingSystemShowBookingsFound.addBookingsToList(handler.getBookingBusinessLayer().findBookings(newBooking));
						bookingSystemShowBookingsFound.showDialog();

					}
				} catch (Exception e) {
					handler.getLoggerBusinessLayer().exceptionCaused(log, e);
					MessageBox.errorMessageBox("There was an issue while trying to execute a search\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
				break;
			case "Export":
				System.out.println("export clicked");
				break;
			case "Add":
				try {
					if (bookingSystemAddPanel.showDialog() == 0) {
						Booking newBooking = convertStringArrayToBooking(bookingSystemAddPanel.getBookingStringArray());
						if (newBooking.isValid()) {
							handler.getBookingBusinessLayer().insertBooking(newBooking);
							bookingSystemPanel.addBookingToList(newBooking);
							log.setBookingIDInserted(newBooking.getBookingID());

						} else {
							MessageBox.errorMessageBox("Please enter all of the required details for booking");
						}
					}
				} catch (Exception e) {
					handler.getLoggerBusinessLayer().exceptionCaused(log, e);
					MessageBox.errorMessageBox("There was an issue while trying to add a booking.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}

				break;
			case "Remove":
				try {
					this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDOfSelectedRow();
					if (this.bookingIDCurrentlyBeingProcessed >= 0) {
						bookingSystemRemovePanel.setTextOfComponents(convertStringArrayListToObjectList(bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList()));
						if (bookingSystemRemovePanel.showDialog() == 0) {
							handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
							handler.getBookingBusinessLayer().removeBooking(this.bookingIDCurrentlyBeingProcessed);
							bookingSystemPanel.removeBookingFromTable();
							log.setBookingIDDeleted(this.bookingIDCurrentlyBeingProcessed);
						}
					} else {
						MessageBox.warningMessageBox("Please select a booking to remove");
					}
				} catch (Exception e) {
					handler.getLoggerBusinessLayer().exceptionCaused(log, e);
					MessageBox.errorMessageBox("There was an issue while trying to remove a booking.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
				break;
			case "Edit":
				try {
					this.bookingIDCurrentlyBeingProcessed = bookingSystemPanel.getIDOfSelectedRow();
					if (this.bookingIDCurrentlyBeingProcessed >= 0) {
						bookingSystemEditPanel.setTextOfComponents(convertStringArrayListToObjectList(bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList()));
						if (bookingSystemEditPanel.showDialog() == 0) {
							Booking newBooking = convertStringArrayToBooking(bookingSystemEditPanel.getBookingStringArray());
							if (newBooking.isValid()) {
								handler.getBookingBusinessLayer().setCurrentIndexOfBookingInList(bookingSystemPanel.getIndexOfSelectedRow());
								handler.getBookingBusinessLayer().modifyBooking(this.bookingIDCurrentlyBeingProcessed, newBooking);
								bookingSystemPanel.replaceBookingInList(newBooking);
								log.setBookingIDEdited(this.bookingIDCurrentlyBeingProcessed);
							} else {
								MessageBox.errorMessageBox("Please enter all of the required details for booking");
							}
						}
					} else {
						MessageBox.warningMessageBox("Please select a booking to modify");
					}
				} catch (Exception e) {
					handler.getLoggerBusinessLayer().exceptionCaused(log, e);
					MessageBox.errorMessageBox("There was an issue while trying to edit a booking.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
				break;
			case "Load":
				try {
					handler.getBookingBusinessLayer().populateBookingListOnLoad();
					bookingSystemPanel.removeAllBookings();
					for (Object object : IteratorUtils.toList(handler.getBookingBusinessLayer().iterator())) {
						bookingSystemPanel.addBookingToList((Booking) object);
					}
					if (bookingSystemPanel.getRowCountOfTable() == 0) {
						MessageBox.infoMessageBox("No bookings found.");
					}
				} catch (Exception e) {
					handler.getLoggerBusinessLayer().exceptionCaused(log, e);
					MessageBox.errorMessageBox("There was an issue while trying to load bookings.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
				break;
			case "Filter":

				break;
			default:
				System.out.println("control handler not found");
		}
		handler.getLoggerBusinessLayer().insertLog(log);
	}

	private Date stringToDate(String stringToConvert) {
		String s = stringToConvert.replaceAll("[^A-Za-z0-9. ]", "."); //will replace all non-alpha characters with a period
		try {
			return BOOKING_DATE_FORMAT.parse(s); //tries to match the string with the format dd.MM.yy
		} catch (ParseException ep) {
			try {
				return BOOKING_DATE_FORMAT_2.parse(s); //tries to match the string with teh format dd.MMM.yy
			} catch (Exception e) {
				listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed); //adds the id of the booking that cannot be properly processed
			}
		} catch (Exception e) {
			listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed); //again but a different catch
		}
		return new Date(); // always returns something we can manage
	}

	private Object[] convertStringArrayListToObjectList(ArrayList<String> arrayOfStrings) {
		return new Object[]{arrayOfStrings.get(1),
				stringToDate(arrayOfStrings.get(2)),
				stringToTime(arrayOfStrings.get(3), false),
				stringToTime(arrayOfStrings.get(3), true),
				arrayOfStrings.get(4),
				arrayOfStrings.get(5),
				arrayOfStrings.get(6)};
	}

	private Booking convertStringArrayToBooking(String[] bookingStrings) {
		return new Booking(this.bookingIDCurrentlyBeingProcessed,
				bookingStrings[0],
				stringToDate(bookingStrings[1]),
				stringToTime(bookingStrings[2], false),
				stringToTime(bookingStrings[3], true),
				bookingStrings[4],
				bookingStrings[5],
				new Equipment(bookingStrings[6]));
	}

	private Date stringToTime(String unVerifiedStringToConvert, boolean collectionTime) {
		String strippedUnverifiedStringToConvert = unVerifiedStringToConvert.replaceAll("[a-zA-z ]", "");
		String verifiedStringToConvert;
		if (!strippedUnverifiedStringToConvert.contains("-")) {
			verifiedStringToConvert = strippedUnverifiedStringToConvert;
		} else {
			if (!collectionTime) {
				verifiedStringToConvert = strippedUnverifiedStringToConvert.substring(0, strippedUnverifiedStringToConvert.indexOf('-'));
			} else {
				verifiedStringToConvert = strippedUnverifiedStringToConvert.substring(strippedUnverifiedStringToConvert.indexOf('-') + 1, strippedUnverifiedStringToConvert.length());
			}
		}
		try { //check if i can parse to long, if not work another way of converting the unverified string into a usable date format..
			long dayMs = Long.parseLong(unVerifiedStringToConvert);
			return new Date(dayMs);
		} catch (NumberFormatException nfe) {
			try {
				return BOOKING_TIME_FORMAT.parse(verifiedStringToConvert);
			} catch (ParseException pe) {
				listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed);
				return new Date();
			}
		}
	}

	private void populateBadBookingMessageBox() {
		String s = "Booking ID: ";
		if (!listOfBadBookingIDs.isEmpty()) {
			for (int i : listOfBadBookingIDs) {
				s += i + ", ";
			}
			s += " have/has incorrectly formatted date(s).";
			listOfBadBookingIDs.clear();
			MessageBox.warningMessageBox(s);
		}
	}

	private String validateDayAsString(String day) {
		String validatedString;
		if (day.matches("Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday")) {
			return day;
		} else {
			char[] chars = day.toCharArray();
			int validatedStartIndex = 0;
			for (int k = 0; k < chars.length; k++) {
				if (k + 3 < chars.length) {
					String dayShort = "";
					dayShort += chars[k];
					dayShort += chars[k + 1];
					dayShort += chars[k + 2];
					if (dayShort.matches("Mon|Tue|Wed|Thu|Fri|Sat|Sun")) {
						validatedStartIndex = k;
						break;
					}
				}
			}
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] == 'd') {
					if (i + 2 < day.length() && day.substring(i, i + 3).equals("day")) {
						validatedString = day.substring(validatedStartIndex, i + 3);
						if(validatedString.matches("Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday")) {
							return validatedString;
						}
					}
				}
			}
			return day;
		}
	}
}



//

// This trick ensures that we get the data properly even if it
// doesn't start from first few rows
// for (int i = 0; i < 10 || i < rows; i++) {
// row = sheet.getRow(i);
// if (row != null) {
// tmp = sheet.getRow(i).getPhysicalNumberOfCells();
// if (tmp > cols) cols = tmp;
// }
