package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacultyDAO {
	public static ArrayList<Faculty> getAllFacultyLoadWithFilters(String startYear, String endYear, String term, String collegeId, String deptId) throws SQLException{      
        Connection con = Connector.getConnector();
        /*
         * SELECT * FROM loads l, faculty f, users u WHERE l.start_year = 2016 AND l.end_year = 2017 
         * AND l.term = 1 AND l.faculty_id = f.faculty_id AND f.user_id = u.user_id AND u.college_id = 2 
         * AND u.dept_id = 12 ORDER BY u.last_name, u.first_name
         */
        String query = "SELECT * FROM " + Constants.LOADS_TABLE + " l, " + Constants.FACULTY_TABLE + " f, " + Constants.USERS_TABLE + " u WHERE l." 
        		+ Constants.LOADS_STARTYEAR + " = ? AND l." + Constants.LOADS_ENDYEAR + " = ? AND l." + Constants.LOADS_TERM + " = ? AND u." 
        		+ Constants.LOADS_COLLEGEID + " = ? AND u." + Constants.LOADS_DEPTID + " = ? AND f." + Constants.FACULTY_ID + " = l."
        		+ Constants.LOADS_FACULTYID + " AND f." + Constants.FACULTY_USERID + " = u." + Constants.USERS_ID + " ORDER BY u."
        		+ Constants.USERS_LASTNAME + ", u." + Constants.USERS_FIRSTNAME;
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        st.setString(4, collegeId);
        st.setString(5, deptId);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        System.out.println(query);
        ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
        
        while(rs.next()){
        	Faculty f = new Faculty();
        	
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));
        	f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE));
        	f.setFacultyId(rs.getString(Constants.FACULTY_ID));
        	f.setYearsOfService(rs.getString(Constants.FACULTY_YEARSTARTED));
        	f.setFacultyType(rs.getString(Constants.FACULTY_TYPE));
        	Load l = LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term);
        	f.setLoad(l);
            
            facultyList.add(f);
        }

        con.close();
        st.close();

        return facultyList;
    }
	
	public static Faculty getFacultyByID(String id, String startYear, String endYear, String term) throws SQLException{  
        Connection con = Connector.getConnector();
        String query = 
        		"SELECT * " +
        		"FROM " + Constants.USERS_TABLE + " u, " + Constants.FACULTY_TABLE + " f " +
        		"WHERE u." + Constants.USERS_ID + " = f." + Constants.FACULTY_USERID + " " +
        		"AND f." + Constants.FACULTY_ID + " = ? ";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, id);
        
//        System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        Faculty f = new Faculty();
        
        while(rs.next()){   
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));
        	f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE));
        	f.setFacultyId(rs.getString(Constants.FACULTY_ID));
        	f.setYearsOfService(rs.getString(Constants.FACULTY_YEARSTARTED));
        	f.setFacultyType(rs.getString(Constants.FACULTY_TYPE));

        	//f.setUser(UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID)));
        	Load l = LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term);
        	
        	
        	if(l.getLoadId() == null){
	        	//LoadDAO.insertNewLoadRow(rs.getString(Constants.FACULTY_ID), f.getUser().getCollegeID(), f.getUser().getDeptID(), startYear, endYear, term);
	        	//LoadDAO.getLoadByID(LoadDAO.getLastRecordIndex()+"", startYear, endYear, term);
	        }
			
        	f.setLoad(l);
        	//System.out.println(f.getLoad().getAdminLoad() + "--" +f.getLoad().getTimeframe().getStartYear());
        }
        
        con.close();
        st.close();
        
        return f;
    }
	
	public static ArrayList<Faculty> getAllFacultyWithRecommendations(Offering o, String facultyType, String deptId, String collegeId, String key) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
        Connection con = Connector.getConnector();
        
        /*
         * 
         SELECT DISTINCT(f.faculty_id) AS faculty_id, COUNT(f.faculty_id) AS faculty_count, f.user_id, 
         f.faculty_type, f.yyear_started  FROM faculty f, offering o, users u 
         where o.faculty_id = f.faculty_id AND f.user_id = u.user_id AND o.course_id = 1345 
         AND u.college_id LIKE '%%' AND u.dept_id LIKE '%%' GROUP BY f.faculty_id ORDER BY faculty_count DESC, 
         f.faculty_id ASC LIMIT 10
         * 
         */
        
        String query = "SELECT DISTINCT(f."+ Constants.FACULTY_ID + ") AS faculty_id, COUNT(f."+ Constants.FACULTY_ID +") AS faculty_count, "+
        			" f."+ Constants.FACULTY_USERID + ", f." + Constants.FACULTY_TYPE + ", f."+ Constants.FACULTY_YEARSTARTED + 
        			" FROM "+ Constants.FACULTY_TABLE +" f, "+ Constants.OFFERING_TABLE +" o, "+ Constants.USERS_TABLE +" u"+
        			" WHERE o."+ Constants.FACULTY_ID +" = f." + Constants.FACULTY_ID + " AND f." + Constants.FACULTY_USERID + " AND u." + Constants.USERS_ID + " AND" +
        			" o. " + Constants.COURSE_ID + " =? "+
        			" GROUP BY f." + Constants.FACULTY_ID + " ORDER BY faculty_count DESC, " + Constants.FACULTY_ID + " ASC;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(o.getCourseId()));  
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));
        	Faculty f;
        	
        	f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE), "Recommended", rs.getString("faculty_count"));
        	//System.out.println("department: " + f.getDepartment().getDeptCode());
        	
        	facultyList.add(f);
        }
        
        con.close();
        st.close();
        
        return facultyList;
    }
	
	public static ArrayList<Faculty> getAllFacultyWithSearchKey(Offering o, String facultyType, String deptId, String collegeId, String key) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
        Connection con = Connector.getConnector();
        String newDeptID, newCollegeID;
        
        if(deptId.equalsIgnoreCase("-999")) newDeptID = "";
        else newDeptID = deptId + "";
        
        if(collegeId.equalsIgnoreCase("-999")) newCollegeID = "";
        else newCollegeID = collegeId + "";
        /*
         * 
         SELECT * FROM faculty f, users u where f.user_id = u.user_id AND u.college_id LIKE '%%' 
         AND u.dept_id LIKE '%%' AND u.first_name LIKE '%%' AND u.last_name LIKE '%%' AND f.faculty_type LIKE '%%' GROUP BY f.faculty_id 
         ORDER BY u.last_name ASC, u.first_name ASC;
         * 
         */
        
        String query = "SELECT *"+
        			" FROM "+ Constants.FACULTY_TABLE +" f, "+ Constants.USERS_TABLE +" u"+
        			" WHERE u."+ Constants.USERS_ID +" = f." + Constants.FACULTY_USERID + " AND u."+ Constants.USERS_COLLEGEID + 
        			" LIKE '%"+newCollegeID+"%' AND u." + Constants.USERS_DEPTID + " LIKE '%"+ newDeptID + "%' AND (u." +Constants.USERS_FIRSTNAME + 
        			" LIKE '%" +key + "%' OR u."+ Constants.USERS_LASTNAME + " LIKE '%"+ key +"%') AND f." + Constants.FACULTY_TYPE + " LIKE '%" + facultyType + "%'"+
        			" GROUP BY f." + Constants.FACULTY_ID + " ORDER BY u." + Constants.USERS_LASTNAME+" ASC, u." + Constants.USERS_FIRSTNAME + " ASC;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));
        	Faculty f;
        	
        	f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE), "Searched", "N/A");
        	
        	facultyList.add(f);
        }
        
        con.close();
        st.close();
        
        return facultyList;
    }
	
	public static ArrayList<Faculty> getAllFacultyWithRecommendations(Offering o, String facultyType, String deptId, String collegeId, String key, String startYear, String endYear, String term) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
        Connection con = Connector.getConnector();
        
        /*
         * 
         SELECT DISTINCT(f.faculty_id) AS faculty_id, COUNT(f.faculty_id) AS faculty_count, f.user_id, 
         f.faculty_type, f.years_of_service  FROM faculty f, offering o, users u 
         where o.faculty_id = f.faculty_id AND f.user_id = u.user_id AND o.course_id = 1345 
         AND u.college_id LIKE '%%' AND u.dept_id LIKE '%%' GROUP BY f.faculty_id ORDER BY faculty_count DESC, 
         f.faculty_id ASC LIMIT 10
         * 
         */
        
        String query = "SELECT DISTINCT(f."+ Constants.FACULTY_ID + ") AS faculty_id, COUNT(f."+ Constants.FACULTY_ID +") AS faculty_count, "+
        			" f."+ Constants.FACULTY_USERID + ", f." + Constants.FACULTY_TYPE + ", f."+ Constants.FACULTY_YEARSTARTED + 
        			" FROM "+ Constants.FACULTY_TABLE +" f, "+ Constants.OFFERING_TABLE +" o, "+ Constants.USERS_TABLE +" u"+
        			" WHERE o."+ Constants.FACULTY_ID +" = f." + Constants.FACULTY_ID + " AND f." + Constants.FACULTY_USERID + " AND u." + Constants.USERS_ID + " AND" +
        			" o. " + Constants.COURSE_ID + " =? "+
        			" GROUP BY f." + Constants.FACULTY_ID + " ORDER BY faculty_count DESC, " + Constants.FACULTY_ID + " ASC;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(o.getCourseId()));  
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   

        	Faculty f = new Faculty();
	        	
        	f.setFacultyId(rs.getString(Constants.FACULTY_ID));
        	f.setYearsOfService(rs.getString(Constants.FACULTY_YEARSTARTED));
        	f.setFacultyType(rs.getString(Constants.FACULTY_TYPE));
        	f.setStatus("Recommended");
        	f.setTeachCount(rs.getString("faculty_count")); //custom column
        	f.setUser(UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID)));
        	//f.setLoad(LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term));
        	Load l = LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term);
			
			if(l.getLoadId() == null){
				//System.out.println("dds");
	        	//LoadDAO.insertNewLoadRow(rs.getString(Constants.FACULTY_ID), f.getUser().getCollegeID(), f.getUser().getDeptID(), startYear, endYear, term);
	        	//LoadDAO.getLoadByID(LoadDAO.getLastRecordIndex()+"", startYear, endYear, term);
	        }
			
        	f.setLoad(l);
        	//System.out.println(f.getUser().getUserId() + "aaa");
        	
        	if(f.getUser().getUserId() != null && !f.getUser().getUserId().equalsIgnoreCase(Constants.NO_PROFESSOR)){
        		facultyList.add(f);
        	}
        }
        
        con.close();
        st.close();
        
        return facultyList;
    }
	
	public static ArrayList<Faculty> getAllFacultyWithSearchKey(Offering o, String facultyType, String deptId, String collegeId, String key, String startYear, String endYear, String term) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
        Connection con = Connector.getConnector();
        String newDeptID, newCollegeID, newFacultyType;
        
        if(deptId.equalsIgnoreCase("-999")) newDeptID = "";
        else newDeptID = deptId + "";
        
        if(collegeId.equalsIgnoreCase("-999")) newCollegeID = "";
        else newCollegeID = collegeId + "";
        
        if(facultyType.equalsIgnoreCase("Full Time")) newFacultyType = "FT";
        else if(facultyType.equalsIgnoreCase("Part Time")) newFacultyType = "PT";
        else if(facultyType.equalsIgnoreCase("Half Time")) newFacultyType = "HT";
        else newFacultyType = "";
        /*
         * 
         SELECT * FROM faculty f, users u where f.user_id = u.user_id AND u.college_id LIKE '%%' 
         AND u.dept_id LIKE '%%' AND u.first_name LIKE '%%' AND u.last_name LIKE '%%' AND f.faculty_type LIKE '%%' GROUP BY f.faculty_id 
         ORDER BY u.last_name ASC, u.first_name ASC;
         * 
         */
        
        String query = "SELECT *"+
        			" FROM "+ Constants.FACULTY_TABLE +" f, "+ Constants.USERS_TABLE +" u"+
        			" WHERE u."+ Constants.USERS_ID +" = f." + Constants.FACULTY_USERID ; 
        			
        if(!collegeId.equalsIgnoreCase("-999")) query+=" AND u."+ Constants.USERS_COLLEGEID + " LIKE '%"+newCollegeID+"%'";
        if(!deptId.equalsIgnoreCase("-999")) query+=" AND u." + Constants.USERS_DEPTID + " LIKE '%"+ newDeptID + "%'";
        
        query+= " AND (u." +Constants.USERS_FIRSTNAME + " LIKE '%" +key + "%' OR u."+ Constants.USERS_LASTNAME + " LIKE '%"+ key +"%')";
        		
        if(!facultyType.equalsIgnoreCase("All")) query+=" AND f." + Constants.FACULTY_TYPE + " LIKE '%" + newFacultyType + "%'";

        query+= " GROUP BY f." + Constants.FACULTY_ID + " ORDER BY u." + Constants.USERS_LASTNAME+" ASC, u." + Constants.USERS_FIRSTNAME + " ASC;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println("-->"+query);
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	
        	Faculty f = new Faculty();
        	
        	f.setFacultyId(rs.getString(Constants.FACULTY_ID));
        	f.setYearsOfService(rs.getString(Constants.FACULTY_YEARSTARTED));
        	f.setFacultyType(rs.getString(Constants.FACULTY_TYPE));
        	f.setStatus("Searched");
        	f.setTeachCount("N/A");
        	f.setUser(UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID)));
        	//f.setLoad(LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term));
        	Load l = LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term);
			
        	if(l.getLoadId() == null){
	        	//LoadDAO.insertNewLoadRow(rs.getString(Constants.FACULTY_ID), f.getUser().getCollegeID(), f.getUser().getDeptID(),startYear, endYear, term);
	        	//LoadDAO.getLoadByID(LoadDAO.getLastRecordIndex()+"", startYear, endYear, term);
	        }
			
        	f.setLoad(l);
        	
        	//System.out.println(f.getUser().getUserId() + "ggg");
        	if(f.getUser().getUserId() != null && !f.getUser().getUserId().equalsIgnoreCase(Constants.NO_PROFESSOR)){
        		facultyList.add(f);
        	}
        }
        
        con.close();
        st.close();
        
        return facultyList;
    }
	
    public static ArrayList<Faculty> getAllFacultyWithSameCollegeAsOffering(Course course, College c, String startYear, String endYear, String term) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
        Connection con = Connector.getConnector();
        
        /*
         * 
         SELECT * FROM college c, faculty f, course co, users u
		WHERE co.college_id = c.college_id AND f.user_id = u.user_id 
			AND u.college_id = co.college_id AND co.course_code LIKE '%JAPALA1%'
         * 
         */
        
        String query = "SELECT *"+
        			" FROM "+ Constants.FACULTY_TABLE +" f, "+ Constants.USERS_TABLE +" u, "+ Constants.COURSE_TABLE +" c, "+ Constants.COLLEGE_TABLE +" co"+
        			" WHERE u."+ Constants.USERS_ID +" = f." + Constants.FACULTY_USERID + " AND u."+ Constants.USERS_COLLEGEID + " = co." + Constants.COLLEGE_ID +
        			" AND c." + Constants.COURSE_COLLEGEID + " = co." +Constants.COLLEGE_ID + 
        			" AND c."+ Constants.COURSE_CODE + " LIKE '%" + course.getCourseCode() + "%'" +
        			" ORDER BY u." + Constants.USERS_LASTNAME+" ASC, u." + Constants.USERS_FIRSTNAME + " ASC;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	
        	Faculty f = new Faculty();
        	
        	f.setFacultyId(rs.getString(Constants.FACULTY_ID));
        	f.setYearsOfService(rs.getString(Constants.FACULTY_YEARSTARTED));
        	f.setFacultyType(rs.getString(Constants.FACULTY_TYPE));
        	f.setStatus("Searched");
        	f.setTeachCount("N/A");
        	f.setUser(UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID)));
        	//f.setLoad(LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term));
        	Load l = LoadDAO.getLoadByID(rs.getString(Constants.FACULTY_ID),startYear,endYear,term);
			
        	if(l.getLoadId() == null){
	        	//LoadDAO.insertNewLoadRow(rs.getString(Constants.FACULTY_ID), f.getUser().getCollegeID(), f.getUser().getDeptID(),startYear, endYear, term);
	        	//LoadDAO.getLoadByID(LoadDAO.getLastRecordIndex()+"", startYear, endYear, term);
	        }
			
        	f.setLoad(l);
        	
        	if(f.getUser().getUserId() != null && !f.getUser().getUserId().equalsIgnoreCase(Constants.NO_PROFESSOR)){
        		facultyList.add(f);
        	}
        }
        
        con.close();
        st.close();
        
        return facultyList;
    }
    
	public static ArrayList<String> getAllUniqueFacultyType() throws SQLException{   
		ArrayList<String> typeList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        SELECT b.building_id, b.building_name, COUNT(r.building_id) AS roomsAvailable
		FROM building b
		LEFT JOIN room r ON b.building_id = r.building_id
		GROUP BY building_id;
         * 
        /* 
        String query = "SELECT * FROM " + Constants.DEPARTMENT_TABLE + " ORDER BY " + Constants.DEPT_CODE + " ASC";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	String d;
        	d = rs.getString(Constants.DEPT_NAME);
        	
        	typeList.add(d);
        }*/
        
        typeList.add("Full Time");
        typeList.add("Part Time");
        typeList.add("Half Time");
        typeList.add("Leave");
        //con.close();
        //st.close();
        
        return typeList;
    }
	
	public static ArrayList<Faculty> getListFacultyByTerm(String startYear, String endYear, String term) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList();
        Connection con = Connector.getConnector();
        String query = 
        		"SELECT * " +
        		"FROM " + Constants.USERS_TABLE + " u, " + Constants.FACULTY_TABLE + " f, " + Constants.LOADS_TABLE + " l " +
        		"WHERE u." + Constants.USERS_ID + " = f." + Constants.FACULTY_USERID + " " +
        		"AND f." + Constants.FACULTY_ID + " = l." + Constants.LOADS_FACULTYID + " " + 
        		"AND l." + Constants.LOADS_STARTYEAR + " = ? " + 
        		"AND l." + Constants.LOADS_ENDYEAR + " = ? " + 
        		"AND l." + Constants.LOADS_TERM + " = ?";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
         //System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));   	

        	Faculty f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE));
        	f.setLoad(LoadDAO.getLoadByID(f.getFacultyId(),startYear,endYear,term));
            
            facultyList.add(f);
        }
        
        
        con.close();
        st.close();
        
        return facultyList;
    }
	
	public static void assignFacultyToOffering(String offeringId, String facultyId) throws SQLException {        
        
    	Connection con = Connector.getConnector();
        String query = "UPDATE " + Constants.OFFERING_TABLE + " SET "+ Constants.OFFERING_FACULTYID +" = ? WHERE " + Constants.OFFERING_ID + " = ?;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(facultyId));
        st.setInt(2, Integer.parseInt(offeringId));
        
        st.executeUpdate(); //use execute update instead of execute query
           
        con.close();
        st.close();
       
	}
	
	public static String removeSpaces(String s){
		s = s.replaceAll("\\s",""); 
		return s;
	}

    // new stuff (j)
    public static boolean checkIfHasLoadId(String facultyId, String startYear, String endYear, String term) throws SQLException {
        Connection con = Connector.getConnector();
        String query = "SELECT " + Constants.LOADS_ID + " FROM " + Constants.LOADS_TABLE + " WHERE " + Constants.FACULTY_ID + " = ? " + " AND " + Constants.LOADS_STARTYEAR + " = ? "
                + " AND " + Constants.LOADS_ENDYEAR + " = ?" + " AND " + Constants.LOADS_TERM + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, facultyId);
        st.setString(2, startYear);
        st.setString(3, endYear);
        st.setString(4, term);
        
        ResultSet rs = st.executeQuery();
        
        // if results set is empty (i.e. no data), return false, else return true
        if(!rs.isBeforeFirst()) {
            return false;
        } else
            return true;

    }
    
    public static boolean checkIfSameFaculty(String facultyId, String offeringId) throws SQLException {
        Connection con = Connector.getConnector();
        
        String query = "SELECT " + Constants.OFFERING_FACULTYID + " FROM " + Constants.OFFERING_TABLE + " WHERE " + Constants.OFFERING_ID + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, offeringId);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();
        int facultyIdTemp = 0;
        if(rs.next())
            facultyIdTemp = rs.getInt(Constants.OFFERING_FACULTYID);
        
        if(facultyIdTemp == Integer.parseInt(facultyId))
            return true;
        else
            return false;
    }
    
    public static void incrementLoad(String loadsId, float units) throws SQLException {
        Connection con = Connector.getConnector();
        
        // increment teaching load
        String query = "UPDATE " + Constants.LOADS_TABLE + " SET " + Constants.LOADS_TEACHINGLOAD + " = " + Constants.LOADS_TEACHINGLOAD + " + " + units 
                + " WHERE " + Constants.LOADS_ID + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(loadsId));
        st.executeUpdate();
        
        
        // increment total load
        String query2 = "UPDATE " + Constants.LOADS_TABLE + " SET " + Constants.LOADS_TOTALLOAD+ " = " + Constants.LOADS_TOTALLOAD + " + " + units 
                + " WHERE " + Constants.LOADS_ID + " = ? ";
        PreparedStatement st2 = (PreparedStatement) con.prepareStatement(query2);
        st2.setInt(1, Integer.parseInt(loadsId));
        st2.executeUpdate();
        
        
    }
    
    public static void decrementLoad(String loadsId, float units) throws SQLException {
        Connection con = Connector.getConnector();
        
        // decrement teaching load
        String query = "UPDATE " + Constants.LOADS_TABLE + " SET " + Constants.LOADS_TEACHINGLOAD + " = "
                + Constants.LOADS_TEACHINGLOAD + " - " + units + " WHERE " + Constants.LOADS_ID + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, loadsId);
        st.executeUpdate();

        // decrement total load
        String query2 = "UPDATE " + Constants.LOADS_TABLE + " SET " + Constants.LOADS_TOTALLOAD + " = "
                + Constants.LOADS_TOTALLOAD + " - " + units + " WHERE " + Constants.LOADS_ID + " = ? ";
        PreparedStatement st2 = (PreparedStatement) con.prepareStatement(query2);
        st2.setString(1, loadsId);
        st2.executeUpdate();
        
        //System.out.println("units:" + units);
        //System.out.println("loads id in decrement: " + loadsId);
        
    }

    
    
    // Note: The "load" referred here is actually the offering/s that matches the parameters given
    public static ArrayList<Offering> getCurrentFacultyLoad(int facultyId, int term, int start_year, int end_year) throws SQLException {
        ArrayList<Offering> loadList = new ArrayList();     
        Connection con = Connector.getConnector();
        String query = 
                "SELECT " + "c." + Constants.COURSE_CODE + ", " + "o." + Constants.OFFERING_TERM + ", " + "o." + Constants.OFFERING_STARTYEAR + ", " + "o." + Constants.OFFERING_ENDYEAR + " , " + "o." + Constants.OFFERING_ID + " " +
                "FROM " + Constants.OFFERING_TABLE + " o, " + Constants.COURSE_TABLE + " c, " + Constants.FACULTY_TABLE + " f " +
                "WHERE " + "o." + Constants.COURSE_ID + "= c." + Constants.COURSE_ID + " AND o." + Constants.FACULTY_ID + " = f." + Constants.FACULTY_ID + " " +
                "AND f." + Constants.FACULTY_ID + " = ? " + "AND o." + Constants.OFFERING_TERM + " = ? " + "AND o." + Constants.OFFERING_STARTYEAR + " = ? " + " AND o." + Constants.OFFERING_ENDYEAR + " = ? ";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, facultyId);
        st.setInt(2, term);
        st.setInt(3, start_year);
        st.setInt(4, end_year);
        
        //System.out.println(query); // for checking
        
        ResultSet rs = st.executeQuery();
        
        int index = 0;
        while(rs.next()) {
            loadList.add(new Offering());
            loadList.get(index).setCourse_code(rs.getString(Constants.COURSE_CODE));
            loadList.get(index).setTerm(rs.getString(Constants.OFFERING_TERM));
            loadList.get(index).setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
            loadList.get(index).setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
            loadList.get(index).setOfferingId(rs.getString(Constants.OFFERING_ID));
            
            index++;
        }
        
        con.close();
        st.close();
        
        return loadList;    
    }
    
    public static Load getCurrentFacultyLoadSpecific(int facultyId, int startYear, int endYear, int term) throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.LOADS_TABLE + " WHERE " + Constants.OFFERING_FACULTYID + " = ? AND " + Constants.OFFERING_STARTYEAR + " = ? AND " +
                        Constants.OFFERING_ENDYEAR + " = ? AND " + Constants.OFFERING_TERM + " = ? ";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, facultyId);
        st.setInt(2, startYear);
        st.setInt(3, endYear);
        st.setInt(4, term);
        
        ResultSet rs = st.executeQuery();
        
        // create Load object
        Load facultyLoad = new Load();
        while(rs.next()) {
            facultyLoad.setLoadId(rs.getString(Constants.LOADS_ID));
            facultyLoad.setFacultyId(rs.getString(Constants.LOADS_FACULTYID));
            facultyLoad.setCollegeId(rs.getString(Constants.LOADS_COLLEGEID));
            facultyLoad.setDeptId(rs.getString(Constants.LOADS_DEPTID));
            facultyLoad.setAdminLoad(rs.getString(Constants.LOADS_ADMINLOAD));
            facultyLoad.setResearchLoad(rs.getString(Constants.LOADS_RESEARCHLOAD));
            facultyLoad.setTeachingLoad(rs.getString(Constants.LOADS_TEACHINGLOAD));
            facultyLoad.setNonAcadLoad(rs.getString(Constants.LOADS_NONACADLOAD));
            facultyLoad.setDeloading(rs.getString(Constants.LOADS_DELOADING));
            facultyLoad.setTotalLoad(rs.getString(Constants.LOADS_TOTALLOAD));
            facultyLoad.setPreparations(rs.getString(Constants.LOADS_PREPARATIONS));
            facultyLoad.setIsOnLeave(rs.getString(Constants.LOADS_ISONLEAVE));
            facultyLoad.setLeaveType(rs.getString(Constants.LOADS_LEAVETYPE));
            facultyLoad.setTimestamp(rs.getString(Constants.LOADS_TIMESTAMP));
            
            String loadstartYear = rs.getString(Constants.LOADS_STARTYEAR);
            String loadendYear = rs.getString(Constants.LOADS_ENDYEAR);
            String loadterm = rs.getString(Constants.LOADS_TERM);
            facultyLoad.setTimeframe(new Timeframe(loadstartYear, loadendYear, loadterm)); //what is this for bro haha
        }
        
        return facultyLoad;
                         
        
        
    }

    public static void insertFacultyToDB(Faculty faculty) throws SQLException {
    	Connection con = Connector.getConnector();
    	String query = "INSERT INTO " + Constants.FACULTY_TABLE + " (" + 
    			Constants.FACULTY_USERID + "," + Constants.FACULTY_YEARSTARTED + "," + Constants.FACULTY_TYPE + 
    			") VALUES (?,?,?)";
    	
    	PreparedStatement ps = con.prepareStatement(query);
    	ps.setString(1, faculty.getUserId());
    	ps.setString(2, faculty.getYearsOfService());
    	ps.setString(3, faculty.getFacultyType());
    	
    	ps.executeUpdate();
    	
    	UserDAO.insertUser(faculty);
    }

    // deloading
    
    public static ArrayList<Faculty> getAllFacultyFromSameDepartment(String departmentId) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList();
        Connection con = Connector.getConnector();
        String query = 
        		"SELECT * " +
        		"FROM " + Constants.USERS_TABLE + " u, " + Constants.FACULTY_TABLE + " f " +
        		"WHERE u." + Constants.USERS_ID + " = f." + Constants.FACULTY_USERID + " " +
        		"AND u." + Constants.USERS_DEPTID + " = ? ";
 
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(departmentId));  

        
         //System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));   	

        	Faculty f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE));
        	//f.setLoad(LoadDAO.getLoadByID(f.getFacultyId(),startYear,endYear,term));
            
            facultyList.add(f);
        }
        
        
        con.close();
        st.close();
        
        return facultyList;
    }
    
    public static ArrayList<Faculty> getAllFacultyFromSameDepartmentWithKey(String departmentId, String key) throws SQLException{   
		ArrayList<Faculty> facultyList = new ArrayList();
        Connection con = Connector.getConnector();
        String query = 
        		"SELECT * " +
        		"FROM " + Constants.USERS_TABLE + " u, " + Constants.FACULTY_TABLE + " f " +
        		"WHERE u." + Constants.USERS_ID + " = f." + Constants.FACULTY_USERID + " " +
        		"AND u." + Constants.USERS_DEPTID + " = ? " + " AND (u." + Constants.USERS_FIRSTNAME + " LIKE '%" + key + "%' OR u." + Constants.USERS_LASTNAME + " LIKE '%" + key + "%')";
 
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(departmentId));  

        
         //System.out.println("Key: (" + key + ")");
       // System.out.println(st);
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	User u = UserDAO.getUserByID(rs.getString(Constants.FACULTY_USERID));   	

        	Faculty f = new Faculty(u, rs.getString(Constants.FACULTY_ID), rs.getString(Constants.FACULTY_YEARSTARTED), rs.getString(Constants.FACULTY_TYPE));
        	//f.setLoad(LoadDAO.getLoadByID(f.getFacultyId(),startYear,endYear,term));
            
            facultyList.add(f);
        }
        
        
        con.close();
        st.close();
        
        return facultyList;
    }
    

    // deloading

}
