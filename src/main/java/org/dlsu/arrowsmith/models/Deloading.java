package org.dlsu.arrowsmith.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Deloading {
	private long deloading_id;
	private String deloading_code;
	private String deloading_name;
	private String deloading_type;
	private String description;
	private College college;
	private Department department;
	private Set<DeloadOffer> deloadOffers;

	public Deloading() {
	}

	public Deloading(long deloading_id, String deloading_code, String deloading_name, String deloading_type, String description, College college, Department department) {
		this.deloading_id = deloading_id;
		this.deloading_code = deloading_code;
		this.deloading_name = deloading_name;
		this.deloading_type = deloading_type;
		this.description = description;
		this.college = college;
		this.department = department;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getDeloading_id() {
		return deloading_id;
	}

	public void setDeloading_id(long deloading_id) {
		this.deloading_id = deloading_id;
	}

	public String getDeloading_code() {
		return deloading_code;
	}

	public void setDeloading_code(String deloading_code) {
		this.deloading_code = deloading_code;
	}

	public String getDeloading_name() {
		return deloading_name;
	}

	public void setDeloading_name(String deloading_name) {
		this.deloading_name = deloading_name;
	}

	public String getDeloading_type() {
		return deloading_type;
	}

	public void setDeloading_type(String deloading_type) {
		this.deloading_type = deloading_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "college_id")
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@ManyToOne
	@JoinColumn(name = "department_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@OneToMany(mappedBy = "deloading", cascade = CascadeType.ALL)
	public Set<DeloadOffer> getDeloadOffers() {
		return deloadOffers;
	}

	public void setDeloadOffers(Set<DeloadOffer> deloadOffers) {
		this.deloadOffers = deloadOffers;
	}
}
