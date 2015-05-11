package com.bookingsystem.controller.handler;


import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Author: [Alex] on [$Date]
 */

public class LoginHandler implements ActionListener {
    private Account accountModel;
    private Handler handler;

    public LoginHandler(Handler handler) {
        this.handler = handler;
        accountModel = null;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Log log = new Log(arg0.getActionCommand(), this.getClass().getSimpleName(), new Date());
        switch (arg0.getActionCommand()) {
            case "Login":
                // accountModel = accountBusinessLayer.retrieveAccount(loginPanel.getLoginUsernameText(), loginPanel.getLoginPasswordText());
                accountModel = handler.getAccountBusinessLayer().retrieveAccount("alex", "donkey");
                if (handler.getAccountBusinessLayer().getError_code() == 0) { //account found
                    handler.getLoggerBusinessLayer().setAccountCurrentlyLoggedIn(accountModel);
                    handler.getView().removeLoginPanel();
                    handler.getView().showBookingSystemPanel();
                    if (accountModel != null && accountModel.getUserLevel() > 0) {
                        handler.getView().getBookingSystemTabbedPane().showAdminPanel();
                    }
                    handler.getView().setVisible(true);
                } else if (handler.getAccountBusinessLayer().getError_code() == 1) {
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
