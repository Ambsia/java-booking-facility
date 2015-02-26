package com.bookingsystem.controller.handler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

import javax.swing.JFileChooser;

import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UIBookingSystemControlPanel;
import com.bookingsystem.view.UIBookingSystemPanel;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

public class BookingHandler implements ActionListener {
	private BookingSystemUILoader view;
	private UIBookingSystemPanel bookingSystemPanel;
	private UIBookingSystemControlPanel bookingSystemControlPanel;

	public BookingHandler(UIBookingSystemPanel bookingSystemPanel) {
		this.bookingSystemPanel = bookingSystemPanel;
		bookingSystemControlPanel = this.bookingSystemPanel.getBookingSystemControlPanel();
	}
	@Override
	public void actionPerformed(ActionEvent eventOccurred) {
		String event = eventOccurred.getActionCommand().replace(" ", "");
		System.out.println(event);
		switch (eventOccurred.getActionCommand()) {
			case "Import":
				JFileChooser jFileChooser = new JFileChooser();

				File file;
				FileInputStream fileInputStream;
				XSSFWorkbook workBook = null;
				XSSFSheet sheet = null;
				XSSFRow row;
				int rows = 0;
				Booking importedBooking = null;
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

							sheet = workBook.getSheetAt(1);
							rows = sheet.getPhysicalNumberOfRows();
							System.out.println(rows);
						}
					}

					Color c = Color.white;
					for (int r = 0; r < rows; r++) {
						row = sheet.getRow(r);
						if (row.toString() != "") {
							if (row.getCell((short) 0).toString() != "") {
								importedBooking = new Booking(r,
										row.getCell((short) 0).toString(),
										row.getCell((short) 1).toString(),
										row.getCell((short) 2).toString(),
										row.getCell((short) 3).toString(),
										row.getCell((short) 4).toString(),
										new Equipment(row.getCell((short) 5).toString()));

								bookingSystemPanel
										.addBookingToList(importedBooking,c);
								System.out.println(importedBooking.toString());

							}
						}
					}
				} catch (Exception e) {
					System.out.println("Exception was thrown; " + e.toString());
				} break;
			case "Details":
				System.out.println("details clicked"); break;
			case "Export":
				System.out.println("export clicked"); break;
			case "Add":
				System.out.println("add clicked"); break;
			case "Remove":
				System.out.println("remove clicked");break;
			case "Edit":
				System.out.println("edit clicked");break;
			default:
				System.out.println("control handler not found");

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
