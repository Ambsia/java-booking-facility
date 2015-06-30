package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alex on 31/05/2015
 */
public class ArchiveBusinessLayer extends BusinessLayer {
    public ArchiveBusinessLayer() {

    }

    public int getTotalBookings() {
        int totalBookings = 0;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetNOfBookingsMade}");
                try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    while (rs.next()) {
                        totalBookings = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get total bookings made!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return totalBookings;
    }

    public Date[] getBusiestHours() {
        Date[] dates = new Date[2];
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetBusiestHours(?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    callableStatement.registerOutParameter(2, Types.INTEGER);
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        if (rs.next()) {
                            dates[0] = rs.getTime(1);
                            dates[1] = rs.getTime(2);
                            System.out.println(rs.getInt(3));
                        }
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to retrieve the busiest hours!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return dates;
    }

    public int getTotalBookingsCompleted() {
        int totalCompletedBookings = 0;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetNBookingsCompleted}");
                try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    while (rs.next()) {
                        totalCompletedBookings = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the total amount of bookings completed!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return totalCompletedBookings;
    }

    public ArrayList<Equipment> getMostUsedEquipment() {
        ArrayList<Equipment> mostUsedEquipmentList = new ArrayList<>();
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetMostUsedEquipment(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        int i = 0;
                        Equipment equipmentToAdd;
                        while (rs.next() && i < 15) {
                            equipmentToAdd = new Equipment(rs.getString(1));
                            equipmentToAdd.setEquipmentUsage(rs.getInt(2));
                            mostUsedEquipmentList.add(equipmentToAdd);
                            i++;
                        }
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the equipment that is most used!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return mostUsedEquipmentList;
    }


    public ArrayList<String> getMostUsedLocation() {
    	ArrayList<String> mostUsedLocation = new ArrayList<>();
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetMostUsedLocation(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    	int i = 0;
                    	while (rs.next() && i<5) {
                        	mostUsedLocation.add("- " + rs.getString(1) + " (" + rs.getInt(2) + ")");
                        	i++;
                    	}
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the location that is most used!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return mostUsedLocation;
    }

    public ArrayList<String> getStaffMemberWithTheMostBookingsMade() {
    	ArrayList<String> staffMemberWithTheMostBookingsMadeList = new ArrayList<>();
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetMostBookingsMadeBy}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    	int i = 0;
                    	while (rs.next() && i<5) {
                    		staffMemberWithTheMostBookingsMadeList.add("- " + rs.getString(1).trim() + " (" + rs.getInt(2) + ")");
                        	i++;
                    	}
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the staff member with the most bookings made!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return staffMemberWithTheMostBookingsMadeList;
    }

// --Commented out by Inspection START (21/06/2015 00:53):
//    public int getTotalSeniorBookings() {
//        return  0;
//    }
// --Commented out by Inspection STOP (21/06/2015 00:53)

// --Commented out by Inspection START (21/06/2015 00:53):
//    public int getTotalJuniorBookings() {
//        return  0;
//    }
// --Commented out by Inspection STOP (21/06/2015 00:53)

    public int getTotalDaysBooked() {
        int totalDaysBooked = 0;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetTotalDaysBooked}");
                try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                    while (rs.next()) {
                        totalDaysBooked = rs.getInt(2);
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the total days booked!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return totalDaysBooked;
    }
}