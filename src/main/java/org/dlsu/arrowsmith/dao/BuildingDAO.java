package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuildingDAO {
	public static ArrayList<Building> getAllBuildingsWithRoomCount() throws SQLException{   
		ArrayList<Building> buildingList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        SELECT b.building_id, b.building_name, COUNT(r.building_id) AS roomsAvailable
		FROM building b
		LEFT JOIN room r ON b.building_id = r.building_id
		GROUP BY building_id;
         * 
         */
        String query = "SELECT b." + Constants.BUILDING_ID + ", b."+ Constants.BUILDING_CODE +", b." + Constants.BUILDING_NAME +", COUNT(r."+ Constants.ROOM_BUILDINGID +") AS roomsAvailable "+
        			"FROM "+ Constants.BUILDING_TABLE +" b "+
        			"LEFT JOIN "+ Constants.ROOM_TABLE +" r ON b." + Constants.BUILDING_ID + " = r."+ Constants.ROOM_BUILDINGID +" "+
        			"GROUP BY b." + Constants.BUILDING_ID + " ORDER BY "+ Constants.BUILDING_NAME +" asc;";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	Building b = new Building();
        	b.setId(rs.getString(Constants.BUILDING_ID));
        	b.setBuildingCode(rs.getString(Constants.BUILDING_CODE));
        	b.setBuildingName(rs.getString(Constants.BUILDING_NAME));
        	b.setRoomsAvailable(rs.getString("roomsAvailable"));
        	
        	buildingList.add(b);
        }
        
        con.close();
        st.close();
        
        return buildingList;
    }
	
	public static Building getBuildingByID(String ID) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.BUILDING_TABLE + " WHERE " + Constants.BUILDING_ID + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, ID);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        Building b = new Building();
        
        while(rs.next()){            
        	b.setId(rs.getString(Constants.BUILDING_ID));
        	b.setBuildingCode(rs.getString(Constants.BUILDING_CODE));
        	b.setBuildingName(rs.getString(Constants.BUILDING_NAME));
        }
        
        con.close();
        st.close();
        
        return b;
    }
	
	public static Building getBuildingByName(String name) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.BUILDING_TABLE + " WHERE " + Constants.BUILDING_NAME + " LIKE '" + name +"'";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        Building b = new Building();
        
        while(rs.next()){            
        	b.setId(rs.getString(Constants.BUILDING_ID));
        	b.setBuildingCode(rs.getString(Constants.BUILDING_CODE));
        	b.setBuildingName(rs.getString(Constants.BUILDING_NAME));
        }
        
        con.close();
        st.close();
        
        return b;
    }
	
	public static ArrayList<Building> getAllBuildings() throws SQLException{   
		ArrayList<Building> buildingList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        select * from building order by building_name asc
         */ 
        String query = "SELECT * FROM "+ Constants.BUILDING_TABLE +" ORDER BY "+ Constants.BUILDING_NAME +" asc;";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	Building b = new Building();
        	b.setId(rs.getString(Constants.BUILDING_ID));
        	b.setBuildingCode(rs.getString(Constants.BUILDING_CODE));
        	b.setBuildingName(rs.getString(Constants.BUILDING_NAME));
        	
        	buildingList.add(b);
        }
        
        con.close();
        st.close();
        
        return buildingList;
    }
	
	public static ArrayList<String> getAllBuildingNames() throws SQLException{   
		ArrayList<String> buildingList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        select * from building order by building_name asc
         */ 
        String query = "SELECT * FROM "+ Constants.BUILDING_TABLE +" ORDER BY "+ Constants.BUILDING_NAME +" asc;";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString(Constants.BUILDING_NAME);
        	
        	buildingList.add(s);
        }
        
        con.close();
        st.close();
        
        return buildingList;
    }
}
