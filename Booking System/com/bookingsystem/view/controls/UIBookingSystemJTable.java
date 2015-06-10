package com.bookingsystem.view.controls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JTable;

/**
 * Author: [Alex]
 */
public abstract class UIBookingSystemJTable extends JTable implements BookingSystemJTableInterface<Object> {


	 /**
	 * 
	 */
	private static final long serialVersionUID = 5534068596222951987L;
	static final DateFormat BOOKING_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
	 static final DateFormat BOOKING_DATE_FORMAT_2 = new SimpleDateFormat("dd.MMM.yy", Locale.ENGLISH);
	 static final DateFormat BOOKING_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	 static final DateFormat BOOKING_TIME_FORMAT_2 = new SimpleDateFormat("H", Locale.ENGLISH);
	 static final DateFormat BOOKING_TIME_FORMAT_3 = new SimpleDateFormat("(HH:mm)", Locale.ENGLISH);
	UIBookingSystemJTable() {
		this.getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public int getIDWithIndex(int index) {
		if (index >= 0) {
			return this.getValueAt((index), 0) != null ? (int) this.getValueAt((index), 0) : -1;
		}
		return  -1;
	}

	public int getIDOfSelectedRow() {
		if (this.getSelectedRow() != -1) {
			return this.getValueAt(this.getSelectedRow(), 0) != null ? (int) this.getValueAt(this.getSelectedRow(), 0) : -1;
		}
		return -1;
	}
	protected Date stringToDate(String stringToConvert) {
		String s = stringToConvert.replaceAll("[^A-Za-z0-9. ]", "."); //will replace all non-alpha characters with a period
		try {
			return BOOKING_DATE_FORMAT.parse(s); //tries to match the string with the format dd.MM.yy
		} catch (ParseException parseException_1) {
			try {
				return BOOKING_DATE_FORMAT_2.parse(s); //tries to match the string with teh format dd.MMM.yy
			} catch (ParseException parseException_2) {
//                if (!listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
//                    listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed);
//                }
			}
		}
		return new Date();
	}
	public ArrayList<String> getSelectedRowAsStringArrayList() {
		ArrayList<String> data = new ArrayList<>();
		if (this.getSelectedRow() != -1) {
			for (int i = 0; i < this.getColumnCount(); i++) {
				data.add(this.getValueAt(this.getSelectedRow(), i).toString());
			}
			return data;
		} else { //adding empty strings, easier for the view panel to process, and we do not have to deal with nulls
			for (int i = 0; i < this.getColumnCount(); i++) {
				data.add("");
			}
			return data;
		}
	}

	protected Date stringToTime(String unVerifiedStringToConvert, boolean collectionTime) {
		String lstrippedUnverifiedStringToConvert = unVerifiedStringToConvert.replaceAll("[a-zA-z ]", "");
		String strippedUnverifiedStringToConvert = lstrippedUnverifiedStringToConvert.replaceAll("[?().]", "");
		int test = 0;
		String verifiedStringToConvert;
		if (!strippedUnverifiedStringToConvert.contains("-")) {
			verifiedStringToConvert = strippedUnverifiedStringToConvert;
		} else {
			if (!collectionTime) {
				verifiedStringToConvert = strippedUnverifiedStringToConvert.substring(0, strippedUnverifiedStringToConvert.indexOf('-'));
			} else {
				verifiedStringToConvert = strippedUnverifiedStringToConvert.substring(strippedUnverifiedStringToConvert.indexOf('-') + 1, strippedUnverifiedStringToConvert.length());
				if (verifiedStringToConvert.equals("")) {
					verifiedStringToConvert = strippedUnverifiedStringToConvert.replace("-", "");
				}
			}
		}
		try { //check if i can parse to long, if not work another way of converting the unverified string into a usable date format..
			long dayMs = Long.parseLong(unVerifiedStringToConvert);
			return new Date(dayMs);
		} catch (NumberFormatException nfe) {
			try {
				return BOOKING_TIME_FORMAT.parse(verifiedStringToConvert);
			} catch (ParseException parseException_1) {
				try {
					return BOOKING_TIME_FORMAT_3.parse(verifiedStringToConvert);
				} catch (ParseException parseException_2) {
					try {
						test = Integer.parseInt(verifiedStringToConvert);
						if (test > 24 && test % 10 == 0) {
							test = test / 10;
						}
						if (test + 12 < 24) {
							if (test >= 12) {
								test = test + 12;
							}
						}
						verifiedStringToConvert = Integer.toString(test);
						return BOOKING_TIME_FORMAT_2.parse(verifiedStringToConvert);
					} catch (Exception parseException_3) {
//                        if (!listOfBadBookingIDs.contains(this.bookingIDCurrentlyBeingProcessed)) {
//                            listOfBadBookingIDs.add(this.bookingIDCurrentlyBeingProcessed);
//                        }
					}
				}
			}
		}
		return  new Date();
	}
}
