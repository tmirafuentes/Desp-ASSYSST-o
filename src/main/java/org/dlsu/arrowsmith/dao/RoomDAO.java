package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDAO {
	public static Room getRoomByID(String ID) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.ROOM_TABLE + " WHERE " + Constants.ROOM_ID + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, ID);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        Room r = new Room();
        
        while(rs.next()){            
        	r.setId(rs.getString(Constants.ROOM_ID));
        	r.setRoomCode(rs.getString(Constants.ROOM_CODE));
        	r.setRoomLocation(rs.getString(Constants.ROOM_LOCATION));
        	r.setRoomCapacity(rs.getString(Constants.ROOM_CAPACITY));
        	r.setBuildingId(rs.getString(Constants.ROOM_BUILDINGID));
        	r.setBuilding(BuildingDAO.getBuildingByID(rs.getString(Constants.ROOM_BUILDINGID)));
        }
        
        con.close();
        st.close();
        
        return r;
    }
	
	public static Room getRoomByCode(String roomCode) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.ROOM_TABLE + " WHERE " + Constants.ROOM_CODE + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, roomCode);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        Room r = new Room();
        
        while(rs.next()){            
        	r.setId(rs.getString(Constants.ROOM_ID));
        	r.setRoomCode(rs.getString(Constants.ROOM_CODE));
        	r.setRoomLocation(rs.getString(Constants.ROOM_LOCATION));
        	r.setRoomCapacity(rs.getString(Constants.ROOM_CAPACITY));
        	r.setBuildingId(rs.getString(Constants.ROOM_BUILDINGID));
        	r.setBuilding(BuildingDAO.getBuildingByID(rs.getString(Constants.ROOM_BUILDINGID)));
        }
        
        con.close();
        st.close();
        
        return r;
    }
	
	public static ArrayList<Room> getAllRoomsOfBuilding(String buildingId, String roomType, String roomCapacity) throws SQLException{   
		ArrayList<Room> roomList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT * "+
        			"FROM "+ Constants.ROOM_TABLE +" "+
        			"WHERE "+ Constants.ROOM_BUILDINGID +" = " + buildingId;
        
        if(!roomType.equalsIgnoreCase("All")) query+= " AND "+ Constants.ROOM_TYPE + " LIKE '" + roomType + "'";
        if(!roomCapacity.equalsIgnoreCase("All")) query+= " AND "+ Constants.ROOM_CAPACITY + " = " + roomCapacity ;
        query+=" ORDER BY "+ Constants.ROOM_CODE +";";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
                
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	Room r = new Room();
        	r.setId(rs.getString(Constants.ROOM_ID));
        	r.setRoomCode(rs.getString(Constants.ROOM_CODE));
        	r.setRoomType(rs.getString(Constants.ROOM_TYPE));
        	r.setRoomCapacity(rs.getString(Constants.ROOM_CAPACITY));
        	r.setRoomLocation(rs.getString(Constants.ROOM_LOCATION));
        	r.setBuildingId(rs.getString(Constants.ROOM_BUILDINGID));
        	r.setBuilding(BuildingDAO.getBuildingByID(rs.getString(Constants.ROOM_BUILDINGID)));
        	
        	roomList.add(r);
        }
        
        con.close();
        st.close();
        
        return roomList;
    }
	
	public static ArrayList<Room> getAllRoomsWithSearchKey(Offering o, int buildingId, String roomType, String key) throws SQLException{   
		ArrayList<Room> roomList = new ArrayList<>();
        Connection con = Connector.getConnector();
        String defaultDay = "M", defaultBeginTime ="730", defaultEndTime ="900";
        
        /*
         * 
         * SELECT * FROM room r
WHERE r.room_id != ALL (SELECT d.room_id FROM days d, offering o
				WHERE d.offering_id = o.offering_id
				AND o.start_year = 2012
				AND o.end_year = 2013
				AND d.begin_time = 1440
				AND d.end_time = 1610
				AND d.class_day LIKE "M") AND building_id = 5 AND room_type LIKE '%lecture%' AND room_code LIKE '%G2%'
         * 
         */
        if(o.getDays().get(0) == null){
        	defaultDay = "M";
        	defaultBeginTime ="730";
        	defaultEndTime ="900";
        }else{
        	defaultDay = o.getDays().get(0).getClassDay();
        	defaultBeginTime = o.getDays().get(0).getBeginTime();
        	defaultEndTime = o.getDays().get(0).getEndTime();
        }
        
        if(!key.isEmpty() && removeSpaces(key).isEmpty()){
        	key = removeSpaces(key); //para empty na as in kasi pag pumasok dito ibig sabihin puro white spaces. Acts like SELECT ALL.
        }
        
        String query = "SELECT * "+
        			"FROM "+ Constants.ROOM_TABLE +" "+
        			"WHERE "+ Constants.ROOM_ID +" != ALL (SELECT d." + Constants.DAYS_ROOMID + " " +
        			"FROM " + Constants.DAYS_TABLE + " d, "+ Constants.OFFERING_TABLE + " o WHERE d."+
        			Constants.DAYS_OFFERINGID + " = o." + Constants.OFFERING_ID + " AND " +
        			"o." + Constants.OFFERING_STARTYEAR + " = ? AND " +
        			"o." + Constants.OFFERING_ENDYEAR + " = ? AND " +
        			"o." + Constants.OFFERING_TERM + " = ? AND " +
        			"d." + Constants.DAYS_BEGINTIME + " = ? AND " +
        			"d." + Constants.DAYS_ENDTIME + " = ? AND " +
        			"d." + Constants.DAYS_CLASSDAY + " LIKE '"+ defaultDay+"')"; 
        
        if(buildingId != -999) query+= " AND "+ Constants.ROOM_BUILDINGID + " = " + buildingId + " ";
        if(!roomType.equalsIgnoreCase("All")) query+= " AND "+ Constants.ROOM_TYPE + " LIKE '" + roomType + "' ";
        
        query+=" AND "+ Constants.ROOM_CODE +" LIKE '%"+ key +"%' ORDER BY "+ Constants.ROOM_CODE +";";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(o.getStartYear()));  
        st.setInt(2, Integer.parseInt(o.getEndYear()));
        st.setInt(3, Integer.parseInt(o.getTerm()));
        st.setInt(4, Integer.parseInt(defaultBeginTime));
        st.setInt(5, Integer.parseInt(defaultEndTime));
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	Room r = new Room();
        	r.setId(rs.getString(Constants.ROOM_ID));
        	r.setRoomCode(rs.getString(Constants.ROOM_CODE));
        	r.setRoomType(rs.getString(Constants.ROOM_TYPE));
        	r.setRoomCapacity(rs.getString(Constants.ROOM_CAPACITY));
        	r.setRoomLocation(rs.getString(Constants.ROOM_LOCATION));
        	r.setBuildingId(rs.getString(Constants.ROOM_BUILDINGID));
        	r.setBuilding(BuildingDAO.getBuildingByID(rs.getString(Constants.ROOM_BUILDINGID)));
        	
        	roomList.add(r);
        }
        
        con.close();
        st.close();
        
        return roomList;
    }
	
	public static ArrayList<Room> getAllRooms() throws SQLException{   
		ArrayList<Room> roomList = new ArrayList<>();
        Connection con = Connector.getConnector();
        String defaultDay = "M", defaultBeginTime ="730", defaultEndTime ="900";
        
        String query = "SELECT * FROM " + Constants.ROOM_TABLE + " WHERE "+ Constants.ROOM_CODE + " NOT LIKE 'No Room' ORDER BY " + Constants.ROOM_CODE;
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	Room r = new Room();
        	r.setId(rs.getString(Constants.ROOM_ID));
        	r.setRoomCode(rs.getString(Constants.ROOM_CODE));
        	r.setRoomType(rs.getString(Constants.ROOM_TYPE));
        	r.setRoomCapacity(rs.getString(Constants.ROOM_CAPACITY));
        	r.setRoomLocation(rs.getString(Constants.ROOM_LOCATION));
        	r.setBuildingId(rs.getString(Constants.ROOM_BUILDINGID));
        	r.setBuilding(BuildingDAO.getBuildingByID(rs.getString(Constants.ROOM_BUILDINGID)));
        	
        	roomList.add(r);
        }
        
        con.close();
        st.close();
        
        return roomList;
    }
	
	public static ArrayList<Room> getAllRoomsANOWithSearchKey(int buildingId, String roomType, String key) throws SQLException{   
		ArrayList<Room> roomList = new ArrayList<>();
        Connection con = Connector.getConnector();
        String defaultDay = "M", defaultBeginTime ="730", defaultEndTime ="900";
        
        /*
         * 
         * SELECT * FROM room WHERE building_id = 5 AND room_type LIKE '%lecture%' AND room_code LIKE '%G2%'
         * 
         */

        if(!key.isEmpty() && removeSpaces(key).isEmpty()){
        	key = removeSpaces(key); //para empty na as in kasi pag pumasok dito ibig sabihin puro white spaces. Acts like SELECT ALL.
        }
        
        String query = "SELECT * "+
        			"FROM "+ Constants.ROOM_TABLE +" "+
        			"WHERE ";
        
        if(buildingId != -999) query+= Constants.ROOM_BUILDINGID + " = " + buildingId + " AND ";
        
        if(!roomType.equalsIgnoreCase("All")) query+= Constants.ROOM_TYPE + " LIKE '" + roomType + "' AND ";
        
        query+= Constants.ROOM_CODE +" LIKE '%"+ key +"%' ORDER BY "+ Constants.ROOM_CODE +";";
        System.out.println(query);
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	Room r = new Room();
        	r.setId(rs.getString(Constants.ROOM_ID));
        	r.setRoomCode(rs.getString(Constants.ROOM_CODE));
        	r.setRoomType(rs.getString(Constants.ROOM_TYPE));
        	r.setRoomCapacity(rs.getString(Constants.ROOM_CAPACITY));
        	r.setRoomLocation(rs.getString(Constants.ROOM_LOCATION));
        	r.setBuildingId(rs.getString(Constants.ROOM_BUILDINGID));
        	r.setBuilding(BuildingDAO.getBuildingByID(rs.getString(Constants.ROOM_BUILDINGID)));
        	
        	roomList.add(r);
        }
        
        con.close();
        st.close();
        
        return roomList;
    }
	
	public static ArrayList<String> getAllUniqueRoomCapacity() throws SQLException{   
		ArrayList<String> capacityList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT("+ Constants.ROOM_CAPACITY +") FROM "+ Constants.ROOM_TABLE +" ORDER BY "+ Constants.ROOM_CAPACITY +";";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString(Constants.ROOM_CAPACITY);
        	
        	capacityList.add(s);
        }
        
        con.close();
        st.close();
        
        return capacityList;
    }
	
	public static ArrayList<String> getAllUniqueRoomType() throws SQLException{   
		ArrayList<String> typeList = new ArrayList<>();
        Connection con = Connector.getConnector();

        String query = "SELECT DISTINCT("+ Constants.ROOM_TYPE +") FROM "+ Constants.ROOM_TABLE +" ORDER BY "+ Constants.ROOM_TYPE +";";
        
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString(Constants.ROOM_TYPE);
        	
        	typeList.add(s);
        }
        
        con.close();
        st.close();
        
        return typeList;
    }
	
	public static String removeSpaces(String s){
		s = s.replaceAll("\\s",""); 
		return s;
	}
}
