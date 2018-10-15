package org.dlsu.arrowsmith.models;

import org.dlsu.arrowsmith.extraModels.Timeframe;

import javax.persistence.*;

@Entity
public class Load {
	private long load_id;
	private int start_year;
	private int end_year;
	private int term;
	private float admin_load;
	private float research_load;
	private float teaching_load;
	private float non_acad_load;
	private float deloading;
	private float total_load;
	private int preparations;
	private boolean has_research_load;
	private boolean is_admin;
	private boolean is_on_leave;
	private String leave_type;
	private Faculty faculty;
	private College college;
	private Department department;

	public Load() {
	}

	public Load(long load_id, int start_year, int end_year, int term, float admin_load, float research_load, float teaching_load, float non_acad_load, float deloading, float total_load, int preparations, boolean has_research_load, boolean is_admin, boolean is_on_leave, String leave_type, Faculty faculty, College college, Department department) {
		this.load_id = load_id;
		this.start_year = start_year;
		this.end_year = end_year;
		this.term = term;
		this.admin_load = admin_load;
		this.research_load = research_load;
		this.teaching_load = teaching_load;
		this.non_acad_load = non_acad_load;
		this.deloading = deloading;
		this.total_load = total_load;
		this.preparations = preparations;
		this.has_research_load = has_research_load;
		this.is_admin = is_admin;
		this.is_on_leave = is_on_leave;
		this.leave_type = leave_type;
		this.faculty = faculty;
		this.college = college;
		this.department = department;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getLoad_id() {
		return load_id;
	}

	public void setLoad_id(long load_id) {
		this.load_id = load_id;
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

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public float getAdmin_load() {
		return admin_load;
	}

	public void setAdmin_load(float admin_load) {
		this.admin_load = admin_load;
	}

	public float getResearch_load() {
		return research_load;
	}

	public void setResearch_load(float research_load) {
		this.research_load = research_load;
	}

	public float getTeaching_load() {
		return teaching_load;
	}

	public void setTeaching_load(float teaching_load) {
		this.teaching_load = teaching_load;
	}

	public float getNon_acad_load() {
		return non_acad_load;
	}

	public void setNon_acad_load(float non_acad_load) {
		this.non_acad_load = non_acad_load;
	}

	public float getDeloading() {
		return deloading;
	}

	public void setDeloading(float deloading) {
		this.deloading = deloading;
	}

	public float getTotal_load() {
		return total_load;
	}

	public void setTotal_load(float total_load) {
		this.total_load = total_load;
	}

	public int getPreparations() {
		return preparations;
	}

	public void setPreparations(int preparations) {
		this.preparations = preparations;
	}

	public boolean isHas_research_load() {
		return has_research_load;
	}

	public void setHas_research_load(boolean has_research_load) {
		this.has_research_load = has_research_load;
	}

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public boolean isIs_on_leave() {
		return is_on_leave;
	}

	public void setIs_on_leave(boolean is_on_leave) {
		this.is_on_leave = is_on_leave;
	}

	public String getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}

	@ManyToOne
	@JoinColumn(name = "faculty_id")
	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	@ManyToOne
	@JoinColumn(name = "course_id")
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
}
