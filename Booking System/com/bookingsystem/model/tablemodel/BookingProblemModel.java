package com.bookingsystem.model.tablemodel;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Alex on 24/05/2015
 */
public class BookingProblemModel extends  DefaultTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9108893724997824892L;

	public BookingProblemModel() {
        addColumn("ID");
        addColumn("Day");
        addColumn("Holder");
        addColumn("Location");
        addColumn("Equipment");
    }
}

