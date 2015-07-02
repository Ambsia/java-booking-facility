package  com.bookingsystem.model;


public final class Equipment {
	
	
	private int equipmentID;
	private String equipmentName;
	private String equipmentDescription;
	private int equipmentUsage;

	public Equipment(String equipmentName) {
		this.equipmentName = equipmentName.trim();
		this.equipmentUsage = 0;
		this.equipmentDescription = "";
	}

// --Commented out by Inspection START (21/06/2015 00:54):
//	public void setEquipmentName(String equipmentName) {
//		this.equipmentName = equipmentName;
//	}
// --Commented out by Inspection STOP (21/06/2015 00:54)

	public int getEquipmentID(){ return this.equipmentID;}

	public void setEquipmentID(int equipmentID) {this.equipmentID = equipmentID;}

	public String getEquipmentName() {
		return this.equipmentName;
	}
	
	public int getEquipmentUsage() {
		return this.equipmentUsage;
	}

	public void setEquipmentUsage(int equipmentUsage) { this.equipmentUsage = equipmentUsage;}

	public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

	public String getEquipmentDescription() {return  this.equipmentDescription;}

	public void setEquipmentDescription(String equipmentDescription) { this.equipmentDescription = equipmentDescription;}

	public void increaseEquipmentUsage() {
		this.equipmentUsage++;
	}

	public void decreaseEquipmentUsage() {
		this.equipmentUsage--;
	}

	@Override
	public String toString() {
		return this.equipmentName + " (" + this.equipmentUsage + ")";
	}


}
