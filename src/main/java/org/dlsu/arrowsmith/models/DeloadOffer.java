package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class DeloadOffer {
	private long deloadOffer_id;
	private int term;
	private int start_year;
	private int end_year;
	private float units;
	private String description;
	private Deloading deloading;
	private Faculty faculty;

	public DeloadOffer() {
	}

	public DeloadOffer(long deloadOffer_id, int term, int start_year, int end_year, float units, String description, Deloading deloading, Faculty faculty) {
		this.deloadOffer_id = deloadOffer_id;
		this.term = term;
		this.start_year = start_year;
		this.end_year = end_year;
		this.units = units;
		this.description = description;
		this.deloading = deloading;
		this.faculty = faculty;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getDeloadOffer_id() {
		return deloadOffer_id;
	}

	public void setDeloadOffer_id(long deloadOffer_id) {
		this.deloadOffer_id = deloadOffer_id;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public int getStart_year() {
		return start_year;
	}

	public void setStart_year(int start_year) {
		this.start_year = start_year;
	}

	public int getEnd_year() {
		return end_year;
	}

	public void setEnd_year(int end_year) {
		this.end_year = end_year;
	}

	public float getUnits() {
		return units;
	}

	public void setUnits(float units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "deloading_id")
	public Deloading getDeloading() {
		return deloading;
	}

	public void setDeloading(Deloading deloading) {
		this.deloading = deloading;
	}

	@ManyToOne
	@JoinColumn(name = "faculty_id")
	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
}
