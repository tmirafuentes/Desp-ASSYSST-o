package org.dlsu.arrowsmith.models;

public class Days {
	private String daysId;
	private String offeringId;
	private String classDay;
	private String beginTime;
	private String endTime;
	private String roomId;
	private Room room;
	
	public Days(){
		
	}
	
	public Days(String classDay, String beginTime, String endTime){
		this.classDay = classDay;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
	public String getDaysId() {
		return daysId;
	}

	public void setDaysId(String daysId) {
		this.daysId = daysId;
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public String getClassDay() {
		return classDay;
	}

	public void setClassDay(String classDay) {
		this.classDay = classDay;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}


}
