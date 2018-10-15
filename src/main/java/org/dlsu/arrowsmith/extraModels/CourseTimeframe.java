package org.dlsu.arrowsmith.extraModels;

import org.dlsu.arrowsmith.models.Course;

import java.util.ArrayList;

public class CourseTimeframe {
	private String yearLevel;
	private Timeframe timeframe;
	private ArrayList<Course> courseList;
	
	public CourseTimeframe() {
		this.courseList = new ArrayList();
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(Timeframe timeframe) {
		this.timeframe = timeframe;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}
	
	public void addCourse(Course course) {
		this.courseList.add(course);
	}

	public String getYearLevel() {
		return yearLevel;
	}

	public void setYearLevel(String yearLevel) {
		this.yearLevel = yearLevel;
	}
}
