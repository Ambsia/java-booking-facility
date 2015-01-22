package com.bookingsystem.model;

import java.util.Iterator;

public class Equipment implements Iterable<Equipment>{
	
	
	int equipmentUsage;
	String equipmentName;
	
	public Equipment(int equipmentUsage, String equipmentName) {
		this.equipmentUsage = equipmentUsage;
		this.equipmentName = equipmentName;
	}

	public void IncreaseEquipmentUsage() {
		//database increase usage of equipment and return current statistic
		
	}
	
	public void AddEquipment() {
		
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
	public Iterator<Equipment> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "Equipment name: " + this.equipmentName + "\n" + "Equipment Usage: " + this.equipmentUsage;
	}

}
