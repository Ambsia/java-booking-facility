package  com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;

final class BookingSystemMain {

	private final BookingSystemUILoader view;

	private final BookingSystemController controller;

	private final BookingBusinessLayer bookingBusinessLayer;


	public static void main(String[] args) {
		new BookingSystemMain();
	}

	private BookingSystemMain() {
		this.view = new BookingSystemUILoader();

		this.bookingBusinessLayer = new BookingBusinessLayer();

		LoggerBusinessLayer loggerBusinessLayer = new LoggerBusinessLayer();

		AccountBusinessLayer accountBusinessLayer = new AccountBusinessLayer();

		this.controller = new BookingSystemController(view,bookingBusinessLayer, accountBusinessLayer, loggerBusinessLayer);
	}

	public BookingSystemUILoader getView() {
		return view;
	}

	public BookingSystemController getController() {
		return controller;
	}

	public BookingBusinessLayer getModel() { return bookingBusinessLayer; }
}
