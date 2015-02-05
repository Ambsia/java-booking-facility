package  com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.view.BookingSystemUILoader;

public final class BookingSystemMain {

	private final BookingSystemUILoader view;

	private final BookingSystemController controller;

	public static void main(String[] args) {
		new BookingSystemMain();
	}

	private BookingSystemMain() {
		System.out.println("loaded");
		this.view = new BookingSystemUILoader();

		this.controller = new BookingSystemController(view);
	}

	public BookingSystemUILoader GetView() {
		return view;
	}

	public BookingSystemController GetController() {
		return controller;
	}
}
