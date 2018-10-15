package org.dlsu.arrowsmith.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Room {
	private long room_id;
	private String room_code;
	private String room_location;
	private String room_type;
	private String room_capacity;
	private Building building;

	public Room() {
	}

	public Room(long room_id, String room_code, String room_location, String room_type, String room_capacity) {
		this.room_id = room_id;
		this.room_code = room_code;
		this.room_location = room_location;
		this.room_type = room_type;
		this.room_capacity = room_capacity;
	}

	public long getRoom_id() {
		return room_id;
	}

	public void setRoom_id(long room_id) {
		this.room_id = room_id;
	}

	public String getRoom_code() {
		return room_code;
	}

	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}

	public String getRoom_location() {
		return room_location;
	}

	public void setRoom_location(String room_location) {
		this.room_location = room_location;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getRoom_capacity() {
		return room_capacity;
	}

	public void setRoom_capacity(String room_capacity) {
		this.room_capacity = room_capacity;
	}

	@ManyToOne
	@JoinColumn(name = "building_id")
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
}
