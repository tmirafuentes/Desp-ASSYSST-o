package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.*;
import java.util.ArrayList;

public class OfferingDAO {
	
	public static ArrayList<Offering> getListOfferingsByTerm(String startYear, String endYear, String term) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " o, " + Constants.COURSE_TABLE + " c WHERE o." 
        		+ Constants.COURSE_ID + " = c." + Constants.COURSE_ID + " AND o." + Constants.OFFERING_STARTYEAR 
        		+ " = ? AND o." + Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ? "
        		+ "ORDER BY c." + Constants.COURSE_CODE + ", o."+ Constants.OFFERING_SECTION + ", o." + Constants.OFFERING_BATCH
        		+ ", o." + Constants.OFFERING_STATUS;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
        	Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
//            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
//            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
//            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            //System.out.println(o.getFaculty().getLastName() + "mff" + o.getFaculty().getFirstName());
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }
	
	public static ArrayList<Offering> getListOfferingsByTermWithKey(String startYear, String endYear, String term, String searchKeyword) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " o, " + Constants.COURSE_TABLE + " c WHERE o." 
        		+ Constants.COURSE_ID + " = c." + Constants.COURSE_ID + " AND o." + Constants.OFFERING_STARTYEAR 
        		+ " = ? AND o." + Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ? "
        		+ "AND (c." + Constants.COURSE_CODE + " LIKE '%" + searchKeyword + "%' OR c." + Constants.COURSE_NAME + " LIKE '%" + searchKeyword + "%') "
        		+ "ORDER BY c." + Constants.COURSE_CODE + ", o."+ Constants.OFFERING_SECTION + ", o." + Constants.OFFERING_BATCH
        		+ ", o." + Constants.OFFERING_STATUS;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
        	Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            //System.out.println(o.getFaculty().getLastName() + "mff" + o.getFaculty().getFirstName());
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }
	
	public static ArrayList<Offering> getListOfferingsByFaculty(String facultyId, String startYear, String endYear, String term) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " o, " + Constants.COURSE_TABLE + " c, " + Constants.FACULTY_TABLE + " f WHERE o." 
        		+ Constants.FACULTY_ID + " = f." + Constants.FACULTY_ID + " AND o." + Constants.OFFERING_STARTYEAR 
        		+ " = ? AND o." + Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ? "
        		+ "AND f." + Constants.FACULTY_ID + " = "+ facultyId + " AND o." + Constants.OFFERING_COURSEID + " = c." + Constants.COURSE_ID 
        		+ " ORDER BY c." + Constants.COURSE_CODE;
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
        	Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            //System.out.println(o.getFaculty().getLastName() + "mff" + o.getFaculty().getFirstName());
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }
	
	public static ArrayList<Offering> getListOfferingsByTermWithFilters(String startYear, String endYear, String term, String[] days, String timeblock, String batch, String status, String roomType, String key) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        
        if(!key.isEmpty() && removeSpaces(key).isEmpty()){
        	key = removeSpaces(key); //para empty na as in kasi pag pumasok dito ibig sabihin puro white spaces. Acts like SELECT ALL.
        }
        /****
         * 
         * 	SELECT * 
			FROM offering o, room r, days d, course c 
			WHERE o.start_year = 2012 AND o.end_year = 2013 AND o.term = 2 AND c.course_id = o.course_id AND 
			d.offering_id = o.offering_id AND d.room_id = r.room_id AND
			(d.class_day LIKE '%M%' OR d.class_day LIKE '%T%') AND c.course_code LIKE '%is%' AND r.room_type 
			LIKE '%no%' AND o.offering_status LIKE '%reg%' AND o.batch = 113 AND d.begin_time = 940 
			AND d.end_time = 1110
         * 
         */
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " o, "+ Constants.ROOM_TABLE + " r, "+ Constants.DAYS_TABLE + " d, "+ Constants.COURSE_TABLE +" c "
        		+ "WHERE c." + Constants.COURSE_ID + " = o."+ Constants.OFFERING_COURSEID +" AND "
        		+ "o." + Constants.OFFERING_ID + " = d."+ Constants.DAYS_OFFERINGID +" AND "
        		+ "r." + Constants.ROOM_ID + " = d."+ Constants.DAYS_ROOMID +" AND "
        		+ "o." + Constants.OFFERING_STARTYEAR + " = ? AND o."+ Constants.OFFERING_ENDYEAR +" = ? AND o."+ Constants.OFFERING_TERM +" = ? AND " 
        		+ "c." + Constants.COURSE_CODE + " LIKE '%" + key +"%'";
        		
		        if(!roomType.equalsIgnoreCase("All")){
		        	query+= " AND r." + Constants.ROOM_TYPE + " LIKE '%" + roomType +"%'";
		        }
		        
		        if(!status.equalsIgnoreCase("All")){
		        	query+= " AND o." + Constants.OFFERING_STATUS + " LIKE '%" + status +"%'";
		        }
		        
		        if(!batch.equalsIgnoreCase("All")){
		        	query+= " AND o." + Constants.OFFERING_BATCH + " = " + batch ;
		        }
		        
		        if(!timeblock.equalsIgnoreCase("All")){
		        	String[] time=timeblock.split("-");
		        	
		        	query+= " AND d." + Constants.DAYS_BEGINTIME + " = " + time[0]; //begin time
		        	query+= " AND d." + Constants.DAYS_ENDTIME + " = " + time[1]; //begin time
		        }
		        
		        if(days.length == 0){
		        	query+= " AND d." + Constants.DAYS_CLASSDAY + " LIKE '%none%'";
		        }else{
		        	query+= " AND (";
		        	for(int i = 0; i < days.length; i++){
		        		query+= " d." + Constants.DAYS_CLASSDAY + " LIKE '%"+ days[i] +"%'";
		        		
		        		if((days.length-1) > i) query+= " OR";
		        	}
		        	query+=")";
		        }
        		query+=" ORDER BY c." + Constants.COURSE_CODE +" asc;";
        
        //System.out.println(query);
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
//        System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
        	Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return combineOfferings(offerings);
    }
	
	public static ArrayList<Offering> getListOfferingsByTermWithCourseKey(String startYear, String endYear, String term, String key) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        
        if(!key.isEmpty() && removeSpaces(key).isEmpty()){
        	key = removeSpaces(key); //para empty na as in kasi pag pumasok dito ibig sabihin puro white spaces. Acts like SELECT ALL.
        }
        
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " o, "+ Constants.COURSE_TABLE +" c WHERE "
        		+ "c." + Constants.COURSE_ID + " = o."+ Constants.OFFERING_COURSEID +" AND o." + Constants.OFFERING_STARTYEAR 
        		+ " = ? AND o." + Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ?"
        		+ " AND c." + Constants.COURSE_CODE 
        		+ " LIKE '%"+key+"%' ORDER BY c." + Constants.COURSE_CODE +" asc;";
        
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
//        System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
        	Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }
	
	public static Offering getOfferingByID(String id) throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " ot WHERE ot." + Constants.OFFERING_ID + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, id);
        Offering o = new Offering();
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
        }
        
        con.close();
        st.close();
        
        return o;
    }
	
	public static int countUnpublished(String startYear, String endYear, String term) throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT COUNT(*) AS unpublished FROM " + Constants.OFFERING_TABLE + " WHERE " + Constants.OFFERING_STARTYEAR + " = ? AND " + Constants.OFFERING_ENDYEAR + " = ? AND " + Constants.OFFERING_TERM + " = ? AND " + Constants.OFFERING_ISPUBLISHED + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        st.setInt(4, 0); //0 kasi unPublished is zero
        
        int unpublished = 0;
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            unpublished = Integer.parseInt((rs.getString("unpublished")));
        }
        
        con.close();
        st.close();
        
        return unpublished;
    }
	
	public static ArrayList<Offering> getAllOfferingsWithIsPublished(String startYear, String endYear, String term, String state) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " ot WHERE ot." + Constants.OFFERING_STARTYEAR + " = ? AND ot." + Constants.OFFERING_ENDYEAR + " = ? AND ot." + Constants.OFFERING_TERM + " = ? AND ot." + Constants.OFFERING_ISPUBLISHED + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        st.setString(4, state);
        
//        System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
        	Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }
	
	public static void publishOfferings(String startYear, String endYear, String term, String state) throws SQLException {
		ArrayList<Offering> offerings = new ArrayList<Offering>();
        String invertedState = "0";
        
        if(state.equalsIgnoreCase("0")) invertedState = "1";
        else invertedState = "0";
        
        offerings = getAllOfferingsWithIsPublished(startYear, endYear, term, invertedState); //get All with old publish state
        
        for(int i = 0; i< offerings.size(); i++){
        	Connection con = Connector.getConnector();
	        String query = "UPDATE " + Constants.OFFERING_TABLE + " SET "+ Constants.OFFERING_ISPUBLISHED +" = ? WHERE " + Constants.OFFERING_ID + " = ?;";
	        
	        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
	        st.setInt(1, Integer.parseInt(state));
	        st.setInt(2, Integer.parseInt(offerings.get(i).getOfferingId()));
	        
	        st.executeUpdate(); //use execute update instead of execute query
	           
	        con.close();
	        st.close();
        }
	}
	
	public static ArrayList<String> getAllUniqueBatch(String startYear, String endYear, String term) throws SQLException{   
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT("+ Constants.OFFERING_BATCH +") FROM "+ Constants.OFFERING_TABLE +
        		" WHERE " + Constants.OFFERING_STARTYEAR + " = ? AND "+ Constants.OFFERING_ENDYEAR + " = ? AND " + Constants.OFFERING_TERM + " = ?"+
        		" ORDER BY "+ Constants.OFFERING_BATCH +";";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(startYear));
        st.setInt(2, Integer.parseInt(endYear));
        st.setInt(3, Integer.parseInt(term));
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString(Constants.OFFERING_BATCH);
        	
        	sList.add(s);
        }
        
        con.close();
        st.close();
        
        return sList;
    }
	
	public static ArrayList<String> getAllUniqueStatus(String startYear, String endYear, String term) throws SQLException{   
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT("+ Constants.OFFERING_STATUS +") FROM "+ Constants.OFFERING_TABLE +
        		" WHERE " + Constants.OFFERING_STARTYEAR + " = ? AND "+ Constants.OFFERING_ENDYEAR + " = ? AND " + Constants.OFFERING_TERM + " = ?"+
        		" ORDER BY "+ Constants.OFFERING_STATUS +";";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(startYear));
        st.setInt(2, Integer.parseInt(endYear));
        st.setInt(3, Integer.parseInt(term));
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString(Constants.OFFERING_STATUS);
        	
        	sList.add(s);
        }
        
        con.close();
        st.close();
        
        return sList;
    }
    
	public static ArrayList<String> getAllUniqueAY() throws SQLException{
		ArrayList<String> res = new ArrayList();
		Connection con = Connector.getConnector();
		
		String query = "SELECT DISTINCT(CONCAT(" + Constants.OFFERING_STARTYEAR + ", '-', " + Constants.OFFERING_ENDYEAR +
							")) as academic_year FROM " + Constants.OFFERING_TABLE + " ORDER BY academic_year DESC";
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
		
		System.out.println(st);
		
		ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString("academic_year");
        	
        	res.add(s);
        }
        
        con.close();
        st.close();
		
		return res;
	}
	
    public static int addNewOfferingsToDB(String startYear, String endYear, String term, String degreeProgram, String courseId, String courseCode, String section,
    									String batch, String status, String remarks, String room, String facultyId, String[] days1, String[] days2, String time1, String time2) throws SQLException{
    	ArrayList<Days> days = new ArrayList<Days>();
    	
    	String[] arr, arr2;
    	String beginTime1 = "0000";
    	String endTime1 = "0000";
    	String beginTime2 = "000";
    	String endTime2 = "0000";
    	String newFacultyId = "";

    	if(time1.contains("-")){
	    	arr = time1.split("-");
	    	beginTime1 = arr[0];
	    	endTime1 = arr[1];
    	}
    	if(time2.contains("-")){
	    	arr2 = time2.split("-");
	    	beginTime2 = arr2[0];
	    	endTime2 = arr2[1];
    	}
    	
    	if(facultyId.equalsIgnoreCase("") || facultyId.isEmpty()) newFacultyId = Constants.NO_FACULTY_ID;
    	else newFacultyId = facultyId;
    	
    	for(int i = 0; i < days1.length; i++){
    		boolean found = false;
    		Days d = new Days();
    		
    		for(int j = 0; j< days.size(); j++){
    			if(days1[i].equalsIgnoreCase(days.get(j).getClassDay()) &&
    			   beginTime1.equalsIgnoreCase(days.get(j).getBeginTime()) &&
    			   endTime1.equalsIgnoreCase(days.get(j).getEndTime())){
    				found = true;
    			}	
    		}
    		if(!found && !days1[i].equalsIgnoreCase(Constants.NO_CLASSDAY_CONSTANT))
				days.add(new Days(days1[i], beginTime1, endTime1));
    	}
    	
    	for(int i = 0; i < days2.length; i++){
    		boolean found = false;
    		Days d = new Days();
    		
    		for(int j = 0; j< days.size(); j++){
    			if(days2[i].equalsIgnoreCase(days.get(j).getClassDay()) &&
    			   beginTime2.equalsIgnoreCase(days.get(j).getBeginTime()) &&
    			   endTime2.equalsIgnoreCase(days.get(j).getEndTime())){
    				found = true;
    			}
    		}
			if(!found && !days2[i].equalsIgnoreCase(Constants.NO_CLASSDAY_CONSTANT))
				days.add(new Days(days2[i], beginTime2, endTime2));
    	}
    	
    	insertOffering(startYear, endYear, term, degreeProgram, courseId, section, batch, status, remarks, newFacultyId);
    	int lastId = getLastRecordIndex();
    	//System.out.println("COURSE CODE: " +courseCode);
    	//System.out.println("LAST ID: " +lastId);
    	//System.out.println(days.size());
    	if(days.size() == 0); 
    	for(int i = 0; i<days.size(); i++){
    		DaysDAO.insertDay(days.get(i), lastId, startYear, endYear, term, room);
    	}
    	
    	return 1;
    }
    
    public static void insertOffering(String startYear, String endYear, String term, String degreeProgram, String courseId, String section, String batch, String status, String remarks, String facultyId){
    	int maxStudent = 45, currStudent = 0, isNonAcad = 0, isSHS = 0, isMasters = 0, isElective = 0, isServiceCourse = 0, isPublished = 0;
    	String electiveType = " ", location = "Main";
    	float iteo = 5.0f;
    	
    	if(batch.isEmpty() || removeSpaces(batch).equalsIgnoreCase("")){
    		batch = "0";
    	}
    	if(startYear.isEmpty() || removeSpaces(startYear).equalsIgnoreCase("")){
    		startYear = "9999";
    	}
    	if(endYear.isEmpty() || removeSpaces(endYear).equalsIgnoreCase("")){
    		endYear = "9999";
    	}
    	if(term.isEmpty() || removeSpaces(term).equalsIgnoreCase("")){
    		term = "1";
    	}
    	
    	if(facultyId.equalsIgnoreCase("") || facultyId.isEmpty()) facultyId = Constants.NO_FACULTY_ID;
    	
        try{
            Connection con = new Connector().getConnector();
            PreparedStatement ps;
            String query = "INSERT INTO " + Constants.OFFERING_TABLE + " (" +
                    Constants.OFFERING_COURSEID + ", " + Constants.OFFERING_FACULTYID + ", " + Constants.OFFERING_DEGREEPROGRAM + ", " + Constants.OFFERING_SECTION + ", " +
                    Constants.OFFERING_BATCH + ", " + Constants.OFFERING_TERM + ", " + Constants.OFFERING_STARTYEAR + ", " + Constants.OFFERING_ENDYEAR + ", " +
                    Constants.OFFERING_MAXSTUDENTSENROLLED + ", " + Constants.OFFERING_CURRSTUDENTSENROLLED + ", " + Constants.OFFERING_ISNONACAD + ", " + Constants.OFFERING_ISSHS + ", " +
                    Constants.OFFERING_ISSERVICECOURSE + ", " + Constants.OFFERING_SERVICEDEPTID + ", " + Constants.OFFERING_ISMASTERS + ", " + Constants.OFFERING_ISELECTIVE + ", " + Constants.OFFERING_ELECTIVETYPE + ", " +
                    Constants.OFFERING_REMARKS + ", " + Constants.OFFERING_STATUS + ", " + Constants.OFFERING_ISPUBLISHED + ", " + Constants.OFFERING_ITEOEVAL + 
                    ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            ps = con.prepareStatement(query);
            ps.setInt(1,Integer.parseInt(courseId));
            ps.setInt(2, Integer.parseInt(facultyId));
            ps.setString(3, degreeProgram);
            ps.setString(4, section);
            ps.setInt(5, Integer.parseInt(batch));
            ps.setInt(6, Integer.parseInt(term));
            ps.setInt(7, Integer.parseInt(startYear));
            ps.setInt(8, Integer.parseInt(endYear));
            ps.setInt(9, maxStudent);
            ps.setInt(10, currStudent);
            ps.setInt(11, isNonAcad);
            ps.setInt(12, isSHS);
            ps.setInt(13, isServiceCourse);
            ps.setInt(14, Integer.parseInt(Constants.NO_DEPARTMENT_ID));
            ps.setInt(15, isMasters);
            ps.setInt(16, isElective);
            ps.setString(17, electiveType);
            ps.setString(18, remarks);
            ps.setString(19, status);
            ps.setInt(20, isPublished);
            ps.setFloat(21, iteo);

            ps.executeUpdate();

            ps.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void deleteAnOffering(String offeringId) throws SQLException{
    	ArrayList<Days> days = new ArrayList<Days>();
    	days = DaysDAO.getListDays(offeringId);
    	
    	try{
            Connection con = new Connector().getConnector();
            PreparedStatement ps;
            String query = "DELETE FROM " + Constants.OFFERING_TABLE + " WHERE " + Constants.OFFERING_ID + " = " + offeringId + ";";
            ps = con.prepareStatement(query);
            ps.executeUpdate();

            ps.close();
            con.close();
    	}catch(Exception e){
            e.printStackTrace();
        }
    	
    	//delete all associated days with the deleted offering
    	for(int i = 0; i < days.size(); i++){
    		DaysDAO.deleteADay(days.get(i).getDaysId());
    	}
    }
    
    public static void deleteAnOfferingList(String startYear, String endYear, String term) throws SQLException{
    	ArrayList<Offering> offerings = new ArrayList<Offering>();
    	offerings = OfferingDAO.getListOfferingsByTerm(startYear, endYear, term);
    	
    	//delete all offerings of the list
    	for(int i = 0; i < offerings.size(); i++){
    		OfferingDAO.deleteAnOffering(offerings.get(i).getOfferingId());
    	}
    }
    
    public static int getLastRecordIndex() throws SQLException{
        Connection con = new Connector().getConnector();
        Statement st = null;
        //String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " ORDER BY " + Constants.OFFERING_ID + " DESC LIMIT 1";
        String query = "SELECT MAX("+Constants.OFFERING_ID+") AS maxID FROM "+Constants.OFFERING_TABLE+";";
        st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        Offering o = new Offering();
        
        while(rs.next()){
            o.setOfferingId(rs.getString("maxID"));
        }
        
        con.close();
        st.close();
        
        return Integer.parseInt(o.getOfferingId());
    }
    
	public static String removeSpaces(String s){
		s = s.replaceAll("\\s",""); 
		return s;
	}
	
	public static ArrayList<Offering> combineOfferings(ArrayList<Offering> offeringList){
		
		for(int i = 0; i<offeringList.size(); i++){
			for(int j = 0; j <offeringList.size(); j++){
				if(offeringList.get(i).getOfferingId().equalsIgnoreCase(offeringList.get(j).getOfferingId()) && i != j){
					offeringList.remove(j);
				}
			}
		}
		
		return offeringList;
	}

    //added (j)

    // gets offering list by faculty id in ascending order, (faculty id = 1 = no prof)
    public static ArrayList<Offering> getListOfferingsByTermASC(String startYear, String endYear, String term) throws SQLException{
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " ot WHERE ot." + Constants.OFFERING_STARTYEAR + " = ? AND ot." + Constants.OFFERING_ENDYEAR + " = ? AND ot." + Constants.OFFERING_TERM + " = ?"
                        +" ORDER BY  ot." + Constants.OFFERING_FACULTYID + " ASC";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
        //System.out.println("PStatement CVC: " + st);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }
    
    public static ArrayList<Offering> getOfferingsPerTimeBlockOfFaculty(String startYear, String endYear, String term, String facultyId, String beginTime, String endTime) throws SQLException{
    	/*
    	 * SELECT * FROM offering o, course c, faculty f, users u, days d 
    	 * WHERE o.start_year = 2012 AND o.end_year = 2013 AND o.term = 2 AND f.faculty_id = o.faculty_id 
    	 * AND f.user_id = u.user_id  AND o.faculty_id = 46 AND o.course_id = c.course_id 
    	 * AND d.offering_id = o.offering_id AND d.begin_time = 1300 AND d.end_time = 1430 
    	 * ORDER BY c.course_code, o.section
    	 */
    	ArrayList<Offering> offerings = new ArrayList<Offering>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.OFFERING_TABLE + " o, " + Constants.COURSE_TABLE + " c, " + Constants.FACULTY_TABLE + " f, " + Constants.USERS_TABLE + " u, "+ Constants.DAYS_TABLE + " d " +
        				"WHERE o." + Constants.OFFERING_STARTYEAR + " = ? AND o." + Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ? AND o." + Constants.OFFERING_FACULTYID + " = ? AND d." +
        				Constants.DAYS_BEGINTIME + " = ? AND d."+ Constants.DAYS_ENDTIME + " = ? AND o." + Constants.FACULTY_ID + " = f." + Constants.FACULTY_ID + " AND f." + Constants.USERS_ID + " = u." +
        				Constants.USERS_ID + " AND o." + Constants.COURSE_ID + " = c." + Constants.COURSE_ID + " AND d." + Constants.OFFERING_ID + " = o." + Constants.OFFERING_ID +
        				" ORDER BY c." + Constants.COURSE_CODE + ", o." + Constants.OFFERING_SECTION;
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        st.setString(4, facultyId);
        st.setString(5, beginTime);
        st.setString(6, endTime);
        
        System.out.println("hey--->"+query);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Offering o = new Offering();
            o.setOfferingId(rs.getString(Constants.OFFERING_ID));
            o.setCourseId(rs.getString(Constants.OFFERING_COURSEID)); 
            o.setFacultyId(rs.getString(Constants.OFFERING_FACULTYID)); 
            o.setDegreeProgram(rs.getString(Constants.OFFERING_DEGREEPROGRAM)); 
            o.setSection(rs.getString(Constants.OFFERING_SECTION)); 
            o.setBatch(rs.getString(Constants.OFFERING_BATCH)); 
            o.setMaxStudentsEnrolled(rs.getString(Constants.OFFERING_MAXSTUDENTSENROLLED));
            o.setCurrStudentsEnrolled(rs.getString(Constants.OFFERING_CURRSTUDENTSENROLLED));
            o.setIsNonAcad(rs.getString(Constants.OFFERING_ISNONACAD));
            o.setIsShs(rs.getString(Constants.OFFERING_ISSHS));
            o.setIsServiceCourse(rs.getString(Constants.OFFERING_ISSERVICECOURSE));
            o.setServicedeptId(rs.getString(Constants.OFFERING_SERVICEDEPTID));
            o.setIsMasters(rs.getString(Constants.OFFERING_ISMASTERS));
            o.setIsElective(rs.getString(Constants.OFFERING_ISELECTIVE));
            o.setElectiveType(rs.getString(Constants.OFFERING_ELECTIVETYPE));
            o.setRemarks(rs.getString(Constants.OFFERING_REMARKS));
            o.setStatus(rs.getString(Constants.OFFERING_STATUS));
            o.setIteoEval(rs.getString(Constants.OFFERING_ITEOEVAL));
            o.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            o.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            o.setTerm(rs.getString(Constants.OFFERING_TERM));
            o.setIsPublished(rs.getString(Constants.OFFERING_ISPUBLISHED));
            o.setDays(DaysDAO.getListDays(o.getOfferingId())); 
            o.setCourse(CourseDAO.getCourseByID(o.getCourseId()));
            o.setFaculty(FacultyDAO.getFacultyByID(rs.getString(Constants.OFFERING_FACULTYID),startYear,endYear,term));
            
            String oTerm = rs.getString(Constants.OFFERING_TERM); 
            String oStartYear = rs.getString(Constants.OFFERING_STARTYEAR); 
            String oEndYear = rs.getString(Constants.OFFERING_ENDYEAR);
            
            o.setTimeframe(new Timeframe(oStartYear, oEndYear, oTerm));
            
            offerings.add(o);
        }
        
        con.close();
        st.close();
        
        return offerings;
    }

    public static float getNumberOfUnits(String offeringId) throws SQLException {
        Connection con = new Connector().getConnector();
        String query = "SELECT c." + Constants.COURSE_UNITS + " FROM " + Constants.OFFERING_TABLE + " o, " +  Constants.COURSE_TABLE + " c"
                        + " WHERE o." + Constants.OFFERING_ID + " = ? "  + "AND o." + Constants.OFFERING_COURSEID + " = c." + Constants.COURSE_ID;
        

        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, offeringId);
        ResultSet rs = st.executeQuery();
        
        float units = 0;
        if(rs.next())
           units = rs.getFloat(Constants.COURSE_UNITS);

           
           return units;
    }
    
    public static String getFacultyIdUsingOfferingId(String offeringId) throws SQLException {
        Connection con = new Connector().getConnector();
        String query = "SELECT " + Constants.FACULTY_ID + " FROM " + Constants.OFFERING_TABLE + " WHERE " + Constants.OFFERING_ID + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, offeringId);
        ResultSet rs = st.executeQuery();
        
        int facultyId = 0;
        if(rs.next())
            facultyId = rs.getInt(Constants.FACULTY_ID);
        
        return String.valueOf(facultyId);
            
        
    }
    public static void removeFacultyFromOffering(String offeringId) throws SQLException {
        // Removes faculty from the offering and also decrements the loads of the faculty
        Connection con = new Connector().getConnector();
        String query = "UPDATE " + Constants.OFFERING_TABLE + " SET " + Constants.OFFERING_FACULTYID + " = 1 " + " WHERE " + Constants.OFFERING_ID + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, offeringId);
        st.executeUpdate();
    }
}
