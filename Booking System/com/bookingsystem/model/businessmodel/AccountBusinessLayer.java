package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Logger;

/**
 * Created by Alex on 30/03/2015.
 */
import java.io.IOException;
import java.sql.*;
public class AccountBusinessLayer {
    private boolean loggedIn;
    private Logger accountLogger;
    private ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues;
    private String[] arrayOfProperties;
    private String databaseConnectionString;
    public AccountBusinessLayer() throws IOException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
        arrayOfProperties = returnSpecifiedPropertyValues.getPropertyValues();

        databaseConnectionString = "jdbc:sqlserver://"+arrayOfProperties[0]+":"+arrayOfProperties[1]+";user="+arrayOfProperties[2]+";password="+arrayOfProperties[3]+";databaseName="+arrayOfProperties[4]+"";
    }



    public void addAccount() {

    }

    public void removeAccount() {

    }


    public boolean getAccount(Account account) throws ClassNotFoundException, SQLException {

        Connection con = DriverManager.getConnection(databaseConnectionString);
        System.out.println("# - Connection Obtained");

        Statement stmt = con.createStatement();
        System.out.println("# - Statement Created");

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Account;");
        System.out.println("# - Query Executed");

        if(rs.next()) {
            System.out.println("Account Count : "+rs.getInt(1));
        }

        rs.close();
        stmt.close();
        con.close();
        System.out.println("# - Resources released");


        //create sql command
        //String sqlCommand = "Select Username,Password from tblUsers where" +
        //"Username=@Username AND Password=@Password";
        accountLogger = new Logger("Logging in.",account);
        return true;
        //Pass parameter's for username and password
        //execute command
        //Logged in = true or false

    }
}
