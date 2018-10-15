package org.dlsu.arrowsmith.models;

import org.dlsu.arrowsmith.extraModels.Timeframe;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Offering {
	private long offering_id;
	private String degree_program;
	private String section;
	private int batch;
	private int term;
	private int start_year;
	private int end_year;
	private int max_students_enrolled;
	private int curr_students_enrolled;
	private boolean is_non_acad;
	private boolean is_shs;
	private boolean is_service_course;
	private boolean is_masters;
	private boolean is_elective;
	private String elective_type;
	private String offering_status;
	private float iteo_eval;
	private boolean is_published;
	private Course course;
	private Faculty faculty;
	private Set<Course> electiveCourses;
	private Set<Days> days;

	public Offering() {
	}

	public Offering(long offering_id, String degree_program, String section, int batch, int term, int start_year, int end_year, int max_students_enrolled, int curr_students_enrolled, boolean is_non_acad, boolean is_shs, boolean is_service_course, boolean is_masters, boolean is_elective, String elective_type, String offering_status, float iteo_eval, boolean is_published, Course course, Faculty faculty) {
		this.offering_id = offering_id;
		this.degree_program = degree_program;
		this.section = section;
		this.batch = batch;
		this.term = term;
		this.start_year = start_year;
		this.end_year = end_year;
		this.max_students_enrolled = max_students_enrolled;
		this.curr_students_enrolled = curr_students_enrolled;
		this.is_non_acad = is_non_acad;
		this.is_shs = is_shs;
		this.is_service_course = is_service_course;
		this.is_masters = is_masters;
		this.is_elective = is_elective;
		this.elective_type = elective_type;
		this.offering_status = offering_status;
		this.iteo_eval = iteo_eval;
		this.is_published = is_published;
		this.course = course;
		this.faculty = faculty;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getOffering_id() {
		return offering_id;
	}

	public void setOffering_id(long offering_id) {
		this.offering_id = offering_id;
	}

	public String getDegree_program() {
		return degree_program;
	}

	public void setDegree_program(String degree_program) {
		this.degree_program = degree_program;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
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

	public int getMax_students_enrolled() {
		return max_students_enrolled;
	}

	public void setMax_students_enrolled(int max_students_enrolled) {
		this.max_students_enrolled = max_students_enrolled;
	}

	public int getCurr_students_enrolled() {
		return curr_students_enrolled;
	}

	public void setCurr_students_enrolled(int curr_students_enrolled) {
		this.curr_students_enrolled = curr_students_enrolled;
	}

	public boolean isIs_non_acad() {
		return is_non_acad;
	}

	public void setIs_non_acad(boolean is_non_acad) {
		this.is_non_acad = is_non_acad;
	}

	public boolean isIs_shs() {
		return is_shs;
	}

	public void setIs_shs(boolean is_shs) {
		this.is_shs = is_shs;
	}

	public boolean isIs_service_course() {
		return is_service_course;
	}

	public void setIs_service_course(boolean is_service_course) {
		this.is_service_course = is_service_course;
	}

	public boolean isIs_masters() {
		return is_masters;
	}

	public void setIs_masters(boolean is_masters) {
		this.is_masters = is_masters;
	}

	public boolean isIs_elective() {
		return is_elective;
	}

	public void setIs_elective(boolean is_elective) {
		this.is_elective = is_elective;
	}

	public String getElective_type() {
		return elective_type;
	}

	public void setElective_type(String elective_type) {
		this.elective_type = elective_type;
	}

	public String getOffering_status() {
		return offering_status;
	}

	public void setOffering_status(String offering_status) {
		this.offering_status = offering_status;
	}

	public float getIteo_eval() {
		return iteo_eval;
	}

	public void setIteo_eval(float iteo_eval) {
		this.iteo_eval = iteo_eval;
	}

	public boolean isIs_published() {
		return is_published;
	}

	public void setIs_published(boolean is_published) {
		this.is_published = is_published;
	}

	@ManyToOne
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@ManyToOne
	@JoinColumn(name = "faculty_id")
	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	@ManyToMany(mappedBy = "offering")
	public Set<Course> getElectiveCourses() {
		return electiveCourses;
	}

	public void setElectiveCourses(Set<Course> electiveCourses) {
		this.electiveCourses = electiveCourses;
	}

	@OneToMany(mappedBy = "offering", cascade = CascadeType.ALL)
	public Set<Days> getDays() {
		return days;
	}

	public void setDays(Set<Days> days) {
		this.days = days;
	}
}
