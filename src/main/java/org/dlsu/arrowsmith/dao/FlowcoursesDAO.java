package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.extraModels.CourseTimeframe;
import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlowcoursesDAO {
	public static ArrayList<String> getFlowcourseById(String id, String term) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT " + Constants.FLOWCOURSES_COURSEID + " FROM " + Constants.FLOWCOURSES_TABLE + " WHERE " + Constants.FLOWCHART_ID + " = ? AND " + Constants.FLOWCOURSES_TERM + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, id);
        st.setString(2, term);

        System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();
        ArrayList<String> res = new ArrayList();
        
        while(rs.next()){            
        	res.add(rs.getString(Constants.FLOWCOURSES_COURSEID));
        }
        
        con.close();
        st.close();
        
        return res;
    }
	
	public static void insertFlowcourses(CourseTimeframe courseTF) throws SQLException {
		Connection con = Connector.getConnector();
		
		String query = "SELECT MAX(" + Constants.FLOWCHART_ID + ")  as " + Constants.FLOWCHART_ID + " FROM " + Constants.FLOWCHART_TABLE;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();
        String currYearLevel = "";
        int lastIndex = 0;
        
        while(rs.next()) {
        	lastIndex = rs.getInt(Constants.FLOWCHART_ID);
        }
        
//        System.out.println("flowcourses lastIndex: " + lastIndex);
        
		query = "INSERT INTO " + Constants.FLOWCOURSES_TABLE + 
				" (" + Constants.FLOWCOURSES_FLOWCHARTID + ", " + Constants.FLOWCOURSES_COURSEID + ", " + Constants.FLOWCOURSES_TERM + ") " +
				"VALUES (?,?,?)";
		
		for (Course course : courseTF.getCourseList()) {
			st = (PreparedStatement) con.prepareStatement(query);
	        st.setString(1, lastIndex + "");
	        st.setString(2, course.getCourseId());
	        st.setString(3, courseTF.getTimeframe().getTerm());
	        
//	        System.out.println("flowcourseQuery: " + st.toString());
	        
	        st.executeUpdate();
		}
        
        con.close();
        st.close();
		
		
	}
}
