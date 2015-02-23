package  com.bookingsystem.view;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.lang.reflect.Array;

import javax.swing.*;

;

public class BookingSystemUILoader extends JFrame {

	private UILoginPanel loginPanel;
	private UIBookingSystemMenuBarLoader menuBarLoader;
	private UIBookingSystemTabbedPane bookingSystemTabbedPane;

	public BookingSystemUILoader() {
		this.setDimension(500, 250);

		setBackground(Color.DARK_GRAY);
		loginPanel = new UILoginPanel();
		menuBarLoader = new UIBookingSystemMenuBarLoader();
		bookingSystemTabbedPane = new UIBookingSystemTabbedPane();
		setLayout(new BorderLayout());
		this.setTitle("LGS Booking System");
	}

	public void parse(String s) {

	}

	public UIBookingSystemMenuBarLoader getMenuBarLoader() {
		return this.menuBarLoader;
	}

	public void showLoginPanel() {
		this.add(loginPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void showBookingSystemPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		menuBarLoader.setBackground(Color.DARK_GRAY);
		this.setJMenuBar(menuBarLoader);

		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		bookingSystemTabbedPane.setBackground(Color.DARK_GRAY);
		this.add(bookingSystemTabbedPane, gridBagConstraints);

		this.setPreferredSize(new Dimension(1000,500));
		this.setMinimumSize(new Dimension(1000,500));

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void setDimension(int x, int y) {
		Dimension d = new Dimension(x,y);
		this.setSize(d);
		this.setMinimumSize(d);
	}

	public UIBookingSystemTabbedPane getbookingSystemTabbedPane() {
		return bookingSystemTabbedPane;
	}
	
	public void removeBookingSystemTabbedPane() {
		this.remove(bookingSystemTabbedPane);
	}

	public UILoginPanel getLoginPanel() { return loginPanel; }

	public void removeLoginPanel() { this.remove(loginPanel); }




}
