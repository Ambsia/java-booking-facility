package  com.bookingsystem.startup;

import com.bookingsystem.controller.BookingSystemController;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.AccountManagementBusinessLayer;
import com.bookingsystem.model.businessmodel.ArchiveBusinessLayer;
import com.bookingsystem.model.businessmodel.BookingBusinessLayer;
import com.bookingsystem.model.businessmodel.BusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;

final class BookingSystemMain {

	private final BookingSystemUILoader view;

	private final BookingSystemController controller;

	private final BookingBusinessLayer bookingBusinessLayer;

	private final  LoggerBusinessLayer loggerBusinessLayer;

	private final  AccountBusinessLayer accountBusinessLayer;

	private final AccountManagementBusinessLayer accountManagementBusinessLayer;

	private final ArchiveBusinessLayer archiveBusinessLayer;

	private final BusinessLayer businessLayer;


	public static void main(String[] args) {
		new BookingSystemMain();
	}

	private BookingSystemMain() {
		this.view = new BookingSystemUILoader();
		this.bookingBusinessLayer = new BookingBusinessLayer();
		this.loggerBusinessLayer = new LoggerBusinessLayer();
		this.accountBusinessLayer = new AccountBusinessLayer();
		this.accountManagementBusinessLayer = new AccountManagementBusinessLayer();
		this.archiveBusinessLayer = new ArchiveBusinessLayer();
		this.businessLayer = new BusinessLayer();
		this.controller = new BookingSystemController(view,bookingBusinessLayer, accountBusinessLayer, loggerBusinessLayer,accountManagementBusinessLayer, archiveBusinessLayer);
	}

	public BookingSystemUILoader getView() {
		return view;
	}

	public BookingSystemController getController() {
		return controller;
	}

	public BookingBusinessLayer getModel() { return bookingBusinessLayer; }
}
