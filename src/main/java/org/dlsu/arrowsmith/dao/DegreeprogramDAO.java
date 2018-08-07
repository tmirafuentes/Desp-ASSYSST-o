package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DegreeprogramDAO {
	public static Degreeprogram getCourseByID(String ID) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.DEGREEPROGRAM_TABLE + " WHERE " + Constants.DEGREEPROGRAM_ID + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, ID);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        Degreeprogram d = new Degreeprogram();
        
        while(rs.next()){            
        	d.setDegreeprogramId(rs.getString(Constants.DEGREEPROGRAM_ID));
        	d.setCollegeId(rs.getString(Constants.DEGREEPROGRAM_COLLEGEID));
        	d.setDeptId(rs.getString(Constants.DEGREEPROGRAM_DEPTID));
        	d.setDegreeprogramCode(rs.getString(Constants.DEGREEPROGRAM_CODE));
        	d.setDegreeprogramName(rs.getString(Constants.DEGREEPROGRAM_NAME));
        }
        
        con.close();
        st.close();
        
        return d;
    }
	
	public static ArrayList<String> getDegreeList() throws SQLException{      
        Connection con = Connector.getConnector();
        String query = "SELECT DISTINCT " + Constants.DEGREEPROGRAM_ID + " FROM " + Constants.DEGREEPROGRAM_TABLE;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        System.out.println("PStatement: " + st);
        ResultSet rs = st.executeQuery();
        ArrayList<String> res = new ArrayList();
        
        while(rs.next()){            
        	res.add(rs.getString(Constants.DEGREEPROGRAM_ID));
        }
        
        return res;
	}
	
	public static ArrayList<String> getAllFlowchartBatch(String collegeId, String deptId) throws SQLException{   
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT(b."+ Constants.BATCHINFO_BATCH + ") FROM " +Constants.DEGREEPROGRAM_TABLE + " d, "+ Constants.BATCHINFO_TABLE + " b "+
    			"WHERE d."+ Constants.DEGREEPROGRAM_ID + " = b." + Constants.BATCHINFO_ID + " AND " +
    			"d."+ Constants.DEGREEPROGRAM_COLLEGEID + " = ? "+
    			"ORDER BY b." +Constants.BATCHINFO_BATCH + " ASC;";
    
	    PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
	    st.setInt(1, Integer.parseInt(collegeId));
	    
	    ResultSet rs = st.executeQuery();
	    
	    while(rs.next()){   
	    	String s = rs.getString(Constants.BATCHINFO_BATCH);
	    	
	    	sList.add(s);
	    }
        
        con.close();
        st.close();
        
        return sList;
    }
	
	public static ArrayList<String> getAllFlowchartDegreeProgram(String collegeId, String deptId) throws SQLException{   
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT(d."+ Constants.DEGREEPROGRAM_CODE + ") FROM " + Constants.DEGREEPROGRAM_TABLE + " d, "+ Constants.BATCHINFO_TABLE + " b "+
        			"WHERE d."+ Constants.DEGREEPROGRAM_ID + " = b." +Constants.BATCHINFO_ID + " AND " +
        			"d."+ Constants.DEGREEPROGRAM_COLLEGEID + " = ? "+
        			"ORDER BY d." +Constants.DEGREEPROGRAM_CODE + " ASC;";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(collegeId));
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString(Constants.DEGREEPROGRAM_CODE);
        	
        	sList.add(s);
        }
        
        con.close();
        st.close();
        
        return sList;
    }
	
	public static String getIDByCode(String code) throws SQLException {
        Connection con = Connector.getConnector();
        String query = "SELECT " + Constants.DEGREEPROGRAM_ID + " FROM " + Constants.DEGREEPROGRAM_TABLE + " WHERE " + Constants.DEGREEPROGRAM_CODE + " = ?";

        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, code);

        
        ResultSet rs = st.executeQuery();
        
        String res = "";
        
        while(rs.next()) {
        	res = rs.getString(Constants.DEGREEPROGRAM_ID);
            
//            System.out.println("res: " + res);
        }
        
        return res;
	}
}
