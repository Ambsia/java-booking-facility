package com.bookingsystem.helpers;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public final class TextFieldRestriction extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3357149113977337182L;
	// fields
	private final int limit;

	/**
	 * Constructor used to set the limit to 3, do not offer a parameter due to
	 * all of the text fields using this document require the same treatment.
	 * */
	public TextFieldRestriction() {
		this.limit = 2;
	}

	/**
	 * void insertString, overridden to allow some specific rules to be met,
	 * this method will check if s is null, try to parse s to an integer and
	 * check if the limit is reached.
	 * */
	@Override
	public void insertString(int offset, String s,
			javax.swing.text.AttributeSet a) throws BadLocationException {
		if (s == null)
			return;
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			MessageBox.errorMessageBox("These fields only accept integers.");
			super.remove(offset, 4);
		}

		if ((getLength() + s.length()) <= limit) {
			super.insertString(offset, s, a);
		}
	}
}
