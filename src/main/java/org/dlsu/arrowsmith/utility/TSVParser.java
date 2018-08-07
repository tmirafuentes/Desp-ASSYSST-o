package org.dlsu.arrowsmith.utility;

import org.dlsu.arrowsmith.dao.CourseDAO;
import org.dlsu.arrowsmith.dao.DegreeprogramDAO;
import org.dlsu.arrowsmith.models.Course;
import org.dlsu.arrowsmith.models.CourseTimeframe;
import org.dlsu.arrowsmith.models.Flowchart;
import org.dlsu.arrowsmith.models.Timeframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class TSVParser {
	public static Flowchart readFlowchartFile(String path) {
		Flowchart flowchart = new Flowchart();
		Timeframe timeframe = new Timeframe();
		CourseTimeframe courseTimeframe = new CourseTimeframe();
		
		BufferedReader br = null;
		FileReader fr = null;

		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("Path: " + path);
		    System.out.println("File exists");
		}
		
		try {
			br = new BufferedReader(new FileReader(path));
//			br = new BufferedReader(new FileReader("C:\\Users\\Stewrat\\Desktop\\trialTSV.txt"));
			String currLine;
			String currYearLevel = "";
			String currStartYear = "";
			String currEndYear = "";
			
			while ((currLine = br.readLine()) != null) {
				String tempRead = currLine.replaceAll("\\s+", "");
				
				if(!tempRead.isEmpty() && tempRead != null) {
					String[] temp = currLine.split("\\t");
					System.out.println("currLine: " + currLine);
					
					if(temp[0].toUpperCase().equals("DEGREE PROGRAM")) {
						System.out.println("Degree Program: " + temp[1]);
						flowchart.setDegreeprogramId(DegreeprogramDAO.getIDByCode(temp[1]));
					} else if(temp[0].toUpperCase().equals("BATCH")) {
						flowchart.setBatch(temp[1]);
						System.out.println("Batch: " + flowchart.getBatch());
					} else if(temp[0].toUpperCase().equals("YEAR LEVEL")) {
						currYearLevel = temp[1];
					} else if(temp[0].toUpperCase().equals("AY")) {
						currStartYear = temp[1];
						currEndYear = temp[2];
					} else if(temp[0].toUpperCase().equals("TERM")) {
						timeframe.setTerm(temp[1]);
						timeframe.setStartYear(currStartYear);
						timeframe.setEndYear(currEndYear);
						
						courseTimeframe.setYearLevel(currYearLevel);
						courseTimeframe.setTimeframe(timeframe);

						System.out.println("Year Level: " + courseTimeframe.getYearLevel());
						System.out.println("AY: " + timeframe.getStartYear() + "-" + timeframe.getEndYear());
						System.out.println("Term: " + timeframe.getTerm());
						
						String currTerm = temp[1]; //for check new term
						
						while((currLine = br.readLine()) != null) {
							tempRead = currLine.replaceAll("\\s+", "");
							
							if(!tempRead.isEmpty() && tempRead != null) {
								temp = currLine.split("\\t");
								
								if(temp[0].toUpperCase().equals("TERM")) {
									if(!temp[1].equals(currTerm.toUpperCase())) {
										flowchart.addCourseTimeframe(courseTimeframe); //previous list of courses finally added
										
										currTerm = temp[1];
										
										timeframe = new Timeframe();
										timeframe.setTerm(temp[1]);
										timeframe.setStartYear(currStartYear);
										timeframe.setEndYear(currEndYear);
										
										courseTimeframe = new CourseTimeframe();
										courseTimeframe.setYearLevel(currYearLevel);
										courseTimeframe.setTimeframe(timeframe);

										System.out.println("Year Level: " + courseTimeframe.getYearLevel());
										System.out.println("AY: " + timeframe.getStartYear() + "-" + timeframe.getEndYear());
										System.out.println("Term: " + timeframe.getTerm());
										
									}
								} else if(temp[0].toUpperCase().equals("YEAR LEVEL")) {
									currYearLevel = temp[1];			
								} else if(temp[0].toUpperCase().equals("AY")) {
									currStartYear = temp[1];
									currEndYear = temp[2];
								} else if(!temp[0].toUpperCase().equals("COURSE CODE")) {
									Course newCourse = new Course();
									
									newCourse.setCourseId(CourseDAO.getIDByCode(temp[0]));
									
//									System.out.println("newCourse: " + temp[0]);
									
									if(newCourse.getCourseId() == null) {
										System.out.println(temp[0] + " not found!");
									}
									
									courseTimeframe.addCourse(newCourse);
								} 
							}
						}
					}
				}
			}
			
			flowchart.addCourseTimeframe(courseTimeframe); //final list added
			
			if (br != null) {
				br.close();
			}
				
			return flowchart;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;

	}
}
