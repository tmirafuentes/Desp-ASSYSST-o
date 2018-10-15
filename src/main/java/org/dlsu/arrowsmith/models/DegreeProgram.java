package org.dlsu.arrowsmith.models;

import org.hibernate.engine.jdbc.batch.spi.Batch;

import javax.persistence.*;
import java.util.Set;

@Entity
public class DegreeProgram {
	private long degreeProgram_id;
	private String degreeProgram_code;
	private String degreeProgram_name;
	private College college;
	private Department department;
	private Set<BatchInfo> batchInfos;

	public DegreeProgram() {
	}


	public DegreeProgram(long degreeProgram_id, String degreeProgram_code, String degreeProgram_name, College college, Department department) {
		this.degreeProgram_id = degreeProgram_id;
		this.degreeProgram_code = degreeProgram_code;
		this.degreeProgram_name = degreeProgram_name;
		this.college = college;
		this.department = department;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getDegreeProgram_id() {
		return degreeProgram_id;
	}

	public void setDegreeProgram_id(long degreeProgram_id) {
		this.degreeProgram_id = degreeProgram_id;
	}

	public String getDegreeProgram_code() {
		return degreeProgram_code;
	}

	public void setDegreeProgram_code(String degreeProgram_code) {
		this.degreeProgram_code = degreeProgram_code;
	}

	public String getDegreeProgram_name() {
		return degreeProgram_name;
	}

	public void setDegreeProgram_name(String degreeProgram_name) {
		this.degreeProgram_name = degreeProgram_name;
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

	@OneToMany(mappedBy = "degreeProgram", cascade = CascadeType.ALL)
	public Set<BatchInfo> getBatchInfos() {
		return batchInfos;
	}

	public void setBatchInfos(Set<BatchInfo> batchInfos) {
		this.batchInfos = batchInfos;
	}
}
