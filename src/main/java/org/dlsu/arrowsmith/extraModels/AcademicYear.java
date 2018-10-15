package org.dlsu.arrowsmith.extraModels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AcademicYear {
	private String id,
			AY,
			batch,
			term,
			startYear,
			endYear,
			isPublished; //id should be concatenation of AY, batch, term separated by (-)
	private String unPublishedCount;
	
	public AcademicYear(){
		
	}
	
	public AcademicYear(String startYear, String endYear, String term){
		this.startYear = startYear;
		this.endYear = endYear;
		this.term = term;
	}
	
	public AcademicYear(String id, String AY, String startYear, String endYear, String term, String batch, String isPublished){
		this.id = id;
		this.AY = AY;
		this.startYear = startYear;
		this.endYear = endYear;
		this.batch = batch;
		this.term = term;
		this.isPublished = isPublished;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAY() {
		return AY;
	}

	public void setAY(String aY) {
		AY = aY;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public void setUnPublishedCount(String unPublishedCount) {
		this.unPublishedCount = unPublishedCount;
		if(Integer.parseInt(unPublishedCount) > 0){
			setIsPublished("0");
		}else setIsPublished("1");
	}

	public String getUnPublishedCount() {
		return unPublishedCount;
	}

}
