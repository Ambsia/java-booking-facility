package com.bookingsystem.view.panelparts.controlpanes;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemAddPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemEditPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemFindPanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemRemovePanel;
import com.bookingsystem.view.dialogpanels.bookingdialog.UIBookingSystemShowBookingsFound;

/**
 * Author: [Alex] on [$Date]
 */
public class UIBookingSystemBookingControlPanel extends UIBookingSystemControlPanel {

    private final UIBookingSystemAddPanel uiBookingSystemAddPanel;
    private final UIBookingSystemFindPanel uiBookingSystemFindPanel;
    private final UIBookingSystemEditPanel uiBookingSystemEditPanel;
    private final UIBookingSystemRemovePanel uiBookingSystemRemovePanel;
    private final UIBookingSystemShowBookingsFound uiBookingSystemShowBookingsFound;

    public UIBookingSystemBookingControlPanel() {
        super();
        setLayout(new GridBagLayout());
        setButtonNames(new String[] {"Load","Search","Complete","Add","Edit", "Remove","Export", "Today's", "Tomorrows"});
        setButtonDimension(new Dimension(100,25));
        createControlPanel();

        uiBookingSystemAddPanel = new UIBookingSystemAddPanel();
        uiBookingSystemFindPanel = new UIBookingSystemFindPanel();
        uiBookingSystemEditPanel = new UIBookingSystemEditPanel();
        uiBookingSystemRemovePanel = new UIBookingSystemRemovePanel();
        uiBookingSystemShowBookingsFound = new UIBookingSystemShowBookingsFound();
    }



    public UIBookingSystemEditPanel getUIBookingSystemEditPanel() {
        return uiBookingSystemEditPanel;
    }

    public UIBookingSystemAddPanel getUIBookingSystemAddPanel() {
        return uiBookingSystemAddPanel;
    }

    public UIBookingSystemFindPanel getUIBookingSystemFindPanel() {
        return uiBookingSystemFindPanel;
    }

    public UIBookingSystemRemovePanel getUIBookingSystemRemovePanel() {
        return uiBookingSystemRemovePanel;
    }

    public UIBookingSystemShowBookingsFound getUIBookingSystemShowBookingsFound() { return uiBookingSystemShowBookingsFound; }
}

