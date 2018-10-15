package org.dlsu.arrowsmith.dao;

import org.dlsu.arrowsmith.extraModels.AcademicYear;
import org.dlsu.arrowsmith.repositories.Connector;
import org.dlsu.arrowsmith.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicYearDAO {
	public static ArrayList<AcademicYear> getAllAY() throws SQLException{
		ArrayList<AcademicYear> AYList = new ArrayList<>();
        Connection con = Connector.getConnector();
        /*
        SELECT DISTINCT(CONCAT(start_year, "-", end_year, "-", term, "-", batch)) AS AY, start_year, end_year, term, batch FROM offering ORDER BY AY asc
         * 
         */
        String query = "SELECT DISTINCT(CONCAT("+ Constants.OFFERING_STARTYEAR +", \"-\", "+ Constants.OFFERING_ENDYEAR +
        				", \"-\", "+ Constants.OFFERING_TERM +")) "
        				+ "AS AY, "+ Constants.OFFERING_STARTYEAR +", "+ Constants.OFFERING_ENDYEAR +", "
        				+ Constants.OFFERING_TERM + " FROM "+ Constants.OFFERING_TABLE 
        				+" ORDER BY AY DESC";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        
        //System.out.println(query);
        
        ResultSet rs = st.executeQuery();//wla pong variable dapat laman dito ty
        
        while(rs.next()){   
        	AcademicYear ay = new AcademicYear();
        	ay.setId(rs.getString("AY"));
        	ay.setAY(rs.getString("AY"));
        	ay.setStartYear(rs.getString(Constants.OFFERING_STARTYEAR));
        	ay.setEndYear(rs.getString(Constants.OFFERING_ENDYEAR));
        	ay.setTerm(rs.getString(Constants.OFFERING_TERM));
        	ay.setUnPublishedCount(OfferingDAO.countUnpublished(rs.getString(Constants.OFFERING_STARTYEAR), rs.getString(Constants.OFFERING_ENDYEAR), rs.getString(Constants.OFFERING_TERM))+"");
        	
        	AYList.add(ay);
        }
        
        con.close();
        st.close();
        
        return AYList;
    }
}
