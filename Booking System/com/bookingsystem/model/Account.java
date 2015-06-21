package com.bookingsystem.model;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

public final class Account {

	private int userID;
	private final String userLogonName;
	private final String hashedPassword;
	private int userLevel;
	private String userSalt;
	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getUserID() { return this.userID; }

	public String getUsername() {
		return this.userLogonName;
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public String getUserSalt() {return this.userSalt;}

	public Account(int userID, int userLevel, String userLogonName,	String unHashedPassword) {
		this.userID = userID;
		this.userLevel = userLevel;
		this.userLogonName = userLogonName.trim();
		this.hashedPassword = SHA1_HASH(unHashedPassword);
	}

	private String SHA1_HASH(String unHashedString) {
		Connection con;
		try {
			ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues("sqlconfig.properties");
			con = DriverManager.getConnection(returnSpecifiedPropertyValues.getDatabaseConnectionString());

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("EXECUTE spGetSalt '" + this.getUsername() + "'");
			if(rs.next())  {
				userSalt = rs.getString(1);
			} else {
				generateSalt();
			}

		} catch (SQLException e) {
			MessageBox.errorMessageBox("There was an issue while we were trying to hash something..!\n" + "Does this make sense you to.." + e.toString() + "?");
		}
		return DigestUtils.sha1Hex(unHashedString + userSalt);
	}

	void generateSalt() {
		int salt;
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			salt = random.nextInt(random.nextInt(1000000) + (10));
		} catch (NoSuchAlgorithmException e) {
			salt = 0;
		}
		this.userSalt = ""+salt;
	}

	@Override
	public String toString() {
		return "Account{" +
				"userID=" + userID +
				", userLogonName='" + userLogonName + '\'' +
				", hashedPassword='" + hashedPassword + '\'' +
				", userLevel=" + userLevel +
				", userSalt='" + userSalt + '\'' +
				'}';
	}

}
