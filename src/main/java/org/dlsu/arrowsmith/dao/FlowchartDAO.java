package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.extraModels.CourseTimeframe;
import org.dlsu.arrowsmith.extraModels.Timeframe;
import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlowchartDAO {
	public static Flowchart getFlowchartByTerm(String startYear, String endYear, String term) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.FLOWCHART_TABLE + "fl, " + Constants.FLOWCOURSES_TABLE + " fc WHERE fl." + 
        		Constants.FLOWCHART_ID + " = fc." + Constants.FLOWCOURSES_FLOWCHARTID + 
        		Constants.FLOWCHART_STARTYEAR + " = ? AND " + Constants.FLOWCHART_ENDYEAR + " = ? AND " + Constants.FLOWCOURSES_TERM + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        
        ResultSet rs = st.executeQuery();
        Flowchart f = new Flowchart();
        Timeframe t = new Timeframe();
        CourseTimeframe c = new CourseTimeframe();
        
        while(rs.next()){            
        	f.setFlowchartId(rs.getString(Constants.FLOWCHART_ID));
        	f.setDegreeprogramId(Constants.FLOWCHART_DEGREEPROGRAMID);
        	f.setBatch(rs.getString(Constants.FLOWCHART_BATCH));
        	c.setYearLevel(rs.getString(Constants.FLOWCHART_YEARLEVEL));
        	t.setStartYear(rs.getString(Constants.FLOWCHART_STARTYEAR));
        	t.setEndYear(rs.getString(Constants.FLOWCHART_ENDYEAR));
        }
        
        ArrayList<String> courseIdList = FlowcoursesDAO.getFlowcourseById(f.getFlowchartId(), term);
        
        for (String string : courseIdList) {
			c.addCourse(CourseDAO.getCourseByID(string));
		}
        
        t.setTerm(term);
        
        c.setTimeframe(t);
        
        f.addCourseTimeframe(c);
        
        con.close();
        st.close();
        
        return f;
    }
	
	public static ArrayList<String> getBatchList() throws SQLException{      
        Connection con = Connector.getConnector();
        String query = "SELECT DISTINCT " + Constants.FLOWCHART_BATCH + " FROM " + Constants.FLOWCHART_TABLE;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();
        ArrayList<String> res = new ArrayList();
        
        while(rs.next()){            
        	res.add(rs.getString(Constants.FLOWCHART_BATCH));
        }
        
        return res;
	}
	
	public static ArrayList<String> getYearList() throws SQLException{      
        Connection con = Connector.getConnector();
        String query = "SELECT DISTINCT " + Constants.FLOWCHART_STARTYEAR + ", " + Constants.FLOWCHART_ENDYEAR + " FROM " + Constants.FLOWCHART_TABLE;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();
        ArrayList<String> res = new ArrayList();
        
        while(rs.next()){    
        	String temp = rs.getString(Constants.FLOWCHART_STARTYEAR) + "-" + rs.getString(Constants.FLOWCHART_ENDYEAR);
        	res.add(temp);
        }
        
        return res;
	}
	
	public static ArrayList<String> getAllFlowchartAcademicYear(String collegeId, String deptId) throws SQLException{   
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT(CONCAT(f."+ Constants.FLOWCHART_STARTYEAR + ", \"-\", f." + Constants.FLOWCHART_ENDYEAR+")) AS academic_year, f." + Constants.FLOWCHART_STARTYEAR + ", f."+ Constants.FLOWCHART_ENDYEAR + " "+
        		    "FROM " +Constants.DEGREEPROGRAM_TABLE + " d, "+ Constants.BATCHINFO_TABLE + " b, "+ Constants.FLOWCHART_TABLE + " f "+
        			"WHERE d."+ Constants.DEGREEPROGRAM_ID + " = b." +Constants.BATCHINFO_ID + " AND " +
        			"d."+ Constants.DEGREEPROGRAM_ID + " = f." +Constants.FLOWCHART_DEGREEPROGRAMID + " AND d."+ Constants.DEGREEPROGRAM_COLLEGEID + " = ? "+
        			"ORDER BY f." +Constants.FLOWCHART_STARTYEAR + " DESC, f." + Constants.FLOWCHART_ENDYEAR + " DESC;";

        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(collegeId));
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString("academic_year");
        	
        	sList.add(s);
        }
        
        con.close();
        st.close();
        
        return sList;
    }
	
	public static void insertFlowchart(Flowchart flowchart) throws SQLException {
		Connection con = Connector.getConnector();
		String query = "SELECT MAX(" + Constants.FLOWCHART_ID + ")  as " + Constants.FLOWCHART_ID + " FROM " + Constants.FLOWCHART_TABLE;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();
        String currYearLevel = "";
        int lastIndex = 0;
        
        while(rs.next()) {
        	lastIndex = rs.getInt(Constants.FLOWCHART_ID);
        }
        
        lastIndex++;
        
        query = "INSERT INTO " + Constants.FLOWCHART_TABLE + 
        		" (" + Constants.FLOWCHART_ID + ", " + Constants.FLOWCHART_DEGREEPROGRAMID + ", " + 
        		Constants.FLOWCHART_BATCH + ", " + Constants.FLOWCHART_YEARLEVEL + ", " + Constants.FLOWCHART_STARTYEAR + ", " + 
        		Constants.FLOWCHART_ENDYEAR + ") " + 
        		"VALUES(?,?,?,?,?,?)";
        
        System.out.println("courseTimeframes: " + flowchart.getCourseTimeframes().size());
        
        for (CourseTimeframe courseTF : flowchart.getCourseTimeframes()) {
        	if(!currYearLevel.equals(courseTF.getYearLevel())) {
        		currYearLevel = courseTF.getYearLevel();
        		
        		st = (PreparedStatement) con.prepareStatement(query);
                st.setString(1, lastIndex + "");
                st.setString(2, flowchart.getDegreeprogramId());
                st.setString(3, flowchart.getBatch());
                
                st.setString(4, courseTF.getYearLevel());
                st.setString(5, courseTF.getTimeframe().getStartYear());
                st.setString(6, courseTF.getTimeframe().getEndYear());
                
                System.out.println("Year: " + courseTF.getYearLevel() + "|" + courseTF.getTimeframe().getStartYear() + "-" + courseTF.getTimeframe().getEndYear());
                
                st.executeUpdate();
                
                lastIndex++;
        	}
            
            FlowcoursesDAO.insertFlowcourses(courseTF);
		}
        
        con.close();
        st.close();
	}
}
