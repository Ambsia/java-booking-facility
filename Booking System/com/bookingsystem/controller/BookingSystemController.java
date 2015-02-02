package com.bookingsystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bookingsystem.model.Account;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UIBookingSystemPanel;
import com.bookingsystem.view.UILoginPanel;

public class BookingSystemController {

	private BookingSystemUILoader view;
	private UILoginPanel loginPanel;
	private UIBookingSystemPanel bookingSystemPanel;

	private Account accountModel; // only need to instantiate it when used

	private boolean loggedInSuccessful;

	// account not instantiated until logged in or created!
	public BookingSystemController(BookingSystemUILoader view) {
		this.view = view;
		view.showLoginPanel();

		bookingSystemPanel = view.getBookingSystemPanel();
		loginPanel = view.getLoginPanel();

		loginPanel.addSubmitListener(new LoginHandler());
		loginPanel.addClearListener(new ClearHandler());
		
		bookingSystemPanel = view.getBookingSystemPanel();
		
		bookingSystemPanel.addSubmitListener(new AddBookingHandler());
	}

	public void RegisterAccount() {

	}
	
	public class AddBookingHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			///List<Equipment> equipment = new ArrayList<Equipment>();
			//Booking booking = new Booking(0,"","","","",equipment,"");
			
			//bookingSystemPanel.addBookingToList(booking);
			try {
				File f = new File("C:\\Users\\boseleya\\Documents\\GitHub\\java-bs\\2014-15 SETUPS.xlsx");
				//FileOutputStream stream = new FileOutputStream(f);
			    XSSFWorkbook wb = new XSSFWorkbook(f);
			    XSSFSheet sheet = wb.getSheetAt(0);
			    XSSFRow row;
			    XSSFCell cell;

			    int rows; // No of rows
			    rows = sheet.getPhysicalNumberOfRows();

			    int cols = 0; // No of columns
			    
			    
			    int tmp = 0;

			    // This trick ensures that we get the data properly even if it doesn't start from first few rows
			    for(int i = 0; i < 10 || i < rows; i++) {
			    	
			        row = sheet.getRow(i);
			        if(row != null) {
			            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			            if(tmp > cols) cols = tmp;
			        }
			    }

			    for(int r = 0; r < rows; r++) {
			        row = sheet.getRow(r);
			        if(row != null) {
			            for(int c = 0; c < cols; c++) {
			                cell = row.getCell((short)c);
			                System.out.println("cell");
			                if(cell != null) {
			                    System.out.println(cell.toString());
			                }
			            }
			        }
			    }
			} catch(Exception ioe) {
			    ioe.printStackTrace();
			}

		}
	}

	public class LoginHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String username, unHashedPassword;
			username = loginPanel.GetLoginUsernameText();
			unHashedPassword = loginPanel.GetLoginPasswordText();

			accountModel = new Account(0, 0, username, unHashedPassword);
			loggedInSuccessful = accountModel.login();
			System.out.println(accountModel.toString() + loggedInSuccessful);

			if (loggedInSuccessful) {
				view.removeLoginPanel();
				view.showBookingSystemPanel();
				view.setVisible(true);
			}
		}
	}

	public class ClearHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			loginPanel.ClearTextBoxes();
		}
	}
}
