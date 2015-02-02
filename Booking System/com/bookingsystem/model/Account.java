package com.bookingsystem.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;

public class Account {

	private int userID;
	private String userLogonName;
	private String hashedPassword;
	private int userLevel;
	private String userSalt;

	public static String validationMsg = "";
	
	private boolean accountCreation = false;

	private static boolean loggedIn = false;

	public Account(int userID, int userLevel, String userLogonName,
			String hashedPassword) {
		this.userID = userID;
		this.userLevel = userLevel;
		this.userLogonName = userLogonName;
		this.hashedPassword = SHA1_HASH(hashedPassword);
	}

	public boolean validation() {
		return !this.userLogonName.isEmpty() || !this.hashedPassword.isEmpty();
	}

	public static boolean isLoggedIn() {
		return loggedIn;
	}

	public String getUsername() {
		return this.userLogonName;
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public void createAccount() {
			userSalt = generateSalt();
	}
	
	public boolean login() {
		
		
		//sql connection string
		
		//try connect
		//try {
		//
		//} catch (SQLException e) {
		//
		//}
		if (userLogonName.equals("alex")) {
			return true;
		}
		//create sql command
		String sqlCommand = "Select Username,Password from tblUsers where" +
		"Username=@Username AND Password=@Password";
		return false;
		//Pass parameter's for username and password
		//execute command
		//Logged in = true or false
		
	}

	public String SHA1_HASH(String unHashedString) {

		if (!accountCreation) {
		//sql connection string
		//try connect to database
				
		//create sql command
		//String sqlCommand = "Select UserID from tblUsers"
		// + " where Username=@Username" + "VALUES (@Username)";
		//sql datareader
		//userID = sqlreader
		//userSalt = sqlreader
		
		
		}
		String hashedString = DigestUtils.sha1Hex(unHashedString + userSalt);
		
		return hashedString;
	}

	public String generateSalt() {
		int salt = 0;
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			salt = random.nextInt(random.nextInt(1000000) + (Integer.MAX_VALUE - 1000000));
		} catch (NoSuchAlgorithmException e) {
			salt = 0;
		}
		
		return new String(Integer.toString(salt));
	} 

	public Account returnAccount() {

		return null;
	}

	@Override
	public String toString() {
		return "Account{" +
				"userID=" + userID +
				", userLogonName='" + userLogonName + '\'' +
				", hashedPassword='" + hashedPassword + '\'' +
				", userLevel=" + userLevel +
				", userSalt='" + userSalt + '\'' +
				", accountCreation=" + accountCreation +
				'}';
	}




}
