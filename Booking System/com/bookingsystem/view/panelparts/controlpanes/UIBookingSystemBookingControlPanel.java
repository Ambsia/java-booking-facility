package com.bookingsystem.view.panelparts.controlpanes;

import com.bookingsystem.view.dialogpanels.bookingdialog.*;

import java.awt.*;

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
        setButtonNames(new String[] {"Load","Search","Complete","Add","Edit", "Remove","Archive", "Today's", "Tomorrows"});
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

