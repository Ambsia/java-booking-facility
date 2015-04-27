package  com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;

public final class BookingSystemMain {

	private final BookingSystemUILoader view;

	private final BookingSystemController controller;

	private final BookingBusinessLayer bookingBusinessLayer;

	private final LoggerBusinessLayer loggerBusinessLayer;

	private final AccountBusinessLayer accountBusinessLayer;


	public final static void main(String[] args) {
		new BookingSystemMain();
	}

	private BookingSystemMain() {
		this.view = new BookingSystemUILoader();

		this.bookingBusinessLayer = new BookingBusinessLayer();

		this.loggerBusinessLayer = new LoggerBusinessLayer();

		this.accountBusinessLayer = new AccountBusinessLayer();

		this.controller = new BookingSystemController(view,bookingBusinessLayer, accountBusinessLayer,loggerBusinessLayer);
	}

	public BookingSystemUILoader getView() {
		return view;
	}

	public BookingSystemController getController() {
		return controller;
	}

	public BookingBusinessLayer getModel() { return bookingBusinessLayer; }
}
