package com.bookingsystem.controller.handler;

import com.bookingsystem.model.businessmodel.*;
import com.bookingsystem.view.BookingSystemUILoader;
import org.apache.commons.collections.IteratorUtils;

/**
 * Created by Alex on 04/05/2015
 */
public class Handler {
	// we will initially call handler passing all business logic over, then we
	// will start calling specific logic classes

	private final AccountBusinessLayer accountBusinessLayer;
	private final AccountManagementBusinessLayer accountManagementBusinessLayer;
	private final BookingBusinessLayer bookingBusinessLayer;
	private final LoggerBusinessLayer loggerBusinessLayer;
	private final ArchiveBusinessLayer archiveBusinessLayer;
	private final BookingSystemUILoader view;

	public Handler(AccountBusinessLayer accountBusinessLayer,
			AccountManagementBusinessLayer accountManagementBusinessLayer,
			BookingBusinessLayer bookingBusinessLayer,
			LoggerBusinessLayer loggerBusinessLayer,
			BookingSystemUILoader view,
			ArchiveBusinessLayer archiveBusinessLayer) {
		this.accountBusinessLayer = accountBusinessLayer;
		this.accountManagementBusinessLayer = accountManagementBusinessLayer;
		this.bookingBusinessLayer = bookingBusinessLayer;
		this.loggerBusinessLayer = loggerBusinessLayer;
		this.archiveBusinessLayer = archiveBusinessLayer;
		this.view = view;
	}

	public BookingSystemUILoader getView() {
		return view;
	}

	public AccountBusinessLayer getAccountBusinessLayer() {
		return accountBusinessLayer;
	}

	public AccountManagementBusinessLayer getAccountManagementBusinessLayer() {
		return accountManagementBusinessLayer;
	}

	public BookingBusinessLayer getBookingBusinessLayer() {
		return bookingBusinessLayer;
	}

	public LoggerBusinessLayer getLoggerBusinessLayer() {
		return loggerBusinessLayer;
	}

	public ArchiveBusinessLayer getArchiveBusinessLayer() {
		return archiveBusinessLayer;
	}

	void initialiseDialogs() {
		view.getBookingSystemTabbedPane()
				.getBookingSystemPanel()
				.getBookingSystemControlPanel()
				.getUIBookingSystemAddPanel()
				.setEquipmentJComboBox(
						IteratorUtils.toList(bookingBusinessLayer
								.getEquipments().iterator()));
		view.getBookingSystemTabbedPane()
				.getBookingSystemPanel()
				.getBookingSystemControlPanel()
				.getUIBookingSystemEditPanel()
				.setEquipmentJComboBox(
						IteratorUtils.toList(bookingBusinessLayer
								.getEquipments().iterator()));
		view.getBookingSystemTabbedPane()
				.getBookingSystemPanel()
				.getBookingSystemControlPanel()
				.getUIBookingSystemFindPanel()
				.setEquipmentJComboBox(
						IteratorUtils.toList(bookingBusinessLayer
								.getEquipments().iterator()));
	}
	// will be the abstract class for all handlers, implementing interfaces and
	// generic methods here
	// all business logic delegated from here
	// all view controls also delegated from here
}
