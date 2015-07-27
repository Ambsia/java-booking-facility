package com.bookingsystem.view.panelparts.controlpanes;

import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemAccountAddPanel;
import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemChangePasswordPanel;
import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemChangeUserLevel;

import java.awt.*;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminControlPanel extends
        UIBookingSystemControlPanel {

    /**
     *
     */
    private static final long serialVersionUID = -5157872014641211611L;
    private final UIBookingSystemAccountAddPanel bookingSystemAccountAddPanel;
    private final UIBookingSystemChangePasswordPanel bookingSystemChangePasswordPanel;
    private final UIBookingSystemChangeUserLevel bookingSystemChangeUserLevel;

    public UIBookingSystemAdminControlPanel() {
        super();
        setLayout(new GridBagLayout());
        setButtonNames(new String[]{"Load Accounts", "Add Account",
                "Remove Account", "View Activity", "Change Password",
                "Change Level"});
        setButtonDimension(new Dimension(138, 25));
        createControlPanel();

        bookingSystemAccountAddPanel = new UIBookingSystemAccountAddPanel();
        bookingSystemChangePasswordPanel = new UIBookingSystemChangePasswordPanel();
        bookingSystemChangeUserLevel = new UIBookingSystemChangeUserLevel();
    }

    public UIBookingSystemChangePasswordPanel getBookingSystemChangePasswordPanel() {
        return bookingSystemChangePasswordPanel;
    }

    public UIBookingSystemChangeUserLevel getBookingSystemChangeUserLevel() {
        return bookingSystemChangeUserLevel;
    }

    public UIBookingSystemAccountAddPanel getBookingSystemAccountAddPanel() {
        return bookingSystemAccountAddPanel;
    }

}
