package org.dlsu.arrowsmith.models;

public class BatchInfo {

	private String batchinfo_id, degreeprogram_id, batch, student_count;
	
	public BatchInfo(){
		
	}
	
	public BatchInfo(String batchinfo_id, String degreeprogram_id, String batch, String student_count){
		this.batchinfo_id = batchinfo_id;
		this.degreeprogram_id = degreeprogram_id;
		this.batch = batch;
		this.student_count = student_count;
	}

	public String getBatchinfo_id() {
		return batchinfo_id;
	}

	public void setBatchinfo_id(String batchinfo_id) {
		this.batchinfo_id = batchinfo_id;
	}

	public String getDegreeprogram_id() {
		return degreeprogram_id;
	}

	public void setDegreeprogram_id(String degreeprogram_id) {
		this.degreeprogram_id = degreeprogram_id;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getStudent_count() {
		return student_count;
	}

	public void setStudent_count(String student_count) {
		this.student_count = student_count;
	}
	
}
