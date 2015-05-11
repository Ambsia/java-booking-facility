package com.bookingsystem.helpers;

import javax.swing.*;
/** This class is used to implement two static methods that take a message
 * and opens a message dialog with different icons, such as error or information. 
 * These methods are static so they are able to be called upon within the view, controller or model package. 
 * */
public final class MessageBox {

    /** static void, warningMessageBox takes a string and passes it to another method which shows the message dialog,
     * also this method will set the title of the message dialog to "Information",
     * the constant integer will be set to INFORMATION_MESSAGE (used to set icon).
     * @param message, the message that needs to be output within the message box dialog.
     * */
	public static void warningMessageBox(String message) {
		show(message, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	/** static void, infoMessageBox takes a string and passes it to another method which shows the message dialog,
	 * also this method will set the title of the message dialog to "Information",
     * the constant integer will be set to INFORMATION_MESSAGE (used to set icon).
	 * @param message, the message that needs to be output within the message box dialog.
	 * */
    public static void infoMessageBox(String message) {
        show(message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /** static void, errorMessageBox takes a string and passes it to another method which shows the message dialog, 
     * also this method will append "Error; " to the start of every message and set the title of the message dialog to "Error",
     * the constant integer will be set to ERROR_MESSAGE (used to set icon).
	 * @param message, the message that needs to be output within the message box dialog.
	 * */
    public static void errorMessageBox(String message) {
        show("Error; " + message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /** static void, show takes a string as a message, a string as a title and a integer as a iconId,
     * then will call upon JOptionPane to show a message dialog using the parameters as output data.
     * @param message, the message to output to the user.
     * @param title, the title of the message dialog.
     * @param iconId, the id of the icon to set the message dialog as.*/
    private static void show(String message, String title, int iconId) {
        JOptionPane.showMessageDialog(null, message, title, iconId);
    }

}
