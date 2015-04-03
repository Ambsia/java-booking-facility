package com.bookingsystem.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Author: [Alex] on [$Date]
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
            printWriter.println(this.dateAndTimeOfAction + " " + currentAccountLoggedIn.toString() + " Action = { " + loggedAction + " }");
        } catch (Exception ignored) {
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
