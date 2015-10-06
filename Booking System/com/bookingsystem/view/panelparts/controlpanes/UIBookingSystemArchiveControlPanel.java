package com.bookingsystem.view.panelparts.controlpanes;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 30/05/2015
 */
public class UIBookingSystemArchiveControlPanel extends
        UIBookingSystemControlPanel {
    /**
     *
     */
    private static final long serialVersionUID = 3009865919675018585L;

    public UIBookingSystemArchiveControlPanel() {
        super();
        
        setLayout(new GridBagLayout());
        setButtonNames(new String[]{"Refresh", "View Statistics"});
        setButtonDimension(new Dimension(125, 25));
        createControlPanel();
    }

    @Override
    public void addListeners(ActionListener al) {
        super.addListeners(al);
    }

}
