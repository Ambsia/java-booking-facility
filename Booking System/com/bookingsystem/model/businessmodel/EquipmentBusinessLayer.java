package com.bookingsystem.model.businessmodel;

import com.bookingsystem.helpers.MessageBox;
import com.bookingsystem.model.Equipment;

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
public class EquipmentBusinessLayer extends BusinessLayer implements
        Iterable<Equipment> {
    private List<Equipment> equipmentList;
    private int currentIndexOfEquipmentInList;

    public EquipmentBusinessLayer() {
        this.equipmentList = new ArrayList<>();
        this.currentIndexOfEquipmentInList = -1;
    }

    public void populateEquipmentList() {
        equipmentList.clear();
        Equipment equipment;
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spGetEquipment}");
                try (ResultSet resultSet = getDatabaseConnector()
                        .executeQuery()) {
                    while (resultSet.next()) {
                        equipment = new Equipment(resultSet.getString(2));
                        equipment.setEquipmentID(resultSet.getInt(1));
                        String desc = "";
                        if (resultSet.getString(3) == null) {
                            desc = "No Description";
                        } else {
                            desc = resultSet.getString(3);
                        }
                        equipment.setEquipmentDescription(desc);
                        equipment.setEquipmentUsage(resultSet.getInt(4));
                        equipmentList.add(equipment);
                    }
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to retrieve equipment from the database.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public boolean addEquipment(Equipment equipment) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spCheckForDuplicateEquipmentName(?,?)}");
                try (CallableStatement csCheckForDupeName = getDatabaseConnector()
                        .getCallableStatement()) {
                    csCheckForDupeName.setString(1,
                            equipment.getEquipmentName());
                    csCheckForDupeName.registerOutParameter(2, Types.INTEGER);
                    csCheckForDupeName.execute();
                    if (csCheckForDupeName.getInt(2) == 0) {
                        getDatabaseConnector().createNewCallableStatement(
                                "{CALL spInsertEquipment(?,?,?)}");
                        try (CallableStatement callableStatement = getDatabaseConnector()
                                .getCallableStatement()) {
                            callableStatement.setString(1,
                                    equipment.getEquipmentName());
                            callableStatement.setString(2,
                                    equipment.getEquipmentDescription());
                            callableStatement.registerOutParameter(3,
                                    Types.INTEGER);
                            getDatabaseConnector().execute();
                            equipment.setEquipmentID(callableStatement
                                    .getInt(3));
                            // System.out.println(equipment.toString());
                            this.equipmentList.add(equipment);
                            return true;
                        }
                    }
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to add equipment into the database.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
        return false;
    }

    public boolean editEquipment(int equipmentID, Equipment newEquipment) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spCheckForDuplicateEquipmentName(?,?)}");
                try (CallableStatement csCheckForDupeName = getDatabaseConnector()
                        .getCallableStatement()) {
                    int duplicateEquipmentName = 0;
                    if (!this.getEqiupment(equipmentID).getEquipmentName()
                            .equals(newEquipment.getEquipmentName())) {
                        // System.out.println("Names are not equal, therefore has been changed and needs to be checked. | "
                        // + this.getEqiupment(equipmentID).getEquipmentName()
                        // +" | COMPARED TO | " +
                        // newEquipment.getEquipmentName() +"|");
                        csCheckForDupeName.setString(1,
                                newEquipment.getEquipmentName());
                        // System.out.println(newEquipment.getEquipmentName());
                        csCheckForDupeName.registerOutParameter(2,
                                Types.INTEGER);
                        csCheckForDupeName.execute();
                        duplicateEquipmentName = csCheckForDupeName.getInt(2);
                        // System.out.println("made it here though");
                        // System.out.println(csCheckForDupeName.getInt(2));
                    }
                    if (duplicateEquipmentName == 0) {
                        // System.out.println("made it");
                        getDatabaseConnector().createNewCallableStatement(
                                "{CALL spMofifyEquipment(?,?,?)}");
                        try (CallableStatement callableStatement = getDatabaseConnector()
                                .getCallableStatement()) {
                            callableStatement.setInt(1, equipmentID);
                            callableStatement.setString(2,
                                    newEquipment.getEquipmentName());
                            callableStatement.setString(3,
                                    newEquipment.getEquipmentDescription());
                            getDatabaseConnector().execute();
                            correctCurrentIndexWithID(equipmentID);
                            removeEquipmentFromList();
                            addEquipmentToListAtAGivenPosition(newEquipment);
                            return true;
                        }
                    }
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to add equipment into the database.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
        return false;
    }

    public int checkHowManyBookingsAreUsingEquipment(int equipmentID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spCheckUsedEquipment(?,?)}");
                try (CallableStatement callableStatement = getDatabaseConnector()
                        .getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.registerOutParameter(2, Types.INTEGER);
                    callableStatement.execute();
                    return callableStatement.getInt(2);
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to check used equipment in the database.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
        return -1;
    }

    public void removeEquipment(int equipmentID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spRemoveEquipment(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector()
                        .getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.execute();
                    correctCurrentIndexWithID(equipmentID);
                    removeEquipmentFromList();
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to remove equipment from the database.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
                }
                getDatabaseConnector().closeConnection();
            }
        }
    }

    public void increaseEquipmentUsage(int equipmentID) {
        getDatabaseConnector().openConnection();
        if (getDatabaseConnector().isConnected()) {
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spIncreaseEquipmentUsage(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector()
                        .getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.execute();
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to increase equipment usage.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
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
            if (getDatabaseConnector().isConnectionClosed()) {
                getDatabaseConnector().createNewCallableStatement(
                        "{CALL spDecreaseEquipmentUsage(?)}");
                try (CallableStatement callableStatement = getDatabaseConnector()
                        .getCallableStatement()) {
                    callableStatement.setInt(1, equipmentID);
                    callableStatement.execute();
                } catch (SQLException e) {
                    MessageBox
                            .errorMessageBox("There was an issue while we were trying to decrease equipment usage.\n"
                                    + "Does this make any sense to you.."
                                    + e.toString() + "?");
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
        return null; // cannot find equipment in list, ask user if they would
        // like to add it.
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
        for (int i = 0; i < equipmentList.size(); i++) {
            if (equipmentList.get(i).getEquipmentID() == idToFind) {
                this.currentIndexOfEquipmentInList = i;
            }
        }
    }

    private void removeEquipmentFromList() {
        if (this.currentIndexOfEquipmentInList >= 0 && equipmentList.size() > 0) {
            this.equipmentList.remove(this.currentIndexOfEquipmentInList);
        } else {
            MessageBox
                    .errorMessageBox("Nothing selected, or there is nothing to delete.");
        }
    }

    private void addEquipmentToListAtAGivenPosition(Equipment equipment) {
        if (currentIndexOfEquipmentInList >= 0
                && currentIndexOfEquipmentInList <= equipmentList.size()) {
            this.equipmentList.add(currentIndexOfEquipmentInList, equipment);
        }
    }

    @Override
    public Iterator<Equipment> iterator() {
        return equipmentList.iterator();
    }
}
