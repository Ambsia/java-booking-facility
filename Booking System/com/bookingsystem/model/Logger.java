package com.bookingsystem.model;

import java.io.*;
import java.util.Date;

/**
 * Created by Alex on 26/02/2015.
 */
public final class Logger {

    private String loggedAction;
    private String dateAndTimeOfAction;
    private Account currentAccountLoggedIn;

    Date today;

    public Logger(String loggedAction, Account currentAccountLoggedIn) {

        today = new Date();
        this.loggedAction = loggedAction;
        this.dateAndTimeOfAction = today.toString();
        this.currentAccountLoggedIn = currentAccountLoggedIn;
        logEvent();
    }

    public void logEvent() {

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("logfile.txt",true)))) {
            printWriter.println(this.dateAndTimeOfAction + " " + currentAccountLoggedIn.toString() + " Action = { " + loggedAction.toString() + " }");
        } catch (Exception e) {
        }
    }

    public Account getCurrentAccountLoggedIn() {
        return currentAccountLoggedIn;
    }

    public String getLoggedAction() {
        return loggedAction;
    }

    public String getDateAndTimeOfAction() {
        return dateAndTimeOfAction;
    }

}
