package org.dlsu.arrowsmith.extraModels;

import org.dlsu.arrowsmith.models.Faculty;

public class LoadTimeblock {
	
	private LoadDay monday, tuesday, wednesday, thursday, friday, saturday, noday;
	private Boolean hasMonday = false, hasTuesday = false, hasWednesday = false, hasThursday = false, hasFriday = false, hasSaturday = false, hasNoday = false;
	private String beginTime, endTime;
	private Faculty faculty;
	
	public LoadTimeblock(){
		
	}
	
	public LoadTimeblock(String beginTime, String endTime){
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public LoadDay getMonday() {
		return monday;
	}

	public void setMonday(LoadDay monday) {
		this.monday = monday;
	}

	public LoadDay getTuesday() {
		return tuesday;
	}

	public void setTuesday(LoadDay tuesday) {
		this.tuesday = tuesday;
	}

	public LoadDay getWednesday() {
		return wednesday;
	}

	public void setWednesday(LoadDay wednesday) {
		this.wednesday = wednesday;
	}

	public LoadDay getThursday() {
		return thursday;
	}

	public void setThursday(LoadDay thursday) {
		this.thursday = thursday;
	}

	public LoadDay getFriday() {
		return friday;
	}

	public void setFriday(LoadDay friday) {
		this.friday = friday;
	}

	public LoadDay getSaturday() {
		return saturday;
	}

	public void setSaturday(LoadDay saturday) {
		this.saturday = saturday;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public LoadDay getNoday() {
		return noday;
	}

	public void setNoday(LoadDay noday) {
		this.noday = noday;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Boolean getHasMonday() {
		return hasMonday;
	}

	public void setHasMonday(Boolean hasMonday) {
		this.hasMonday = hasMonday;
	}

	public Boolean getHasTuesday() {
		return hasTuesday;
	}

	public void setHasTuesday(Boolean hasTuesday) {
		this.hasTuesday = hasTuesday;
	}

	public Boolean getHasWednesday() {
		return hasWednesday;
	}

	public void setHasWednesday(Boolean hasWednesday) {
		this.hasWednesday = hasWednesday;
	}

	public Boolean getHasThursday() {
		return hasThursday;
	}

	public void setHasThursday(Boolean hasThursday) {
		this.hasThursday = hasThursday;
	}

	public Boolean getHasFriday() {
		return hasFriday;
	}

	public void setHasFriday(Boolean hasFriday) {
		this.hasFriday = hasFriday;
	}

	public Boolean getHasSaturday() {
		return hasSaturday;
	}

	public void setHasSaturday(Boolean hasSaturday) {
		this.hasSaturday = hasSaturday;
	}

	public Boolean getHasNoday() {
		return hasNoday;
	}

	public void setHasNoday(Boolean hasNoday) {
		this.hasNoday = hasNoday;
	}
	
}
