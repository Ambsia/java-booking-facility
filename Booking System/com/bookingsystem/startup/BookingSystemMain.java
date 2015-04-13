package  com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;

public final class BookingSystemMain {

	private final BookingSystemUILoader view;

	private final BookingSystemController controller;

	private final BookingBusinessLayer model;

	public final static void main(String[] args) {
		new BookingSystemMain();
	}

	private BookingSystemMain() {
		this.view = new BookingSystemUILoader();

		this.model = new BookingBusinessLayer();

		this.controller = new BookingSystemController(view, model);
	}

	public BookingSystemUILoader getView() {
		return view;
	}

	public BookingSystemController getController() {
		return controller;
	}

	public BookingBusinessLayer getModel() { return model; }
}
