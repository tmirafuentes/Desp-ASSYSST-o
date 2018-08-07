package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquivalenceDAO {
	
	public static int checkIfTwoCoursesAreEquivalent(String course1ID, String course2ID) throws SQLException{
		Connection con = Connector.getConnector();

        String query = "SELECT COUNT(*) AS hasEquivalent FROM "+Constants.EQUIVALENCE_TABLE+" WHERE "+ Constants.EQUIVALENCE_ID + " = ? AND " + Constants.EQUIVALENCE_COURSEID + " = ?";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(course1ID));
        st.setInt(2, Integer.parseInt(course2ID));
        ResultSet rs = st.executeQuery();
        
        int isEquivalent = 0;
        
        while(rs.next()){
            isEquivalent = Integer.parseInt(rs.getString("hasEquivalent"));
        }
        
        con.close();
        st.close();
        
        return isEquivalent;
	}
	
	public static void insertEquivalenceIntoDB(String courseId, String equivalenceId) throws SQLException {
		Connection con = Connector.getConnector();
		String query = "INSERT INTO " + Constants.EQUIVALENCE_TABLE + 
						" (" + Constants.EQUIVALENCE_COURSEID + "," + Constants.EQUIVALENCE_ID +  ") " + 
						"VALUES(?,?)";
		
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, courseId);
        st.setString(2, equivalenceId);
		
        st.executeUpdate();
	}
}
