package  com.bookingsystem.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UILoginPanel extends JPanel {

	private JTextField txtLoginUsername;
	private JTextField txtLoginPassword;
	private JLabel lblLoginUsername;
	private JLabel lblLoginPassword;
	private JButton btnLogin;
	private JButton btnClear;

	public UILoginPanel() {
		lblLoginUsername = new JLabel("Username:");
		lblLoginPassword = new JLabel("Password:");
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

	public String GetLoginUsernameText() {
		return txtLoginUsername.getText();
	}

	public String GetLoginPasswordText() {
		return txtLoginPassword.getText();
	}
	
	public void ClearTextBoxes() {
		this.txtLoginPassword.setText("");
		this.txtLoginUsername.setText("");
	}

}
