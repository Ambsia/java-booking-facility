package com.bookingsystem.model.tablemodel;

import com.bookingsystem.model.Account;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author: [Alex]
 */
public class AccountTableModel extends AbstractTableModel {
	private static final int COLUMN_NO = 0;
	private static final int COLUMN_ACCOUNT_NAME = 1;
	private static final int COLUMN_ACCOUNT_LEVEL = 2;

	private static String[] columnNames = {"Account ID", "Account Name", "Account Level"};

	private List<Account> accountList;

	public AccountTableModel(List<Account> accountList) {
		super();
		this.accountList = accountList;
		System.out.println(accountList.toString());
		int indexCount = 1;
		for (Account account : accountList) {
			account.setBookingIndex(indexCount++);
		}
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return accountList.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (accountList.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Account account = accountList.get(rowIndex);
		Object returnValue = null;
		switch (columnIndex) {
			case COLUMN_NO:
				returnValue = account.getUserID();
				break;
			case COLUMN_ACCOUNT_NAME:
				returnValue = account.getUsername();
				break;
			case COLUMN_ACCOUNT_LEVEL:
				returnValue = account.getUserLevel();
				break;
			default:
				throw new IllegalArgumentException("Invalid column index");
		}
		return returnValue;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Account account = accountList.get(rowIndex);
		switch (columnIndex) {
			case COLUMN_NO:
				account.setBookingIndex((int) value);
				break;
			default:
				throw new IllegalArgumentException("Invalid column index");
		}
		this.fireTableCellUpdated(rowIndex, columnIndex);

	}

	public void clearAccountList() {
		this.accountList.clear();
		this.fireTableDataChanged();
	}

	public void addAccount(Account account) {
		this.accountList.add(account);
		this.fireTableDataChanged();
	}

	public void addAccountList(List<Account> accountList) {
		for (Account account : accountList) {
			this.accountList.add(account);
			this.fireTableDataChanged();
		}
	}

	public void removeAccount(Account account) {
		this.accountList.remove(account);
		this.fireTableDataChanged();
	}

	public Account getAccount(int id) {
		for (Account account : this.accountList) {
			if (account.getUserID() == id) {
				return account;
			}
		}
		return null;
	}

	public void removeRows(List<Integer> indices) {
		Collections.sort(indices);
		for (int i = indices.size() - 1; i >= 0; i--) {
			this.accountList.remove(indices.get(i).intValue());
			fireTableRowsDeleted(indices.get(i), indices.get(i));
		}
		fireTableDataChanged();
	}

}



