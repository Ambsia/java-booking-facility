package com.bookingsystem.view.controls;

import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public interface BookingSystemJTableInterface<Object> {

    public void addArrayOfRowsToList(ArrayList<Object> arrayListOfData);

    public void addRowToList(Object data);

    public Object getRowFromList(int identifierOfData);

    public void replaceRowInList(Object rowData);

    public void removeRowFromList();

    public void removeAllRowsFromList();
}
