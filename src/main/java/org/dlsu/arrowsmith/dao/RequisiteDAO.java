package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequisiteDAO {
	public static void insertRequisiteIntoDB(String courseId, String reqCourseId, String reqType) throws SQLException {
		Connection con = Connector.getConnector();
		String query = "INSERT INTO " + Constants.REQUISITE_TABLE + 
						" (" + Constants.REQUISITE_COURSEID + "," + Constants.REQUISITE_REQCOURSEID + "," +
						Constants.REQUISITE_REQTYPE + ") " + 
						"VALUES(?,?,?)";
		
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, courseId);
        st.setString(2, reqCourseId);
        st.setString(3, reqType);
		
        st.executeUpdate();
	}
}
