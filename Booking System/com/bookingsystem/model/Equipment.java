package  com.bookingsystem.model;


public final class Equipment {
	
	
	private int equipmentUsage;
	private String equipmentName;
	
	public Equipment(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	public void setEquipmentStatistic(int usageStatistic) {
		this.equipmentUsage = usageStatistic;
	}
	
	
	public String getEquipmentName() {
		return this.equipmentName;
	}
	
	public int getEquipmentUsageStatistic() {
		return this.equipmentUsage;
	}


	@Override
	public String toString() {
		return this.equipmentName + " (" + this.equipmentUsage + ")";
	}
}
