package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Account;
import com.bookingsystem.model.Log;

import java.sql.*;
import java.util.ArrayList;

/**
 * Author: [Alex]
 */
public class LoggerBusinessLayer extends BusinessLayer {

    private Account accountCurrentlyLoggedIn = null;

    public LoggerBusinessLayer() {

    }

    public void insertLog(Log log) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spInsertLog(?,?,?,?,?,?,?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setString(1, log.getEventLogged());
                    callableStatement.setString(2, log.getClassEvent());
                    callableStatement.setTimestamp(3, getTimeStamp(log.getDateAndTimeOfEvent()));
                    callableStatement.setInt(4, accountCurrentlyLoggedIn.getUserID());
                    callableStatement.setInt(5, log.getBookingIDInserted());
                    callableStatement.setInt(6, log.getBookingIDEdited());
                    callableStatement.setInt(7, log.getBookingIDDeleted());
                    callableStatement.setInt(8, log.getAccountIDCreated());
                    callableStatement.setInt(9, log.getAccountIDDeleted());
                    getDatabaseConnector().execute();
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to insert a log.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }

    private java.sql.Timestamp getTimeStamp(java.util.Date date) {
        return new Timestamp(date.getTime());
    }


    public void exceptionCaused(Log log, Exception exceptionType) {

    }

    public void removeLogsForAccount(int currentAccountID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            System.out.println("" + getDatabaseConnector().isConnected());
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spRemoveLogsForAccount(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setInt(1, currentAccountID);
                    callableStatement.execute();

                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while removing logs.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
    }

    public ArrayList<Log> getLogsForAccount(int currentAccountID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                ArrayList<Log> logArrayList = new ArrayList<>();
                ArrayList<Integer> logIDS = new ArrayList<>();
                ArrayList<Integer> integers = new ArrayList<>();
                if (currentAccountID != -1) {
                    getDatabaseConnector().createNewCallableStatement("{CALL spGetLogsForAccount(?)}");
                    try (CallableStatement csGetLogsForAccount = getDatabaseConnector().getCallableStatement()) {
                        csGetLogsForAccount.setInt(1, currentAccountID);
                        try (ResultSet rs = csGetLogsForAccount.executeQuery()) {
                            while (rs.next()) {
                                logArrayList.add(new Log(rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                                logIDS.add(rs.getInt(1));
                            }
                        }
                        getDatabaseConnector().createNewCallableStatement("{CALL spGetIDPlayedWith(?,?)}");
                        try (CallableStatement csGetIDPlayedWith = getDatabaseConnector().getCallableStatement()) {
                            csGetIDPlayedWith.registerOutParameter(2, Types.INTEGER);

                            for (Integer i : logIDS) {
                                csGetIDPlayedWith.setInt(1, i);
                                csGetIDPlayedWith.execute();
                                integers.add(csGetIDPlayedWith.getInt(2));
                            }

                            for (int k = 0; k < logArrayList.size(); k++) {
                                logArrayList.get(k).setLogID(logIDS.get(k));
                                logArrayList.get(k).setIdPlayedWith(integers.get(k));
                            }

                            return logArrayList;
                        }
                    } catch (SQLException e) {
                        MessageBox.errorMessageBox("There was an issue while retrieving logs for an account.\n" + "Does this make any sense to you.." + e.toString() + "?");
                    }
                } else {
                    MessageBox.errorMessageBox("You must select an account to view logs.");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return null;
    }

    public void setAccountCurrentlyLoggedIn(Account accountCurrentlyLoggedIn) {
        this.accountCurrentlyLoggedIn = accountCurrentlyLoggedIn;
    }
//=======
//	private Account accountCurrentlyLoggedIn;
//
//	public LoggerBusinessLayer() {
//		this.accountCurrentlyLoggedIn = null;
//	}
//
//	public void insertLog(Log log) {
//		try {
//			getDatabaseConnector().openConnection();
//			getDatabaseConnector().createNewCallableStatement("{CALL spInsertLog(?,?,?,?,?,?,?,?,?)}");
//			try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement())
//			{
//				callableStatement.setString(1, log.getEventLogged());
//				callableStatement.setString(2, log.getClassEvent());
//				callableStatement.setTimestamp(3, getTimeStamp(log.getDateAndTimeOfEvent()));
//				callableStatement.setInt(4, accountCurrentlyLoggedIn.getUserID());
//				callableStatement.setInt(5, log.getBookingIDInserted());
//				callableStatement.setInt(6, log.getBookingIDEdited());
//				callableStatement.setInt(7, log.getBookingIDDeleted());
//				callableStatement.setInt(8, log.getAccountIDCreated());
//				callableStatement.setInt(9, log.getAccountIDDeleted());
//				getDatabaseConnector().execute();
//			}
//			getDatabaseConnector().closeConnection();
//		} catch (SQLException e) {
//			MessageBox.errorMessageBox("There was an issue while we were trying to insert a log.\n" + "Does this make any sense to you.." + e.toString() + "?");
//		}
//	}
//
//	public java.sql.Timestamp getTimeStamp(java.util.Date date) {
//		return new Timestamp(date.getTime());
//	}
//
//	public void exceptionCaused(Log log, Exception exceptionType) {
//	}
//
//	public void removeLogsForAccount(int currentAccountID) {
//		try {
//			getDatabaseConnector().openConnection();
//			getDatabaseConnector().createNewCallableStatement("{CALL spRemoveLogsForAccount(?)}");
//			try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement();)
//			{
//				callableStatement.setInt(1, currentAccountID);
//				getDatabaseConnector().execute();
//			}
//			getDatabaseConnector().closeConnection();
//		} catch (SQLException e) {
//			MessageBox.errorMessageBox("There was an issue while removing logs.\n" + "Does this make any sense to you.." + e.toString() + "?");
//		}
//	}
//
//	public ArrayList<Log> getLogsForAccount(int currentAccountID) {
//		ArrayList<Log> logArrayList = new ArrayList<>();
//		ArrayList<Integer> logIDS = new ArrayList<>();
//		ArrayList<Integer> integers = new ArrayList<>();
//		try {
//			getDatabaseConnector().openConnection();
//			if (currentAccountID != -1) {
//				try (Connection connection = DriverManager.getConnection(databaseConnectionString);
//					 CallableStatement csGetLogsForAccount = connection.prepareCall("{CALL spGetLogsForAccount(?)}");
//					 CallableStatement csGetIDPlayedWith = connection.prepareCall("{CALL spGetIDPlayedWith(?,?)}")) {
//
//					csGetLogsForAccount.setInt(1,currentAccountID);
//
//					ResultSet rs = csGetLogsForAccount.executeQuery();
//					while (rs.next()) {
//						logArrayList.add(new Log(rs.getString(2),rs.getString(3),rs.getTimestamp(4)));
//						logIDS.add(rs.getInt(1));
//					}
//
//					csGetIDPlayedWith.registerOutParameter(2, Types.INTEGER);
//
//					for (Integer i : logIDS) {
//						csGetIDPlayedWith.setInt(1,i);
//						csGetIDPlayedWith.execute();
//						integers.add(csGetIDPlayedWith.getInt(2));
//					}
//
//					for (int k = 0; k<logArrayList.size(); k++) {
//						logArrayList.get(k).setLogID(logIDS.get(k));
//						logArrayList.get(k).setIdPlayedWith(integers.get(k));
//					}
//
//				}
//			} else {
//				MessageBox.errorMessageBox("You must select an account to view logs.");
//			}
//		} catch (SQLException e) {
//			MessageBox.errorMessageBox("There was an issue while retrieving logs for an account.\n" + "Does this make any sense to you.." + e.toString() + "?");
//		}
//		return  logArrayList;
//	}
//
//	public void setAccountCurrentlyLoggedIn(Account accountCurrentlyLoggedIn) {
//		this.accountCurrentlyLoggedIn = accountCurrentlyLoggedIn;
//	}
//>>>>>>> origin/master
}
