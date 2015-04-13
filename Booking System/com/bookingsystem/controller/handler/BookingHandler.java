package com.bookingsystem.controller.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFileChooser;

import com.bookingsystem.model.businessmodel.BookingBusinessLayer;


import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import com.bookingsystem.view.*;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BookingHandler implements ActionListener {
	private BookingSystemUILoader view;
	private UIBookingSystemPanel bookingSystemPanel;
	private UIBookingSystemControlPanel bookingSystemControlPanel;
	private UIBookingSystemAddPanel bookingSystemAddPanel;
	private UIBookingSystemFindPanel bookingSystemFindPanel;
	private UIBookingSystemEditPanel bookingSystemEditPanel;
	private static DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
	private static DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

	private BookingBusinessLayer bookingBusinessLayer;
	private List<Integer> listOfBadBookingIDs;

	public BookingHandler(BookingBusinessLayer model,UIBookingSystemPanel bookingSystemPanel) {
		this.bookingSystemPanel = bookingSystemPanel;
		bookingSystemControlPanel = this.bookingSystemPanel.getBookingSystemControlPanel();
		bookingSystemAddPanel = bookingSystemControlPanel.getUIBookingSystemAddPanel();
		bookingSystemFindPanel = bookingSystemControlPanel.getUIBookingSystemFindPanel();
		bookingSystemEditPanel = bookingSystemControlPanel.getUIBookingSystemEditPanel();
		listOfBadBookingIDs = new ArrayList<>();

		bookingBusinessLayer = model;
	}

	@Override
	public void actionPerformed(ActionEvent eventOccurred) {
		switch (eventOccurred.getActionCommand()) {
			case "Import":
				JFileChooser jFileChooser = new JFileChooser();

				File file = null;
				FileInputStream fileInputStream = null;
				XSSFWorkbook workBook = null;
				XSSFSheet sheet = null;
				XSSFRow row = null;
				int rows = 0;
				Booking importedBooking;
				try {
					int returnVal = jFileChooser.showOpenDialog(view);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						if (jFileChooser.getSelectedFile().getName()
								.endsWith(".xlsx")) {
							file = jFileChooser.getSelectedFile();
							System.out.println("Opening: " + file.getName() + "."
									+ "\n");
							fileInputStream = new FileInputStream(file);
							workBook = (XSSFWorkbook) WorkbookFactory
									.create(new PushbackInputStream(fileInputStream));

							sheet = workBook.getSheetAt(0);
							rows = sheet.getPhysicalNumberOfRows();

							for (int r = 1; r < rows; r++) {
								row = sheet.getRow(r);
								if (row.toString() != "") {
									if (row.getCell((short) 0).toString() != "") {

										importedBooking = new Booking(r,
												row.getCell((short) 0).toString(),
												stringToDate(r, row.getCell((short) 1).toString()),
												stringToTime(r, row.getCell((short) 2).toString(), false),
												stringToTime(r, row.getCell((short) 2).toString(), true),
												row.getCell((short) 3).toString(),
												row.getCell((short) 4).toString(),
												new Equipment(row.getCell((short) 5).toString()));

										bookingBusinessLayer.insertBooking(importedBooking);
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
					MessageBox.errorMessageBox(e.toString());
				}
				populateBadBookingMessageBox();
				break;
			case "Search":
				try {
					int result = bookingSystemFindPanel.showDialog();
					if (result == 0) {
						String[] bookingStrings = bookingSystemFindPanel.getBookingStringArray();
						int id = 1;
						Booking newBooking = new Booking(id,
								bookingStrings[0],
								stringToDate(id, bookingStrings[1]),
								stringToTime(id, bookingStrings[2], false),
								stringToTime(id, bookingStrings[3], true),
								bookingStrings[4],
								bookingStrings[5],
								new Equipment(bookingStrings[6]));

						MessageBox.infoMessageBox("A search will be performed based on the given details.");

						bookingSystemPanel.addBookingsToList(bookingBusinessLayer.findBookings(newBooking));
					}
				} catch (Exception e) {
					MessageBox.errorMessageBox("There was an issue while trying to execute a search\n" + "Does this make any sense to you.." + e.toString() + "?");
				}
				break;
			case "Export":
				System.out.println("export clicked");
				break;
			case "Add":
				try {
					int result = bookingSystemAddPanel.showDialog();
					if (result == 0) {
						String[] bookingStrings = bookingSystemAddPanel.getBookingStringArray();

						int id = bookingSystemPanel.getRowCountOfTable();

						Booking newBooking = new Booking(id,
								bookingStrings[0],
								stringToDate(id, bookingStrings[1]),
								stringToTime(id, bookingStrings[2], false),
								stringToTime(id, bookingStrings[3], true),
								bookingStrings[4],
								bookingStrings[5],
								new Equipment(bookingStrings[6]));
						if (!newBooking.isValid()) {
							bookingBusinessLayer.insertBooking(newBooking);
							bookingSystemPanel.addBookingToList(newBooking);

						} else {
							MessageBox.errorMessageBox("Please enter all of the required details for booking");
						}
					}
				} catch (Exception e) {
					MessageBox.errorMessageBox("There was an issue while trying to add a booking.\n" + "Does this make any sense to you.." + e.toString() + "?");
				}

				break;
			case "Remove":
				System.out.println("remove clicked");
				break;
			case "Edit":
				try {

					int id = bookingSystemPanel.getIDOfSelectedRow();
					if (id >= 0) {
						ArrayList<String> arrayOfStrings = bookingSystemPanel.getCurrentlySelectedRowAsStringArrayList();

						Object[] bookingObjects = { arrayOfStrings.get(1),
								stringToDate(0, arrayOfStrings.get(2)),
								stringToTime(0, arrayOfStrings.get(3), false),
								stringToTime(0, arrayOfStrings.get(3),true),
								arrayOfStrings.get(4),
								arrayOfStrings.get(5),
								arrayOfStrings.get(6) };

						bookingSystemEditPanel.setTextOfComponents(bookingObjects);

						int result = bookingSystemEditPanel.showDialog();
						if (result == 0) {
							String[] bookingStrings = bookingSystemEditPanel.getBookingStringArray();
							Booking newBooking = new Booking(id,
									bookingStrings[0],
									stringToDate(id, bookingStrings[1]),
									stringToTime(id, bookingStrings[2], false),
									stringToTime(id, bookingStrings[3], true),
									bookingStrings[4],
									bookingStrings[5],
									new Equipment(bookingStrings[6]));
							if (!newBooking.isValid()) {
								bookingBusinessLayer.modifyBooking(id,newBooking);
								bookingSystemPanel.replaceBookingInList(newBooking); //pass id of booking too replace
							} else {
								MessageBox.errorMessageBox("Please enter all of the required details for booking");
							}
						}
					} else {
						MessageBox.warningMessageBox("Please select a booking to modify");
					}
				} catch (Exception e) {
					MessageBox.errorMessageBox(e.toString());
				}
				break;
			default:
				System.out.println("control handler not found");
		}

	}

	public Date stringToDate(int bookingId, String stringToConvert) {
		String s = stringToConvert.replaceAll("-",".");
		try {
			return BOOKING_DATE_FORMAT.parse(s);
		} catch (ParseException e) {
			listOfBadBookingIDs.add(bookingId);
			return new Date();

		} catch (Exception e) {
			return null;
		}
	}

	public Date stringToTime(int bookingId, String unVerifiedStringToConvert, boolean collectionTime) throws Exception {

		String strippedUnverifiedStringToConvert = unVerifiedStringToConvert.replaceAll("[a-zA-z ]", "");

		String verifiedStringToConvert = "unused";

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
		} catch (NumberFormatException ep) {
			return (Date) BOOKING_TIME_FORMAT.parse(verifiedStringToConvert);
		} catch (Exception e) { //last choice - notify user that the date was in the incorrect format and add a new date
			listOfBadBookingIDs.add(bookingId);
			return new Date();
		}
	}


	public void populateBadBookingMessageBox() {
		String s = "Booking ID: ";
		if (!listOfBadBookingIDs.isEmpty()) {
				for (int i : listOfBadBookingIDs) {
				s += i + ", ";
			}
			s+=" have/has incorrectly formatted date(s).";
			listOfBadBookingIDs.clear();
			MessageBox.warningMessageBox(s);
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
