package com.bookingsystem.controller.handler;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

/**
 * Author: [Alex] on [$Date]
 */

public class LoginHandler implements ActionListener {
    private Account accountModel;
    private Handler handler;

    public LoginHandler(Handler handler)   {
        this.handler = handler;
        accountModel = null;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Log log = new Log(arg0.getActionCommand(),this.getClass().getSimpleName(), new Date());
        switch (arg0.getActionCommand()) {
            case "Login":
               //accountModel = handler.getAccountBusinessLayer().retrieveAccount(handler.getView().getLoginPanel().getLoginUsernameText(), handler.getView().getLoginPanel().getLoginPasswordText());
               accountModel = handler.getAccountBusinessLayer().retrieveAccount("alex", "donkey");
                if (handler.getAccountBusinessLayer().isAccountFound()) {
                    handler.getLoggerBusinessLayer().setAccountCurrentlyLoggedIn(accountModel);
                    handler.getView().removeLoginPanel();
                    handler.getView().showBookingSystemPanel();
                    if (accountModel != null && accountModel.getUserLevel() > 2) {
                        handler.getView().getBookingSystemTabbedPane().showAdminPanel();
                    }
                    handler.getView().setVisible(true);
                } else {
                    MessageBox.errorMessageBox("Incorrect password or login.");
                }
                break;
            case "Clear":
                handler.getView().getLoginPanel().clearTextBoxes();
                break;
            default:
                System.out.println("handler not found for " + arg0.getActionCommand());
                break;
        }
        if (accountModel != null) {
            handler.getLoggerBusinessLayer().insertLog(log);
        }
    }
}
