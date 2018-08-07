package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SpecialDAO {
	public static ArrayList<String> getCourseByID(String degree, String batch, String startYear, String endYear, String term) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT " + Constants.FLOWCOURSES_COURSEID + " FROM " + Constants.FLOWCHART_TABLE + " ft, " + Constants.DEGREEPROGRAM_TABLE + " dp, " + Constants.FLOWCOURSES_TABLE + " fs " + 
        		"WHERE ft." + Constants.FLOWCHART_ID + " = fs." + Constants.FLOWCOURSES_FLOWCHARTID + " " + 
        		"AND ft." + Constants.FLOWCHART_DEGREEPROGRAMID + " = dp." + Constants.DEGREEPROGRAM_ID + " " + 
        		"AND ft." + Constants.FLOWCHART_BATCH + " = ? " + 
        		"AND ft." + Constants.FLOWCHART_STARTYEAR + " = ? " + 
        		"AND ft." + Constants.FLOWCHART_ENDYEAR + " = ? " + 
        		"AND fs." + Constants.FLOWCOURSES_TERM + " = ? " +
        		"AND dp." + Constants.DEGREEPROGRAM_CODE + " = ? ";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, batch);
        st.setString(2, startYear);
        st.setString(3, endYear);
        st.setString(4, term);
        st.setString(5, degree);
        
        //System.out.println("PStatement: " + st);
        
        ResultSet rs = st.executeQuery();
        ArrayList<String> res = new ArrayList();
        
        while(rs.next()){            
        	res.add(rs.getString(Constants.FLOWCOURSES_COURSEID));
        }
        
        con.close();
        st.close();
        
        return res;
    }

}