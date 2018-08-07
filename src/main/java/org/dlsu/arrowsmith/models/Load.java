package org.dlsu.arrowsmith.models;

public class Load {
	private String loadId;
	private String facultyId;
	private String collegeId;
	private String deptId;
	private String adminLoad;
	private String researchLoad;
	private String teachingLoad;
	private String nonAcadLoad;
	private String deloading;
	private String totalLoad;
	private String preparations;
	private String isOnLeave;
	private String leaveType;
	private String timestamp;

	private Timeframe timeframe;

	public Load(String facultyId, String collegeId, String deptId, String adminLoad, String researchLoad,
			String teachingLoad, String nonAcadLoad, String deloading, String totalLoad, String preparations, String isOnLeave, String leaveType){
		this.facultyId = facultyId;
		this.collegeId = collegeId;
		this.deptId = deptId;
		this.adminLoad = adminLoad;
		this.researchLoad = researchLoad;
		this.teachingLoad = teachingLoad;
		this.nonAcadLoad = nonAcadLoad;
		this.deloading = deloading;
		this.totalLoad = totalLoad;
		this.preparations = preparations;
		this.isOnLeave = isOnLeave;
		this.leaveType = leaveType;
	}
	
	public Load(){
		
	}
	
	public String getLoadId() {
		return loadId;
	}

	public void setLoadId(String loadId) {
		this.loadId = loadId;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	public String getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getAdminLoad() {
		return adminLoad;
	}

	public void setAdminLoad(String adminLoad) {
		this.adminLoad = adminLoad;
	}

	public String getResearchLoad() {
		return researchLoad;
	}

	public void setResearchLoad(String researchLoad) {
		this.researchLoad = researchLoad;
	}

	public String getTeachingLoad() {
		return teachingLoad;
	}

	public void setTeachingLoad(String teachingLoad) {
		this.teachingLoad = teachingLoad;
	}

	public String getNonAcadLoad() {
		return nonAcadLoad;
	}

	public void setNonAcadLoad(String nonAcadLoad) {
		this.nonAcadLoad = nonAcadLoad;
	}

	public String getDeloading() {
		return deloading;
	}

	public void setDeloading(String deloading) {
		this.deloading = deloading;
	}

	public String getTotalLoad() {
		return totalLoad;
	}

	public void setTotalLoad(String totalLoad) {
		this.totalLoad = totalLoad;
	}

	public String getPreparations() {
		return preparations;
	}

	public void setPreparations(String preparations) {
		this.preparations = preparations;
	}

	public String getIsOnLeave() {
		return isOnLeave;
	}

	public void setIsOnLeave(String isOnLeave) {
		this.isOnLeave = isOnLeave;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(Timeframe timeframe) {
		this.timeframe = timeframe;
	}

	
}
