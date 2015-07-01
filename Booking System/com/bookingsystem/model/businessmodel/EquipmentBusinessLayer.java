package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Booking;
import com.bookingsystem.model.Equipment;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.commons.collections.IteratorUtils;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alex on 30/06/2015
 */
public class EquipmentBusinessLayer  extends  BusinessLayer implements Iterable<Equipment> {
    private List<Equipment> equipmentList;
    private int currentIndexOfEquipmentInList;

    public EquipmentBusinessLayer() {
        this.equipmentList = new ArrayList<>();
        this.currentIndexOfEquipmentInList = -1;
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
                getDatabaseConnector().closeConnection();
            }
        }

    }
    public void addEquipment(Equipment equipment) {
        getDatabaseConnector().openConnection();
        if(getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spInsertEquipment(?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setString(1, equipment.getEquipmentName());
                    callableStatement.setString(2,equipment.getEquipmentDescription());
                    callableStatement.registerOutParameter(3, Types.INTEGER);
                    getDatabaseConnector().execute();
                    equipment.setEquipmentID(callableStatement.getInt(3));
                    System.out.println(equipment.toString());
                    this.equipmentList.add(equipment);
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to add equipment into the database.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public void editEquipment(Equipment equipment) {
        getDatabaseConnector().openConnection();
        if(getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spMofifyEquipment(?,?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setInt(1, equipment.getEquipmentID());
                    callableStatement.setString(2, equipment.getEquipmentName());
                    callableStatement.setString(3, equipment.getEquipmentDescription());
                    getDatabaseConnector().execute();
                    correctCurrentIndexWithID(equipment.getEquipmentID());
                    addEquipmentToListAtAGivenPosition(equipment);
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to add equipment into the database.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public void removeEquipment(int equipmentID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if(getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spRemoveEquipment(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.execute();
                    correctCurrentIndexWithID(equipmentID);
                    removeEquipmentFromList();
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to remove equipment from the database.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public void increaseEquipmentUsage(int equipmentID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if(getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spIncreaseEquipmentUsage(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.execute();
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to increase equipment usage.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                if (this.getEqiupment(equipmentID) != null) {
                    this.getEqiupment(equipmentID).increaseEquipmentUsage();
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }
    public void decreaseEquipmentUsage(int equipmentID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if(getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement("{CALL spDecreaseEquipmentUsage(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector().getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.execute();
                } catch (SQLException e) {
                    MessageBox.errorMessageBox("There was an issue while we were trying to decrease equipment usage.\n" + "Does this make any sense to you.." + e.toString() + "?");
                }
                if (this.getEqiupment(equipmentID) != null) {
                    this.getEqiupment(equipmentID).decreaseEquipmentUsage();
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public Equipment getEqiupment(int equipmentID) {
        for (Equipment equipment : this.equipmentList) {
            if (equipment.getEquipmentID() == equipmentID) {
                return equipment;
            }
        }
        return null; //cannot find equipment in list, ask user if they would like to add it.
    }

    public Equipment getEquipmentWithName(String equipmentName) {
        for (Equipment equipment : this.equipmentList) {
            if (equipment.getEquipmentName().equals(equipmentName)) {
                return equipment;
            }
        }
        return null;
    }

    private void correctCurrentIndexWithID(int idToFind) {
        for (int i = 0; i<equipmentList.size();i++) {
            if (equipmentList.get(i).getEquipmentID() == idToFind) {
                this.currentIndexOfEquipmentInList = i;
            }
        }
    }

    private void removeEquipmentFromList() {
        if (this.currentIndexOfEquipmentInList >= 0 && equipmentList.size() > 0) {
            this.equipmentList.remove(this.currentIndexOfEquipmentInList);
        } else {
            MessageBox.errorMessageBox("Nothing selected, or there is nothing to delete.");
        }
    }

    private void addEquipmentToListAtAGivenPosition(Equipment equipment) {
        if (currentIndexOfEquipmentInList >= 0 && currentIndexOfEquipmentInList <= equipmentList.size()) {
            this.equipmentList.add(currentIndexOfEquipmentInList, equipment);
        }
    }



    @Override
    public Iterator<Equipment> iterator() {
      return equipmentList.iterator();
    }
}
