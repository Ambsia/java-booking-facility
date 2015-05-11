package  com.bookingsystem.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UILoginPanel extends JPanel {

	private final JTextField txtLoginUsername;
	private final JTextField txtLoginPassword;
	private final JButton btnLogin;
	private final JButton btnClear;

	public UILoginPanel() {
		JLabel lblLoginUsername = new JLabel("Username:");
		JLabel lblLoginPassword = new JLabel("Password:");
		txtLoginUsername = new JTextField(10);
		txtLoginPassword = new JPasswordField(10);
		btnLogin = new JButton("Login");
		btnClear = new JButton("Clear");
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 10, 2);
		this.add(lblLoginUsername, gbc);
		gbc.gridx = 1;// new column
		this.add(txtLoginUsername, gbc);
		gbc.gridy = 1; // new row
		gbc.gridx = 0;
		this.add(lblLoginPassword, gbc);
		gbc.gridx = 1; // new column
		this.add(txtLoginPassword, gbc);
		gbc.gridy = 2; // new row
		gbc.gridx = 0;
		this.add(btnLogin, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		this.add(btnClear, gbc);
	}

	public void addSubmitListener(ActionListener al) {
		btnLogin.addActionListener(al);
	}
	
	public void addClearListener(ActionListener al) {
		btnClear.addActionListener(al);
	}

	public String getLoginUsernameText() {
		return txtLoginUsername.getText();
	}

	public String getLoginPasswordText() {
		return txtLoginPassword.getText();
	}
	
	public void clearTextBoxes() {
		this.txtLoginPassword.setText("");
		this.txtLoginUsername.setText("");
	}

}
