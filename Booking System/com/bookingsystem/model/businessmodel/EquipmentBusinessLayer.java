package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;
import org.apache.commons.collections.IteratorUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 30/06/2015
 */
public class EquipmentBusinessLayer  extends  BusinessLayer implements Iterable<Equipment> {
    private List<Equipment> equipmentList;

    public EquipmentBusinessLayer() {
        this.equipmentList = new ArrayList<>();
    }

    public void populateEquipmentList() {
        Equipment equipment;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spGetEquipment}");
                try (ResultSet resultSet = getDatabaseConnector().executeQuery()){
                    while (resultSet.next()) {
                        equipment = new Equipment(resultSet.getString(2));
                        equipment.setEquipmentID(resultSet.getInt(1));
                        equipment.setEquipmentDescription(resultSet.getString(3));
                        equipment.setEquipmentUsage(resultSet.getInt(4));
                        equipmentList.add(equipment);
                    }
                }  catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to retrieve equipment from the database.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
            }
        }
        System.out.println(equipmentList.toString());
    }

    public Equipment getEqiupment(int equipmentID) {
        for (Equipment equipment : this.equipmentList) {
            if (equipment.getEquipmentID() == equipmentID) {
                return equipment;
            }
        }
        return null; //cannot find equipment in list, ask user if they would like to add it.
    }

    public void insertEquipment(Equipment equipment) {

    }

    public void removeEquipment(int equipmentID) {

    }

    public void editEquipment(Equipment equipment) {

    }

    @Override
    public Iterator<Equipment> iterator() {
      return equipmentList.iterator();
    }
}
