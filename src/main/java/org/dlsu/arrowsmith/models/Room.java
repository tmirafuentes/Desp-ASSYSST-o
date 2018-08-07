package org.dlsu.arrowsmith.models;

public class Room {
	private String id;
	private String roomCode;
	private String roomLocation;
	private String roomType;
	private String roomCapacity;
	private String buildingId;
	private Building building;

	public Room(){
		
	}
	
	public Room(String id,String roomCode, String roomLocation, String roomType, String roomCapacity, String buildingId){
		this.id = id;
		this.buildingId = buildingId;
		this.roomCode = roomCode;
		this.roomLocation = roomLocation;
		this.roomCapacity = roomCapacity;
		this.roomType = roomType;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(String roomLocation) {
		this.roomLocation = roomLocation;
	}

	public String getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(String roomCapacity) {
		this.roomCapacity = roomCapacity;
	}
	
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
}
