package com.bookingsystem.view.controls;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.tablemodel.AccountTableModel;

import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class UIBookingSystemJTableAccounts extends UIBookingSystemJTable {
	private final AccountTableModel accountTableModel;

	public UIBookingSystemJTableAccounts(AccountTableModel accountTableModel) {
		super();
		this.accountTableModel = accountTableModel;
		this.setModel(this.accountTableModel);
	}

	@Override
	public void addArrayOfRowsToList(ArrayList<Object> arrayList) {
		for (Object object : arrayList) {
			addRowToList(object);
		}
	}

	@Override
	public void addRowToList(Object data) {
		Account account = (Account) data;
		accountTableModel.addRow(new Object[]{account.getUserID(),
				account.getUsername(),
				account.getUserLevel() });
	}

	@Override
	public Object getRowFromList(int identifierOfData) {
		if (identifierOfData >= 0 && identifierOfData != (int) accountTableModel.getValueAt(0, identifierOfData)) {
			return new Account((int) accountTableModel.getValueAt(0, identifierOfData),
					(int) accountTableModel.getValueAt(1, identifierOfData),
					(String) accountTableModel.getValueAt(2, identifierOfData),
					"*******");
		}
		else return null;
	}

	@Override
	public void replaceRowInList(Object rowData) {
		Account newAccount = (Account) rowData;
		accountTableModel.setValueAt(newAccount.getUserID(), this.getSelectedRow(), 0);
		accountTableModel.setValueAt(newAccount.getUsername(), this.getSelectedRow(), 1);
		accountTableModel.setValueAt(newAccount.getUserLevel(), this.getSelectedRow(), 2);
	}

	@Override
	public void removeRowFromList() {
		if (this.getSelectedRow() < accountTableModel.getRowCount() && this.getSelectedRow() >= 0) {
			accountTableModel.removeRow(this.getSelectedRow());
		} else {
			throw new IndexOutOfBoundsException("Index does not exist");
		}
	}

	@Override
	public void removeAllRowsFromList() {
		int rowCount = accountTableModel.getRowCount();
		for (int i = rowCount-1;i>=0;i--) {
			accountTableModel.removeRow(i);
		}
	}

}
