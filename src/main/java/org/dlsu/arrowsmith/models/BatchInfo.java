package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class BatchInfo {
	private long batchinfo_id;
	private long batch;
	private long student_count;
	private DegreeProgram degreeProgram;
	
	public BatchInfo(){
		
	}

	public BatchInfo(long batchinfo_id, long batch, long student_count, DegreeProgram degreeProgram) {
		this.batchinfo_id = batchinfo_id;
		this.batch = batch;
		this.student_count = student_count;
		this.degreeProgram = degreeProgram;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getBatchinfo_id() {
		return batchinfo_id;
	}

	public void setBatchinfo_id(long batchinfo_id) {
		this.batchinfo_id = batchinfo_id;
	}

	@ManyToOne
	@JoinColumn(name = "degreeProgram_id")
	public DegreeProgram getDegreeProgram() {
		return degreeProgram;
	}

	public void setDegreeProgram(DegreeProgram degreeProgram) {
		this.degreeProgram = degreeProgram;
	}

	public long getBatch() {
		return batch;
	}

	public void setBatch(long batch) {
		this.batch = batch;
	}

	public long getStudent_count() {
		return student_count;
	}

	public void setStudent_count(long student_count) {
		this.student_count = student_count;
	}
}
