package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class Days {
	private long days_id;
	private String class_day;
	private String begin_time;
	private String end_time;
	private Offering offering;
	private Room room;
	
	public Days(){
		
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getDays_id() {
		return days_id;
	}

	public void setDays_id(long days_id) {
		this.days_id = days_id;
	}

	public String getClass_day() {
		return class_day;
	}

	public void setClass_day(String class_day) {
		this.class_day = class_day;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	@ManyToOne
	@JoinColumn(name = "offering_id")
	public Offering getOffering() {
		return offering;
	}

	public void setOffering(Offering offering) {
		this.offering = offering;
	}

	@ManyToOne
	@JoinColumn(name = "room_id")
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
