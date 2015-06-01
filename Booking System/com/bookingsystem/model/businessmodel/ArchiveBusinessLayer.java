package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 31/05/2015.
 */
public class ArchiveBusinessLayer extends BusinessLayer {
    public ArchiveBusinessLayer() {

    }

    public int getTotalBookings() {
        int totalBookings = 0;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
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
            if (!getDatabaseConnector().isConnectionClosed()) {
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
            if (!getDatabaseConnector().isConnectionClosed()) {
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

    public String getMostUsedEquipment() {
        String mostUsedEquipment = "";
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetMostUsedEquipment(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        if (rs.next()) {
                            mostUsedEquipment = rs.getString(1);
                            mostUsedEquipment += " (" + rs.getInt(2) + ") Uses.";
                        }
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the equipment that is most used!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return mostUsedEquipment;
    }

    public String getMostUsedLocation() {
        String mostUsedLocation = "";
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetMostUsedLocation(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        if (rs.next()) {
                            mostUsedLocation = rs.getString(1);
                            mostUsedLocation += " (" + rs.getInt(2) + ") Bookings.";
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

    public String getStaffMemberWithTheMostBookingsMade() {
        String staffMemberWithTheMostBookingsMade = "";
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetMostBookingsMadeBy(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    try (ResultSet rs = getDatabaseConnector().executeQuery()) {
                        if (rs.next()) {
                            staffMemberWithTheMostBookingsMade = rs.getString(1).trim();
                            staffMemberWithTheMostBookingsMade += " (" + rs.getInt(2) + ") Bookings.";
                        }
                    }
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to get the staff member with the most bookings made!\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
            getDatabaseConnector().closeConnection();
        }
        return staffMemberWithTheMostBookingsMade;
    }

    public int getTotalSeniorBookings() {
        return  0;
    }

    public int getTotalJuniorBookings() {
        return  0;
    }

    public int getTotalDaysBooked() {
        int totalDaysBooked = 0;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (!getDatabaseConnector().isConnectionClosed()) {
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