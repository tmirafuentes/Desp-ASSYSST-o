package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeloadingDAO {
	
	public static ArrayList<Deloading> getDeloadingList() throws SQLException {
		ArrayList<Deloading> deloadingList = new ArrayList<>();
        Connection con = Connector.getConnector();
        
        String query = "SELECT * FROM deloading ORDER BY deloading_code";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	Deloading deloading = new Deloading();
        	deloading.setDeloadingId(rs.getString("deloading_id"));
        	deloading.setCollegeId(rs.getString("college_id"));
        	deloading.setDeptId(rs.getString("dept_id"));
        	deloading.setDeloadingCode(rs.getString("deloading_code"));
        	deloading.setDeloadingName(rs.getString("deloading_name"));
        	deloading.setDeloadingType(rs.getString("deloading_type"));
        	deloading.setDeloadUnits(rs.getString("units"));
        	deloading.setDescription(rs.getString("description"));

        	
        	deloadingList.add(deloading);
        }
        
        con.close();
        st.close();
        
        return deloadingList;
	}
	
	public static Deloading getDeloadingById(String deloadingId) throws SQLException {
		Connection con = Connector.getConnector();
		String query = "SELECT * FROM deloading WHERE deloading_id = " + deloadingId;
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
	    ResultSet rs = st.executeQuery();
	    
	    Deloading deloading = new Deloading();
	    
	    while(rs.next()){   
        	
        	deloading.setDeloadingId(rs.getString("deloading_id"));
        	deloading.setCollegeId(rs.getString("college_id"));
        	deloading.setDeptId(rs.getString("dept_id"));
        	deloading.setDeloadingCode(rs.getString("deloading_code"));
        	deloading.setDeloadingName(rs.getString("deloading_name"));
        	deloading.setDeloadingType(rs.getString("deloading_type"));
        	deloading.setDeloadUnits(rs.getString("units"));
        	deloading.setDescription(rs.getString("description"));
	    }
	    
	    con.close();
        st.close();
        
        return deloading;
	    
	    
	}
	
	public static ArrayList<DeloadOffer> getFacultyDeloadingList(String facultyId, String startYear, String endYear, String term) throws SQLException {
		ArrayList<DeloadOffer> facultyDeloadingList = new ArrayList<>();
		Connection con = Connector.getConnector();
		String query = "SELECT * FROM deloadoffer WHERE faculty_id = ? AND term = ? AND start_year = ? AND end_year = ? ";
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);

		st.setString(1, facultyId);
		st.setString(2, term);
		st.setString(3, startYear);
		st.setString(4, endYear);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next()){   
			DeloadOffer deloadOffer = new DeloadOffer();
			deloadOffer.setDeloadofferId(rs.getString("deloadoffer_id"));
			deloadOffer.setDeloading(getDeloadingById(rs.getString("deloading_id")));
			deloadOffer.setFacultyId(rs.getString("faculty_id"));
			deloadOffer.setTerm(rs.getString("term"));
			deloadOffer.setStart_year(rs.getString("start_year"));
			deloadOffer.setEnd_year(rs.getString("end_year"));
			deloadOffer.setRemarks(rs.getString("remarks"));
        	
        	facultyDeloadingList.add(deloadOffer);
        }
        
        con.close();
        st.close();
        
        return facultyDeloadingList;
		
		
	}
	
	public static ArrayList<DeloadOffer> getFacultyDeloadingListAll(String facultyId) throws SQLException {
		ArrayList<DeloadOffer> facultyDeloadingList = new ArrayList<>();
		Connection con = Connector.getConnector();
		String query = "SELECT * FROM deloadoffer WHERE faculty_id = ? ORDER BY start_year DESC";
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);

		st.setString(1, facultyId);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next()){   
			DeloadOffer deloadOffer = new DeloadOffer();
			deloadOffer.setDeloadofferId(rs.getString("deloadoffer_id"));
			deloadOffer.setDeloading(getDeloadingById(rs.getString("deloading_id")));
			deloadOffer.setFacultyId(rs.getString("faculty_id"));
			deloadOffer.setTerm(rs.getString("term"));
			deloadOffer.setStart_year(rs.getString("start_year"));
			deloadOffer.setEnd_year(rs.getString("end_year"));
			deloadOffer.setRemarks(rs.getString("remarks"));
        	
        	facultyDeloadingList.add(deloadOffer);
        }
        
        con.close();
        st.close();
        
        return facultyDeloadingList;
		
		
	}
	
	public static DeloadOffer getDeloadOfferingById(String deloadofferId,String facultyId, String startYear, String endYear, String term) throws SQLException {
		Connection con = Connector.getConnector();
		String query = "SELECT * FROM deloadoffer WHERE faculty_id = ? AND term = ? AND start_year = ? AND end_year = ? AND deloadoffer_id = ? ";
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);

		st.setString(1, facultyId);
		st.setString(2, term);
		st.setString(3, startYear);
		st.setString(4, endYear);
		st.setString(5, deloadofferId);

	    ResultSet rs = st.executeQuery();
	    
	    DeloadOffer deloadOffer = new DeloadOffer();
	    while(rs.next()){   
			
			deloadOffer.setDeloadofferId(rs.getString("deloadoffer_id"));
			deloadOffer.setDeloading(getDeloadingById(rs.getString("deloading_id")));
			deloadOffer.setFacultyId(rs.getString("faculty_id"));
			deloadOffer.setTerm(rs.getString("term"));
			deloadOffer.setStart_year(rs.getString("start_year"));
			deloadOffer.setEnd_year(rs.getString("end_year"));
			deloadOffer.setRemarks(rs.getString("remarks"));
        	
        }
	    
	    con.close();
        st.close();
        
        return deloadOffer;
	    
	    
	}
	
	public static DeloadOffer getDeloadOfferingByIdOnly(String deloadofferId) throws SQLException {
		Connection con = Connector.getConnector();
		String query = "SELECT * FROM deloadoffer WHERE deloadoffer_id = ? ";
		PreparedStatement st = (PreparedStatement) con.prepareStatement(query);

		st.setString(1, deloadofferId);

	    ResultSet rs = st.executeQuery();
	    
	    DeloadOffer deloadOffer = new DeloadOffer();
	    while(rs.next()){   
			
			deloadOffer.setDeloadofferId(rs.getString("deloadoffer_id"));
			deloadOffer.setDeloading(getDeloadingById(rs.getString("deloading_id")));
			deloadOffer.setFacultyId(rs.getString("faculty_id"));
			deloadOffer.setTerm(rs.getString("term"));
			deloadOffer.setStart_year(rs.getString("start_year"));
			deloadOffer.setEnd_year(rs.getString("end_year"));
			deloadOffer.setRemarks(rs.getString("remarks"));
        	
        }
	    
	    con.close();
        st.close();
        
        return deloadOffer;
	}
	
	public static void adminDeloading(String facultyId, String startYear, String endYear, String term,String deloadingId) throws SQLException {
		 Connection con = Connector.getConnector();
		 String query = "INSERT INTO deloadoffer (deloading_id, faculty_id, term, start_year, end_year, remarks)"
		 		+ " VALUES(" + deloadingId + "," + facultyId + "," + term + "," + startYear + "," + endYear + "," + "'none'" + ")";
		 PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
		 System.out.println(st);
		 
		st.executeUpdate();
		System.out.println("Successfully executed admin deloading on facultyId: " + facultyId);
		
		con.close();
	    st.close();
	
	}
	
	public static void researchDeloading(String facultyId, String startYear, String endYear, String term,String deloadingId) throws SQLException {
		 Connection con = Connector.getConnector();
		 String query = "INSERT INTO deloadoffer (deloading_id, faculty_id, term, start_year, end_year, remarks)"
		 		+ " VALUES(" + deloadingId + "," + facultyId + "," + term + "," + startYear + "," + endYear + "," + "'none'" + ")";
		 PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
		 System.out.println(st);
		 
		st.executeUpdate();
		System.out.println("Successfully executed research deloading on facultyId: " + facultyId);
		
		con.close();
	    st.close();
	
	}

	public static void leaveDeloading(String facultyId, String startYear, String endYear, String term,String deloadingId) throws SQLException {
		 Connection con = Connector.getConnector();
		 String query = "INSERT INTO deloadoffer (deloading_id, faculty_id, term, start_year, end_year, remarks)"
		 		+ " VALUES(" + deloadingId + "," + facultyId + "," + term + "," + startYear + "," + endYear + "," + "'none'" + ")";
		 PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
		 System.out.println(st);
		 
		st.executeUpdate();
		System.out.println("Successfully executed leave deloading on facultyId: " + facultyId);
		
		con.close();
	    st.close();
	
	}
	
	public static void otherDeloading(String facultyId, String startYear, String endYear, String term,String deloadingId) throws SQLException {
		 Connection con = Connector.getConnector();
		 String query = "INSERT INTO deloadoffer (deloading_id, faculty_id, term, start_year, end_year, remarks)"
		 		+ " VALUES(" + deloadingId + "," + facultyId + "," + term + "," + startYear + "," + endYear + "," + "'none'" + ")";
		 PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
		 System.out.println(st);
		 
		st.executeUpdate();
		System.out.println("Successfully executed leave deloading on facultyId: " + facultyId);
		
		con.close();
	    st.close();
	
	}
	
	public static void addNewDeloading(String deloadingName, String deloadingCode, String deloadingType, String units, String description, String deptId) throws SQLException {
		 Connection con = Connector.getConnector();
		 String query = "INSERT INTO deloading (college_id, dept_id, deloading_code, deloading_name, deloading_type, units, description)"
		 		+ " VALUES( 2, " + deptId + ",'" + deloadingCode + "','" + deloadingName + "','" + deloadingType + "'," + units + ", '"  + description + "')";
		 PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
		 System.out.println(st);
		 
		st.executeUpdate();
		
		con.close();
	    st.close();
	
	}
}
