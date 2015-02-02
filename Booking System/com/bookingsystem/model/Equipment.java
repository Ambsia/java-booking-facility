package  com.bookingsystem.model;


public class Equipment {
	
	
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
	public String toString() {
		return "Equipment{" +
				"equipmentUsage=" + equipmentUsage +
				", equipmentName='" + equipmentName + '\'' +
				'}';
	}
}
