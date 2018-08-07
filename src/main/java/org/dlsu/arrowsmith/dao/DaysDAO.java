package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaysDAO {
	
	public static ArrayList<Days> getListDays(String offeringId) throws SQLException {
		ArrayList<Days> days = new ArrayList<Days>();
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.DAYS_TABLE + " WHERE " + Constants.DAYS_OFFERINGID + " = ?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        st.setInt(1, Integer.parseInt(offeringId));
        
        ResultSet rs = st.executeQuery();
        
//        System.out.println("PStatement: " + st);
        
        while(rs.next()){
        	Days d = new Days();
            d.setDaysId(rs.getString(Constants.DAYS_ID));
        	d.setOfferingId(rs.getString(Constants.DAYS_OFFERINGID));
        	d.setClassDay(rs.getString(Constants.DAYS_CLASSDAY));
        	d.setBeginTime(rs.getString(Constants.DAYS_BEGINTIME));
        	d.setEndTime(rs.getString(Constants.DAYS_ENDTIME));
        	d.setRoomId(rs.getString(Constants.DAYS_ROOMID));
        	d.setRoom(RoomDAO.getRoomByID(d.getRoomId()));
        	
        	System.out.println("dayssss: " + d.getClassDay());
        	
            days.add(d);
        }
        
        con.close();
        st.close();
        
        return days;
	}
	
	public static void assignRoomToOffering(String offeringId, String roomId) throws SQLException {
		ArrayList<Days> days = new ArrayList<Days>();
        
        days = getListDays(offeringId);
        
        for(int i = 0; i< days.size(); i++){
        	Connection con = Connector.getConnector();
	        String query = "UPDATE " + Constants.DAYS_TABLE + " SET "+ Constants.DAYS_ROOMID +" = ? WHERE " + Constants.DAYS_ID + " = ?;";
	        
	        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
	        st.setInt(1, Integer.parseInt(roomId));
	        st.setInt(2, Integer.parseInt(days.get(i).getDaysId()));
	        
	        st.executeUpdate(); //use execute update instead of execute query
	           
	        con.close();
	        st.close();
        }
	}
	
	public static ArrayList<String> getAllUniqueTimeBlock(String startYear, String endYear, String term) throws SQLException{   
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();

        /***
         * 
         * Select DISTINCT(CONCAT(d.begin_time,"-",d.end_time)) as time_block, d.begin_time, d.end_time 
         * From days d, offering o where o.start_year = 2012 AND o.end_year = 2013 AND o.term =2 
         * AND d.offering_id = o.offering_id order by d.begin_time asc, d.end_time asc;
         */
        PreparedStatement st;
        
        if(!startYear.equalsIgnoreCase("none")){ //if may filters
	        String query = "SELECT DISTINCT(CONCAT(d."+ Constants.DAYS_BEGINTIME + ", \"-\", d."+ Constants.DAYS_ENDTIME+")) AS time_block, d." + Constants.DAYS_BEGINTIME + ", d."+ Constants.DAYS_ENDTIME +
	        		" FROM "+ Constants.DAYS_TABLE + " d, " + Constants.OFFERING_TABLE + " o " +
	        		" WHERE o." + Constants.OFFERING_STARTYEAR + " = ? AND o."+ Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ? AND o."+ Constants.OFFERING_ID + " = d." +Constants.DAYS_OFFERINGID +
	        		" ORDER BY d."+ Constants.DAYS_BEGINTIME + " ASC, d."+ Constants.DAYS_ENDTIME + " ASC;";
	        
	        st = (PreparedStatement) con.prepareStatement(query);
	        st.setInt(1, Integer.parseInt(startYear));
	        st.setInt(2, Integer.parseInt(endYear));
	        st.setInt(3, Integer.parseInt(term));
        }else{
        	String query = "SELECT DISTINCT(CONCAT(d."+ Constants.DAYS_BEGINTIME + ", \"-\", d."+ Constants.DAYS_ENDTIME+")) AS time_block, d." + Constants.DAYS_BEGINTIME + ", d."+ Constants.DAYS_ENDTIME +
	        		" FROM "+ Constants.DAYS_TABLE + " d, " + Constants.OFFERING_TABLE + " o " +
	        		" WHERE o."+ Constants.OFFERING_ID + " = d." +Constants.DAYS_OFFERINGID +
	        		" ORDER BY d."+ Constants.DAYS_BEGINTIME + " ASC, d."+ Constants.DAYS_ENDTIME + " ASC;";
	        
	        st = (PreparedStatement) con.prepareStatement(query);
        }
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){   
        	String s = rs.getString("time_block");
        	
        	sList.add(s);
        }
        
        con.close();
        st.close();
        
        return sList;
    }
	
	public static ArrayList<LoadTimeblock> getUniqueTimeBlocksOfFaculty(String startYear, String endYear, String term, String facultyId) throws SQLException{
		/*
		 * SELECT DISTINCT(CONCAT(d.begin_time, "-", d.end_time)) AS timeblock, d.begin_time, d.end_time 
		 * FROM days d, offering o, faculty f WHERE f.faculty_id = 46 AND f.faculty_id = o.faculty_id 
		 * AND o.offering_id = d.offering_id AND o.start_year = 2012 AND o.end_year = 2013 AND o.term = 2
		 * ORDER BY d.begin_time, d.end_time
		 */
		ArrayList<String> sList = new ArrayList<>();
        Connection con = Connector.getConnector();
        PreparedStatement st;
		
        String query = "SELECT DISTINCT(CONCAT(d."+ Constants.DAYS_BEGINTIME + ", \"-\", d."+ Constants.DAYS_ENDTIME+")) AS time_block, d." + Constants.DAYS_BEGINTIME + ", d."+ Constants.DAYS_ENDTIME +
        		" FROM "+ Constants.DAYS_TABLE + " d, " + Constants.OFFERING_TABLE + " o, " + Constants.FACULTY_TABLE + " f "+
        		" WHERE o." + Constants.OFFERING_STARTYEAR + " = ? AND o." + Constants.OFFERING_ENDYEAR + " = ? AND o." + Constants.OFFERING_TERM + " = ? AND o." + Constants.OFFERING_FACULTYID + " = ? AND o." +
        		Constants.OFFERING_ID + " = d." +Constants.DAYS_OFFERINGID + " AND f." + Constants.FACULTY_ID + " = o." + Constants.OFFERING_FACULTYID +
        		" ORDER BY d."+ Constants.DAYS_BEGINTIME + " ASC, d."+ Constants.DAYS_ENDTIME + " ASC;";
        
        System.out.println(query);
        System.out.println(startYear+"-"+endYear+"-"+term+"-"+facultyId);
        
        st = (PreparedStatement) con.prepareStatement(query);
        st.setString(1, startYear);
        st.setString(2, endYear);
        st.setString(3, term);
        st.setString(4, facultyId);
        
		ResultSet rs = st.executeQuery();
		ArrayList<LoadTimeblock> timeblockList = new ArrayList<LoadTimeblock>();
		
        while(rs.next()){   
        	String s = rs.getString("time_block");
        	LoadTimeblock lt = new LoadTimeblock();
        	lt.setBeginTime(rs.getString(Constants.DAYS_BEGINTIME));
        	lt.setEndTime(rs.getString(Constants.DAYS_ENDTIME));
        	lt.setFaculty(FacultyDAO.getFacultyByID(facultyId, startYear, endYear, term));
        	
        	ArrayList<Offering> offeringList = new ArrayList<Offering>();
        	offeringList = OfferingDAO.getOfferingsPerTimeBlockOfFaculty(startYear, endYear, term, facultyId, lt.getBeginTime(), lt.getEndTime());
        	
        	for(int i = 0; i < offeringList.size(); i++){
        		ArrayList<Days> daysList = offeringList.get(i).getDays();
        		Offering currentOffering = offeringList.get(i);
        		
        		for(int j = 0; j < daysList.size(); j++){
	    			Days d = daysList.get(j);
	        			
	    			if(d.getClassDay().equalsIgnoreCase("M")){
	    				lt.setMonday(new LoadDay(currentOffering));
	    				lt.setHasMonday(true);
	    			}else if(d.getClassDay().equalsIgnoreCase("T")){
	    				lt.setTuesday(new LoadDay(currentOffering));
	    				lt.setHasTuesday(true);
	    			}else if(d.getClassDay().equalsIgnoreCase("W")){
	    				lt.setWednesday(new LoadDay(currentOffering));
	    				lt.setHasWednesday(true);
	    			}else if(d.getClassDay().equalsIgnoreCase("H")){
	    				lt.setThursday(new LoadDay(currentOffering));
	    				lt.setHasThursday(true);
	    			}else if(d.getClassDay().equalsIgnoreCase("F")){
	    				lt.setFriday(new LoadDay(currentOffering));
	    				lt.setHasFriday(true);
	    			}else if(d.getClassDay().equalsIgnoreCase("S")){
	    				lt.setSaturday(new LoadDay(currentOffering));
	    				lt.setHasSaturday(true);
	    			}else{
	    				lt.setNoday(new LoadDay(currentOffering));
	    				lt.setHasNoday(true);
	    			}
    			}
        	}
        	timeblockList.add(lt);
        }
        
        con.close();
        st.close();
        
        return timeblockList;
	}
	
	public static void insertDay(Days d, int id, String startYear, String endYear, String term, String roomCode){
        try{
            Connection con = new Connector().getConnector();
            PreparedStatement ps;
            
            String query = "INSERT INTO " + Constants.DAYS_TABLE + " (" +
                    Constants.DAYS_OFFERINGID + ", " + Constants.DAYS_ROOMID + ", " + Constants.DAYS_CLASSDAY + ", " + Constants.DAYS_BEGINTIME+ ", " + Constants.DAYS_ENDTIME + ") VALUES(?,?,?,?,?);";

            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            if(roomCode.equalsIgnoreCase("") || roomCode.isEmpty()){
            	ps.setInt(2, Integer.parseInt(Constants.NO_ROOM_ID));
            }else{
            	ps.setInt(2, Integer.parseInt(RoomDAO.getRoomByCode(roomCode).getId()));
            }
            ps.setString(3, d.getClassDay());
            ps.setInt(4, Integer.parseInt(d.getBeginTime()));
            ps.setInt(5, Integer.parseInt(d.getEndTime()));

            ps.executeUpdate();

            ps.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	public static void deleteADay(String daysId) throws SQLException{
    	
    	try{
            Connection con = new Connector().getConnector();
            PreparedStatement ps;
            String query = "DELETE FROM " + Constants.DAYS_TABLE + " WHERE " + Constants.DAYS_ID + " = " + daysId + ";";
            ps = con.prepareStatement(query);
            ps.executeUpdate();

            ps.close();
            con.close();
    	}catch(Exception e){
            e.printStackTrace();
        }
    }
	
}
