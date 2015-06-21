package  com.bookingsystem.model;


public final class Equipment {
	
	
	private int equipmentUsage;
	private final String equipmentName;
	
	public Equipment(String equipmentName) {
		this.equipmentName = equipmentName;
	}

// --Commented out by Inspection START (21/06/2015 00:54):
//	public void setEquipmentName(String equipmentName) {
//		this.equipmentName = equipmentName;
//	}
// --Commented out by Inspection STOP (21/06/2015 00:54)

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
