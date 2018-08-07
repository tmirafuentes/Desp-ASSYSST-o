package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class CollegeDAO {
    
	public static int getCollegeIDByCode(String code) throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.COLLEGE_TABLE + " WHERE " + Constants.COLLEGE_CODE + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, removeSpaces(code));
        
        System.out.println("College PS: " + st.toString());
        
        ResultSet rs = st.executeQuery();
        College c = new College();
        
        while(rs.next()){
            c.setId(rs.getString(Constants.COLLEGE_ID));
        	c.setCollegeCode(rs.getString(Constants.COLLEGE_CODE));
        	c.setCollegeName(rs.getString(Constants.COLLEGE_NAME));
        	c.setIsObsolete(rs.getString(Constants.COLLEGE_ISOBSOLETE));
        }
        
        con.close();
        st.close();
        return Integer.parseInt(c.getId());
    }
    
	public static College getCollegeByName(String name) throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.COLLEGE_TABLE + " WHERE " + Constants.COLLEGE_NAME + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, name);
        
        ResultSet rs = st.executeQuery();
        College c = new College();
        
        while(rs.next()){
            c.setId(rs.getString(Constants.COLLEGE_ID));
            c.setCollegeCode(rs.getString(Constants.COLLEGE_CODE));
            c.setCollegeName(rs.getString(Constants.COLLEGE_NAME));
            c.setIsObsolete(rs.getString(Constants.COLLEGE_ISOBSOLETE));
        }
        
        con.close();
        st.close();
        return c;
    }
	
	public static College getCollegeByID(String ID) throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.COLLEGE_TABLE + " WHERE " + Constants.COLLEGE_ID + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, removeSpaces(ID));
        
        ResultSet rs = st.executeQuery();
        College c = new College();
        
        while(rs.next()){
            c.setId(rs.getString(Constants.COLLEGE_ID));
            c.setCollegeCode(rs.getString(Constants.COLLEGE_CODE));
            c.setCollegeName(rs.getString(Constants.COLLEGE_NAME));
            c.setIsObsolete(rs.getString(Constants.COLLEGE_ISOBSOLETE));
        }
        
        con.close();
        st.close();
        return c;
    }
	
	public static ArrayList<College> getAllColleges() throws SQLException{   
		ArrayList<College> collegeList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        SELECT b.building_id, b.building_name, COUNT(r.building_id) AS roomsAvailable
		FROM building b
		LEFT JOIN room r ON b.building_id = r.building_id
		GROUP BY building_id;
         * 
         */
        String query = "SELECT * FROM " + Constants.COLLEGE_TABLE + " ORDER BY " + Constants.COLLEGE_CODE + " ASC";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	College c = new College();
        	c.setId(rs.getString(Constants.COLLEGE_ID));
        	c.setCollegeCode(rs.getString(Constants.COLLEGE_CODE));
        	c.setCollegeName(rs.getString(Constants.COLLEGE_NAME));
        	c.setIsObsolete(rs.getString(Constants.COLLEGE_ISOBSOLETE));
        	
        	collegeList.add(c);
        }
        
        con.close();
        st.close();
        
        return collegeList;
    }
	
	public static ArrayList<String> getAllUniqueColleges() throws SQLException{   
		ArrayList<String> collegeList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        SELECT b.building_id, b.building_name, COUNT(r.building_id) AS roomsAvailable
		FROM building b
		LEFT JOIN room r ON b.building_id = r.building_id
		GROUP BY building_id;
         * 
         */
        String query = "SELECT * FROM " + Constants.COLLEGE_TABLE + " ORDER BY " + Constants.COLLEGE_CODE + " ASC";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	String c;
        	c = rs.getString(Constants.COLLEGE_NAME);
        	
        	collegeList.add(c);
        }
        
        con.close();
        st.close();
        
        return collegeList;
    }
	
    public static String removeSpaces(String s){
        return s.replaceAll("\\s","");
    }
}
