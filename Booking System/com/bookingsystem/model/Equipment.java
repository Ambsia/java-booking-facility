package com.bookingsystem.model;


public final class Equipment {


    private int equipmentUsage;
    private String equipmentName;

    public Equipment(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public void SetEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }


    public String GetEquipmentName() {
        return this.equipmentName;
    }

    public int GetEquipmentUsageStatistic() {
        return this.equipmentUsage;
    }


    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentUsage=" + equipmentUsage +
                ", equipmentName='" + equipmentName + '\'' +
                '}';
    }
}
