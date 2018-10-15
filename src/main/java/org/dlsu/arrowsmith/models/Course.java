package org.dlsu.arrowsmith.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Course {
	private long course_id;
	private String course_code;
	private String course_name;
	private String course_type;
	private float units;
	private String remarks;
	private String description;
	private Area area;
	private Department department;
	private College college;
	private Set<Offering> offerings;
	private Set<Offering> electives;

	public Course() {
		
	}

	public long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(long course_id) {
		this.course_id = course_id;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_type() {
		return course_type;
	}

	public void setCourse_type(String course_type) {
		this.course_type = course_type;
	}

	public float getUnits() {
		return units;
	}

	public void setUnits(float units) {
		this.units = units;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "area_id")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@ManyToOne
	@JoinColumn(name = "department_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne
	@JoinColumn(name = "college_id")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	public Set<Offering> getOfferings() {
		return offerings;
	}

	public void setOfferings(Set<Offering> offerings) {
		this.offerings = offerings;
	}

	@ManyToMany
	@JoinTable(name = "elecOffer", joinColumns = @JoinColumn(name = "courseEquivalence_id", referencedColumnName = "course_id"),
				inverseJoinColumns = @JoinColumn(name = "offering_id", referencedColumnName = "offering_id"))
	public Set<Offering> getElectives() {
		return electives;
	}

	public void setElectives(Set<Offering> electives) {
		this.electives = electives;
	}
}
