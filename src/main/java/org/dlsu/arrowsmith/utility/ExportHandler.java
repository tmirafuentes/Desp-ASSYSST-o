package org.dlsu.arrowsmith.utility;

import org.dlsu.arrowsmith.dao.OfferingDAO;
import org.dlsu.arrowsmith.models.Constants;
import org.dlsu.arrowsmith.models.Offering;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExportHandler {
	public static ArrayList<String> exportTable(String table) {
		ArrayList<String> res = new ArrayList();
		
		
		
		return res;
	}
	
	public static ArrayList<String[]> exportTable(String table, String startYear, String endYear, String term){
		ArrayList<String[]> res = new ArrayList();
		ArrayList temp = new ArrayList();
		
		if(table.toUpperCase().equals("OFFERING")) {
			ArrayList<String> colNames = getAllTableColumns(table);
			
			String[] colHeaders = new String[colNames.size()];
			
			System.out.println("Im here");
			
			for (int i = 0; i < colNames.size(); i++) {
				colHeaders[i] = colNames.get(i);
			}
			
			res.add(colHeaders);
			
			System.out.println("start*end: " + startYear + "*" + endYear);
			
			try {
				temp = OfferingDAO.getListOfferingsByTerm(startYear, endYear, term);
				
				System.out.println("Now im here");
				
				for (Object object : temp) {
					Offering tempOffer = (Offering) object;
					
					System.out.println("tempOffer: " + tempOffer.getCourse().getCourseCode());
					
					System.out.println("size: -------------------------------------------------------------" + colNames.size());
					
					String[] colRows = new String[colNames.size()];
					int i = 0;

					if(tempOffer.getDegreeProgram() != null) {
						colRows[i++] = tempOffer.getDegreeProgram();
					} else {
						colRows[i++] = "";
					}
					
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getCourse().getCourseCode();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getFaculty().getFirstName();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getFaculty().getLastName();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					
					if(tempOffer.getSection() != null) {
						colRows[i++] = tempOffer.getSection();
					} else {
						colRows[i++] = "";
					}
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getBatch();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getTimeframe().getStartYear();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getTimeframe().getEndYear();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getTimeframe().getTerm();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					
					if(tempOffer.getDays() != null && !tempOffer.getDays().isEmpty()) {
						if(tempOffer.getDays() != null && !tempOffer.getDays().get(0).getClassDay().equals("")) {
							System.out.println("has days1");
							colRows[i++] = tempOffer.getDays().get(0).getClassDay();
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = tempOffer.getDays().get(0).getBeginTime();
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = tempOffer.getDays().get(0).getEndTime();
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							
							if(tempOffer.getDays().get(0).getRoom() != null) {
								colRows[i++] = tempOffer.getDays().get(0).getRoom().getRoomCode();
							} else {
								colRows[i++] = "No Room";
							}
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						} else {
							colRows[i++] = "None";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = 0 + "";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = 0 + "";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = "No Room";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						}
					} else {
						colRows[i++] = "No date assigned";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						colRows[i++] = 0 + "";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						colRows[i++] = 0 + "";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						colRows[i++] = "No Room";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					}
					
					if(tempOffer.getDays().size() > 1) {
						if(tempOffer.getDays() != null && !tempOffer.getDays().get(1).getClassDay().equals("")) {
							System.out.println("has days2");
							colRows[i++] = tempOffer.getDays().get(1).getClassDay();
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = tempOffer.getDays().get(1).getBeginTime();
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = tempOffer.getDays().get(1).getEndTime();
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							
							if(tempOffer.getDays().get(1).getRoom() != null) {
								colRows[i++] = tempOffer.getDays().get(1).getRoom().getRoomCode();
								System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							} else {
								colRows[i++] = "No Room";
								System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							}
						} else {
							colRows[i++] = "None";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = 0 + "";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = 0 + "";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
							colRows[i++] = "No Room";
							System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						}
					} else {
						colRows[i++] = "No second day";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						colRows[i++] = 0 + "";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						colRows[i++] = 0 + "";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
						colRows[i++] = "No Room";
						System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					}
					
					if(tempOffer.getRemarks() != null) {
						colRows[i++] = tempOffer.getRemarks();
					} else {
						colRows[i++] = "";
					}
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					
					colRows[i++] = tempOffer.getStatus();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getMaxStudentsEnrolled();
					System.out.println("i: " + (i-1) +  "--------------------------" + colRows[i-1] + "|" + colHeaders[i-1]);
					colRows[i++] = tempOffer.getCurrStudentsEnrolled();
					colRows[i++] = tempOffer.getIsNonAcad();
					colRows[i++] = tempOffer.getIsShs();
					colRows[i++] = tempOffer.getIsServiceCourse();
					colRows[i++] = tempOffer.getServicedeptId();
					colRows[i++] = tempOffer.getIsMasters();
					colRows[i++] = tempOffer.getIsElective();
					
					if(tempOffer.getElectiveType() != null) {
						colRows[i++] = tempOffer.getElectiveType();
					} else {
						colRows[i++] = "";
					}
					
					colRows[i++] = tempOffer.getIteoEval();
					
					System.out.println("i: " + (i-1) +  "--------------------------");
					colRows[i++] = tempOffer.getIsPublished();
					
					System.out.println("i: " + (i-1) +  "--------------------------");
					
					res.add(colRows);
					
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;		
	}
	
	public static ArrayList<String> getAllTableNames(){
		ArrayList<String> res = new ArrayList();
		
		res.add(Constants.COURSE_TABLE);
		res.add(Constants.COLLEGE_TABLE);
		res.add(Constants.DEPARTMENT_TABLE);
		res.add(Constants.FACULTY_TABLE);
		res.add(Constants.USERS_TABLE);
		res.add(Constants.LOADS_TABLE);
		res.add(Constants.OFFERING_TABLE);
		res.add(Constants.DAYS_TABLE);
		res.add(Constants.BUILDING_TABLE);
		res.add(Constants.ROOM_TABLE);
		res.add(Constants.DEGREEPROGRAM_TABLE);
		res.add(Constants.BATCHINFO_TABLE);
		res.add(Constants.FLOWCHART_TABLE);
		res.add(Constants.FLOWCOURSES_TABLE);
		res.add(Constants.EQUIVALENCE_TABLE);
		res.add(Constants.REQUISITE_TABLE);
		
		return res;
	}
	
	private static ArrayList<String> getAllTableColumns(String table){
		ArrayList<String> res = new ArrayList();
		
		switch(table.toUpperCase()) {
			case "COURSE": 
				res.add(Constants.COURSE_ID);
				res.add(Constants.COURSE_COLLEGEID);
				res.add(Constants.COURSE_DEPTID);
				res.add(Constants.COURSE_AREAID);
				res.add(Constants.COURSE_CODE);
				res.add(Constants.COURSE_NAME);
				res.add(Constants.COURSE_UNITS);
				res.add(Constants.COURSE_REMARKS);
				res.add(Constants.COURSE_DESCRIPTION);
				break;
			case "FACULTY": 
				res.add(Constants.FACULTY_ID);
				res.add(Constants.FACULTY_USERID);
				res.add(Constants.FACULTY_YEARSTARTED);
				res.add(Constants.FACULTY_TYPE);
				break;
			case "USERS": 
				res.add(Constants.USERS_ID);
				res.add(Constants.USERS_COLLEGEID);
				res.add(Constants.USERS_DEPTID);
				res.add(Constants.USERS_FIRSTNAME);
				res.add(Constants.USERS_MIDDLENAME);
				res.add(Constants.USERS_LASTNAME);
				res.add(Constants.USERS_TYPE);
				res.add(Constants.USERS_PASSWORD);
				break;
			case "COLLEGE": 
				res.add(Constants.COLLEGE_ID);
				res.add(Constants.COLLEGE_CODE);
				res.add(Constants.COLLEGE_NAME);
				res.add(Constants.COLLEGE_ISOBSOLETE);
				break;
			case "DEPARTMENT": 
				res.add(Constants.DEPT_ID);
				res.add(Constants.DEPT_CODE);
				res.add(Constants.DEPT_NAME);
				res.add(Constants.DEPT_SIZE);
				res.add(Constants.DEPT_COLLEGEID);
				res.add(Constants.DEPT_ISOBSOLETE);
				res.add(Constants.COURSE_UNITS);
				res.add(Constants.COURSE_REMARKS);
				res.add(Constants.COURSE_DESCRIPTION);
				break;
			case "DAYS": 
				res.add(Constants.DAYS_ID);
				res.add(Constants.DAYS_OFFERINGID);
				res.add(Constants.DAYS_ROOMID);
				res.add(Constants.DAYS_STARTYEAR);
				res.add(Constants.DAYS_ENDYEAR);
				res.add(Constants.DAYS_TERM);
				res.add(Constants.DAYS_CLASSDAY);
				res.add(Constants.DAYS_BEGINTIME);
				res.add(Constants.DAYS_ENDTIME);
				break;
			case "EQUIVALENCE": 
				res.add(Constants.EQUIVALENCE_COURSEID);
				res.add(Constants.EQUIVALENCE_ID);
				break;
			case "LOADS": 
				res.add(Constants.LOADS_ID);
				res.add(Constants.LOADS_FACULTYID);
				res.add(Constants.LOADS_COLLEGEID);
				res.add(Constants.LOADS_DEPTID);
				res.add(Constants.LOADS_STARTYEAR);
				res.add(Constants.LOADS_ENDYEAR);
				res.add(Constants.LOADS_TERM);
				res.add(Constants.LOADS_ADMINLOAD);
				res.add(Constants.LOADS_RESEARCHLOAD);
				res.add(Constants.LOADS_TEACHINGLOAD);
				res.add(Constants.LOADS_NONACADLOAD);
				res.add(Constants.LOADS_DELOADING);
				res.add(Constants.LOADS_TOTALLOAD);
				res.add(Constants.LOADS_PREPARATIONS);
				res.add(Constants.LOADS_HASRESEARCHLOAD);
				res.add(Constants.LOADS_ISADMIN);
				res.add(Constants.LOADS_LEAVETYPE);
				res.add(Constants.LOADS_ISONLEAVE);
				res.add(Constants.LOADS_TIMESTAMP);
				break;
			case "OFFERING": //cleaned
				res.add(Constants.OFFERING_DEGREEPROGRAM);
				res.add(Constants.COURSE_CODE);
				res.add(Constants.USERS_FIRSTNAME);
				res.add(Constants.USERS_LASTNAME);
				res.add(Constants.OFFERING_SECTION);
				res.add(Constants.OFFERING_BATCH);
				res.add(Constants.OFFERING_STARTYEAR);
				res.add(Constants.OFFERING_ENDYEAR);
				res.add(Constants.OFFERING_TERM);
				res.add(Constants.DAYS_CLASSDAY);
				res.add(Constants.DAYS_BEGINTIME);
				res.add(Constants.DAYS_ENDTIME);
				res.add(Constants.ROOM_CODE);
				res.add(Constants.DAYS_CLASSDAY);
				res.add(Constants.DAYS_BEGINTIME);
				res.add(Constants.DAYS_ENDTIME);
				res.add(Constants.ROOM_CODE);
				res.add(Constants.OFFERING_REMARKS);
				res.add(Constants.OFFERING_STATUS);
				res.add(Constants.OFFERING_MAXSTUDENTSENROLLED);
				res.add(Constants.OFFERING_CURRSTUDENTSENROLLED);
				res.add(Constants.OFFERING_ISNONACAD);
				res.add(Constants.OFFERING_ISSHS);
				res.add(Constants.OFFERING_ISSERVICECOURSE);
				res.add(Constants.OFFERING_SERVICEDEPTID);
				res.add(Constants.OFFERING_ISMASTERS);
				res.add(Constants.OFFERING_ISELECTIVE);
				res.add(Constants.OFFERING_ELECTIVETYPE);
				res.add(Constants.OFFERING_ITEOEVAL);
				res.add(Constants.OFFERING_ISPUBLISHED);
				break;
			case "BUILDING": 
				res.add(Constants.BUILDING_ID);
				res.add(Constants.BUILDING_NAME);
				res.add(Constants.BUILDING_CODE);
				break;
			case "ROOM": 
				res.add(Constants.ROOM_ID);
				res.add(Constants.ROOM_CODE);
				res.add(Constants.ROOM_LOCATION);
				res.add(Constants.ROOM_TYPE);
				res.add(Constants.ROOM_CAPACITY);
				res.add(Constants.ROOM_BUILDINGID);
				break;
			case "BATCHINFO": 
				res.add(Constants.BATCHINFO_ID);
				res.add(Constants.BATCHINFO_DEGREEPROGRAMID);
				res.add(Constants.BATCHINFO_BATCH);
				res.add(Constants.BATCHINFO_STUDENTCOUNT);
				break;
			case "DEGREEPROGRAM": 
				res.add(Constants.DEGREEPROGRAM_ID);
				res.add(Constants.DEGREEPROGRAM_COLLEGEID);
				res.add(Constants.DEGREEPROGRAM_DEPTID);
				res.add(Constants.DEGREEPROGRAM_CODE);
				res.add(Constants.DEGREEPROGRAM_NAME);
				break;
			case "FLOWCHART": 
				res.add(Constants.FLOWCHART_ID);
				res.add(Constants.FLOWCHART_DEGREEPROGRAMID);
				res.add(Constants.FLOWCHART_BATCH);
				res.add(Constants.FLOWCHART_YEARLEVEL);
				res.add(Constants.FLOWCHART_STARTYEAR);
				res.add(Constants.FLOWCHART_ENDYEAR);
				break;
			case "FLOWCOURSES": 
				res.add(Constants.FLOWCOURSES_FLOWCHARTID);
				res.add(Constants.FLOWCOURSES_COURSEID);
				res.add(Constants.FLOWCOURSES_TERM);
				break;
			case "REQUISITE": 
				res.add(Constants.REQUISITE_COURSEID);
				res.add(Constants.REQUISITE_REQCOURSEID);
				res.add(Constants.REQUISITE_REQTYPE);
				break;
		}
		
		return res;
	}
}
