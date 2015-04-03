package com.bookingsystem.controller.handler;


import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UILoginPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Author: [Alex] on [$Date]
 */

public class LoginHandler implements ActionListener {
    private Account accountModel; // only need to instantiate it when used
    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private AccountBusinessLayer accountBusinessLayer;

    public LoginHandler(BookingSystemUILoader view)  {
        this.view = view;
        loginPanel = view.getLoginPanel();
        try {
            accountBusinessLayer = new AccountBusinessLayer();
        } catch (IOException e) {
            MessageBox.errorMessageBox(e.toString());
        } catch (ClassNotFoundException e) {
            MessageBox.errorMessageBox(e.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        switch (arg0.getActionCommand()) {
            case "Login":
                String username, unHashedPassword;
                username = loginPanel.GetLoginUsernameText();
                unHashedPassword = loginPanel.GetLoginPasswordText();

                accountModel = new Account(0, 0, username, unHashedPassword);
                try {
                    if (accountBusinessLayer.getAccount(accountModel)) {
                        view.removeLoginPanel();
                        view.showBookingSystemPanel();
                        view.setVisible(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "Clear":
                loginPanel.ClearTextBoxes();
                break;
        }
    }
}
