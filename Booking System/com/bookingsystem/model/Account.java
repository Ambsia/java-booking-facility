package com.bookingsystem.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

public final class Account {

	private int userID;
	private String userLogonName;
	private String hashedPassword;
	private int userLevel;
	private String userSalt;

	Logger accountLogger;

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public boolean validation() {
		return !this.userLogonName.isEmpty() || !this.hashedPassword.isEmpty();
	}


	public String getUsername() {
		return this.userLogonName;
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public Account(int userID, int userLevel, String userLogonName,
			String unHashedPassword) {
		this.userID = userID;
		this.userLevel = userLevel;
		this.userLogonName = userLogonName;
		this.hashedPassword = SHA1_HASH(unHashedPassword);
		accountLogger = new Logger("Initialised Account.",this);
	}



	private String SHA1_HASH(String unHashedString) {
		Connection con;
		try {
			accountLogger = new Logger("Attempting to retrieve salt of account", this);
			ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues();
			con = DriverManager.getConnection(returnSpecifiedPropertyValues.getDatabaseConnectionString());
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("EXECUTE spGetSalt '" + this.getUsername() + "'");
			while(rs.next())  {
				userSalt = rs.getString(1);
			}
			accountLogger = new Logger("Salt Retrieval Successful", this);
		} catch (SQLException e) {
			MessageBox.errorMessageBox(e.toString());
		} catch (IOException e) {
			MessageBox.errorMessageBox(e.toString());
		}
		String hashedString = DigestUtils.sha1Hex(unHashedString + userSalt);
		accountLogger = new Logger("Hashing Un-Hashed String.",this);
		return hashedString;
	}

	public String generateSalt() {
		int salt;
		try {
			accountLogger = new Logger("Attempting to generate salt.", this);
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			salt = random.nextInt(random.nextInt(1000000) + (Integer.MAX_VALUE - 1000000));
			accountLogger = new Logger("Generated Salt.",this);
		} catch (NoSuchAlgorithmException e) {
			salt = 0;
		}
		return "" +salt;
	}

	@Override
	public String toString() {
		return "Account{" +
				"userID=" + userID +
				", userLogonName='" + userLogonName + '\'' +
				", hashedPassword='" + hashedPassword + '\'' +
				", userLevel=" + userLevel +
				", userSalt='" + userSalt + '\'' +
				", accountCreation=" +
				'}';
	}




}
