package com.bookingsystem.controller.handler;


import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;
import com.bookingsystem.model.businessmodel.AccountBusinessLayer;
import com.bookingsystem.model.businessmodel.LoggerBusinessLayer;
import com.bookingsystem.view.BookingSystemUILoader;
import com.bookingsystem.view.UILoginPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Author: [Alex] on [$Date]
 */

public class LoginHandler implements ActionListener {
    private Account accountModel; // only need to instantiate it when used
    private BookingSystemUILoader view;
    private UILoginPanel loginPanel;
    private AccountBusinessLayer accountBusinessLayer;

    private LoggerBusinessLayer loggerBusinessLayer;

    public LoginHandler(AccountBusinessLayer accountBusinessLayer, BookingSystemUILoader view, LoggerBusinessLayer loggerBusinessLayer)  {
        this.view = view;
        this.loggerBusinessLayer = loggerBusinessLayer;
        loginPanel = view.getLoginPanel();
        this.accountBusinessLayer = accountBusinessLayer;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Log log = new Log(arg0.getActionCommand(),this.getClass().toString(), new Date());
        switch (arg0.getActionCommand()) {
            case "Login":
                //accountModel = accountBusinessLayer.retrieveAccount(loginPanel.getLoginUsernameText(), loginPanel.getLoginPasswordText());
                accountModel = accountBusinessLayer.retrieveAccount("alex", "donkey");
                if (accountBusinessLayer.isAccountFound()) {
                    loggerBusinessLayer.setAccountCurrentlyLoggedIn(accountModel);
                    view.removeLoginPanel();
                    view.showBookingSystemPanel();
                    if (accountModel != null && accountModel.getUserLevel() == 3) {
                        view.getBookingSystemTabbedPane().showAdminPanel();
                    }
                    view.setVisible(true);
                } else {
                    MessageBox.errorMessageBox("Incorrect password or login.");
                }
                break;

            case "Clear":
                loginPanel.clearTextBoxes();
                break;
            default:
                System.out.println("handler not found for " + arg0.getActionCommand());
                break;
        }
        if (accountModel != null) {
            loggerBusinessLayer.insertLog(log);
        }
    }
}
