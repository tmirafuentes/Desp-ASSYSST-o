package org.dlsu.arrowsmith.models;

public class Building {
	String id, buildingName, buildingCode, roomsAvailable;
	
	public Building(){
		
	}
	
	public Building(String id, String buildingName, String buildingCode, String roomsAvailable){
		this.id = id;
		this.buildingName = buildingName;
		this.buildingCode = buildingCode;
		this.roomsAvailable = roomsAvailable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getRoomsAvailable() {
		return roomsAvailable;
	}

	public void setRoomsAvailable(String roomsAvailable) {
		this.roomsAvailable = roomsAvailable;
	}
	
}
