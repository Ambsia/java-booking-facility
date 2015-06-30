package  com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.model.businessmodel.*;
import com.bookingsystem.view.BookingSystemUILoader;

final class BookingSystemMain {

	private final BookingSystemUILoader view;

	private final BookingSystemController controller;

	private final BookingBusinessLayer bookingBusinessLayer;

	private final ArchiveBusinessLayer archiveBusinessLayer;

	private final  EquipmentBusinessLayer equipments;


	public static void main(String[] args) {
		new BookingSystemMain();
	}

	private BookingSystemMain() {
		this.view = new BookingSystemUILoader();
		equipments = new EquipmentBusinessLayer();

		this.bookingBusinessLayer = new BookingBusinessLayer(equipments);
		LoggerBusinessLayer loggerBusinessLayer = new LoggerBusinessLayer();
		AccountBusinessLayer accountBusinessLayer = new AccountBusinessLayer();
		AccountManagementBusinessLayer accountManagementBusinessLayer = new AccountManagementBusinessLayer();
		this.archiveBusinessLayer = new ArchiveBusinessLayer();
		this.controller = new BookingSystemController(view,bookingBusinessLayer, accountBusinessLayer, loggerBusinessLayer, accountManagementBusinessLayer, archiveBusinessLayer);
	}

	public BookingSystemUILoader getView() {
		return view;
	}

	public BookingSystemController getController() {
		return controller;
	}

	public BookingBusinessLayer getModel() { return bookingBusinessLayer; }
}
