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
        accountBusinessLayer = new AccountBusinessLayer();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        switch (arg0.getActionCommand()) {
            case "Login":

                try {
                    accountModel = accountBusinessLayer.retrieveAccount(loginPanel.getLoginUsernameText(),loginPanel.getLoginPasswordText() );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(accountBusinessLayer.isAccountFound()) {
                    view.removeLoginPanel();
                    view.showBookingSystemPanel();
                    view.setVisible(true);
                } else {
                    MessageBox.errorMessageBox("Incorrect password or login.");
                }
                break;

            case "Clear":
                loginPanel.clearTextBoxes();
                break;

          //  case "Clear":
           //     Account account = new Account(0,0,"do" , "donkey");
           //     accountBusinessLayer.insertAccount(account);
           //     break;
        }
    }
}
