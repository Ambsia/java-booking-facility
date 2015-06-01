package com.bookingsystem.view.panelparts.controlpanes;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 30/05/2015.
 */
public class UIBookingSystemArchiveControlPanel extends UIBookingSystemControlPanel {
    public UIBookingSystemArchiveControlPanel() {
        super();
        setLayout(new GridBagLayout());
        setButtonNames(new String[] {"Load","",""});
        setButtonDimension(new Dimension(100,25));
        createControlPanel();
    }

    public void addListeners(ActionListener al) {
        super.addListeners(al);
    }

}
