package com.bookingsystem.controller.handler;

import com.bookingsystem.model.Account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 01/03/2015.
 */
public class LoginHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String username, unHashedPassword;
        username = loginPanel.GetLoginUsernameText();
        unHashedPassword = loginPanel.GetLoginPasswordText();

        accountModel = new Account(0, 0, username, unHashedPassword);
        loggedInSuccessful = accountModel.login();
        System.out.println(accountModel.toString() + loggedInSuccessful);

        if (loggedInSuccessful) {
            view.removeLoginPanel();
            view.showBookingSystemPanel();
            view.setVisible(true);
        }
    }
}
