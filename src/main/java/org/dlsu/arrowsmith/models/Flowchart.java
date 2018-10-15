package org.dlsu.arrowsmith.models;

import com.sun.javafx.geom.transform.BaseTransform;
import org.dlsu.arrowsmith.extraModels.CourseTimeframe;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Flowchart {
	private long flowchart_id;
	private int batch;
	private int year_level;
	private int start_year;
	private int end_year;
	private DegreeProgram degreeProgram;
	private Set<Flowcourse> flowcourses;

	public Flowchart() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getFlowchart_id() {
		return flowchart_id;
	}

	public void setFlowchart_id(long flowchart_id) {
		this.flowchart_id = flowchart_id;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public int getYear_level() {
		return year_level;
	}

	public void setYear_level(int year_level) {
		this.year_level = year_level;
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

	@ManyToOne
	@JoinColumn(name = "degreeProgram_id")
	public DegreeProgram getDegreeProgram() {
		return degreeProgram;
	}

	public void setDegreeProgram(DegreeProgram degreeProgram) {
		this.degreeProgram = degreeProgram;
	}

	@OneToMany(mappedBy = "flowchart", cascade = CascadeType.ALL)
	public Set<Flowcourse> getFlowcourses() {
		return flowcourses;
	}

	public void setFlowcourses(Set<Flowcourse> flowcourses) {
		this.flowcourses = flowcourses;
	}
}
