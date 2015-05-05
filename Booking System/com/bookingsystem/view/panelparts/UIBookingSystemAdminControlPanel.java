package com.bookingsystem.view.panelparts;

import com.bookingsystem.view.dialogpanels.accountdialog.UIBookingSystemAccountAddPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class UIBookingSystemAdminControlPanel extends JPanel {

	private final ArrayList<JButton> controlButtonList;

	private UIBookingSystemAccountAddPanel bookingSystemAccountAddPanel;

	public UIBookingSystemAdminControlPanel() {
		setLayout(new GridBagLayout());
		bookingSystemAccountAddPanel = new UIBookingSystemAccountAddPanel();
		String[] buttonNames = {"Load Accounts","Add Account","Remove Account","View Activity", "View Exceptions", ""};
		controlButtonList = new ArrayList<>();
		Dimension buttonDimension;
		for ( int buttonNo = 0, colsPassed = 0, rowsPassed = 0; buttonNo<buttonNames.length;buttonNo++) {
			JPanel jPanel = new JPanel();
			JButton jButton = new JButton(buttonNames[buttonNo]);
			int length = 40;//buttonNames[buttonNo].length() < 13 ? buttonNames[buttonNo].length() +5 : buttonNames[buttonNo].length() + 30;
			buttonDimension = new Dimension(95 + length, 25);
			jButton.setPreferredSize(buttonDimension);

			if (colsPassed == 3) { rowsPassed++; colsPassed = 0; }

			addControlToPanel(jPanel, colsPassed++, rowsPassed);
			controlButtonList.add(jButton);
			jPanel.add(controlButtonList.get(buttonNo));
		}
	}

	void addControlToPanel(Component component, int gridX, int gridY) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(component, gbc);
	}

	public void addListeners(ActionListener al) {
		for (JButton button : controlButtonList) {
			button.addActionListener(al);
		}
	}

	public UIBookingSystemAccountAddPanel getBookingSystemAccountAddPanel() {
		return bookingSystemAccountAddPanel;
	}

}
