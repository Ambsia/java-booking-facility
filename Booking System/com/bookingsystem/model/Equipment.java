package com.bookingsystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.helpers.ReturnSpecifiedPropertyValues;

public final class Equipment {

    private int equipmentID;
    private String equipmentName;
    private String equipmentDescription;
    private int equipmentUsage;

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentID=" + equipmentID +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentDescription='" + equipmentDescription + '\'' +
                ", equipmentUsage=" + equipmentUsage +
                '}';
    }

    public Equipment(String equipmentName) {
        this.equipmentName = equipmentName.trim();
        this.equipmentUsage = 0;
        this.equipmentDescription = "";
    }

    // --Commented out by Inspection START (21/06/2015 00:54):
    // public void setEquipmentName(String equipmentName) {
    // this.equipmentName = equipmentName;
    // }
    // --Commented out by Inspection STOP (21/06/2015 00:54)

    public int getEquipmentID() {
        return this.equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getEquipmentName() {
        return this.equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getEquipmentUsage() {
        return this.equipmentUsage;
    }

    public void setEquipmentUsage(int equipmentUsage) {
        this.equipmentUsage = equipmentUsage;
    }

    public String getEquipmentDescription() {
        return this.equipmentDescription;
    }

    public void setEquipmentDescription(String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

    public void increaseEquipmentUsage() {
        this.equipmentUsage++;
    }

    public void decreaseEquipmentUsage() {
        this.equipmentUsage--;
    }


    public String fakeToString() {
        return this.equipmentName + " (" + this.equipmentUsage + ")";
    }

    public boolean verify() {
        return !this.equipmentName.isEmpty();
    }

	public void resetUsage() {
        Connection con;
        try {
            ReturnSpecifiedPropertyValues returnSpecifiedPropertyValues = new ReturnSpecifiedPropertyValues(
                    "sqlconfig.properties");
            con = DriverManager.getConnection(returnSpecifiedPropertyValues
                    .getDatabaseConnectionString());

            Statement stmt = con.createStatement();
            stmt.execute("EXECUTE spResetUsageStatistic '" + this.getEquipmentID()
                    + "'");
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MessageBox
                    .errorMessageBox("There was an issue while trying to reset a usage statistic\n"
                            + "Does this make sense to you?\n"
                            + e.toString()
                            );
        }
	}

}
