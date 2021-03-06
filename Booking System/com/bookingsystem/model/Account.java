package com.bookingsystem.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.codec.digest.DigestUtils;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;

public final class Account {

    private final String userLogonName;
    private int userID;
    private String hashedPassword;
    private int userLevel;
    private String userSalt;

    public Account(int userID, int userLevel, String userLogonName,
                   String unHashedPassword) {
        this.userID = userID;
        this.userLevel = userLevel;
        this.userLogonName = userLogonName.trim();
        this.hashedPassword = SHA1_HASH(unHashedPassword, this.userLogonName);
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return this.userLogonName;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public String getUserSalt() {
        return this.userSalt;
    }

    private String SHA1_HASH(String unHashedString, String username) {
        getSalt(username);

        return DigestUtils.sha1Hex(unHashedString + this.getUserSalt());
    }

    private void getSalt(String username) {
        Connection con;
        try {
            ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues(
                    "sqlconfig.properties");
            con = DriverManager.getConnection(returnSpecifiedPropertyValues
                    .getDatabaseConnectionString());

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("EXECUTE spGetSalt '" + username
                    + "'");
            if (rs.next()) {
                this.userSalt = rs.getString(1);
            } else {
                generateSalt();
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MessageBox
                    .errorMessageBox("There was an issue while we were trying to hash something..!\n"
                            + "Does this make sense to you?\n."
                            + e.toString());
        }
    }

    public boolean changePassword(int userID, String username,
                                  String newPassword) {
        String newPasswordHashed = SHA1_HASH(newPassword, username);
        Connection con;
        try {
            ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues(
                    "sqlconfig.properties");
            con = DriverManager.getConnection(returnSpecifiedPropertyValues
                    .getDatabaseConnectionString());

            CallableStatement callableStatement = con
                    .prepareCall("{CALL spChangePassword(?,?)}");
            callableStatement.setInt(1, userID);
            callableStatement.setString(2, newPasswordHashed);
            callableStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            MessageBox
                    .errorMessageBox("There was an issue whilst attempting to change an accounts password.");
        }
        return false;
    }

    public boolean changeUserLevel(int userID, int newLevel) {
        Connection con;
        try {
            ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues(
                    "sqlconfig.properties");
            con = DriverManager.getConnection(returnSpecifiedPropertyValues
                    .getDatabaseConnectionString());

            CallableStatement callableStatement = con
                    .prepareCall("{CALL spChangeUserLevel(?,?)}");
            callableStatement.setInt(1, userID);
            callableStatement.setInt(2, newLevel);
            callableStatement.execute();
            return true;
        } catch (SQLException e) {
            MessageBox
                    .errorMessageBox("There was an issue whilst attempting to change an accounts user level.");
        }
        return false;
    }

    void generateSalt() {
        int salt;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            salt = random.nextInt(random.nextInt(1000000) + (10));
        } catch (NoSuchAlgorithmException e) {
            salt = 0;
        }
        //System.out.println(salt);
        this.userSalt = "" + salt;
    }

    @Override
    public String toString() {
        return "Account{" + "userID=" + userID + ", userLogonName='"
                + userLogonName + '\'' + ", hashedPassword='" + hashedPassword
                + '\'' + ", userLevel=" + userLevel + ", userSalt='" + userSalt
                + '\'' + '}';
    }

}
