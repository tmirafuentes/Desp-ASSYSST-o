package org.dlsu.arrowsmith.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Building {
	private long building_id;
	private String building_name;
	private String building_code;
	private Set<Room> rooms;
	
	public Building(){
		
	}

	public Building(long building_id, String building_name, String building_code) {
		this.building_id = building_id;
		this.building_name = building_name;
		this.building_code = building_code;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getBuilding_id() {
		return building_id;
	}

	public void setBuilding_id(long building_id) {
		this.building_id = building_id;
	}

	public String getBuilding_name() {
		return building_name;
	}

	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}

	public String getBuilding_code() {
		return building_code;
	}

	public void setBuilding_code(String building_code) {
		this.building_code = building_code;
	}

	@OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}
}
