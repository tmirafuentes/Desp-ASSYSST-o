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
public class UserDAO {
    public static void insertUser(User u){
        try{
            Connection con = Connector.getConnector();
            PreparedStatement ps;
            String query = "INSERT INTO " + Constants.USERS_TABLE + " (" + Constants.USERS_ID + ", " +
                    Constants.USERS_COLLEGEID + ", " + Constants.USERS_DEPTID + ", " + Constants.USERS_FIRSTNAME + ", " + Constants.USERS_MIDDLENAME + ", " + Constants.USERS_LASTNAME + ", " + Constants.USERS_TYPE + ", " + Constants.USERS_PASSWORD + ") VALUES(?,?,?,?,?,?,?,?);";

            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(u.getUserId()));
            ps.setInt(2, Integer.parseInt(u.getCollegeID()));
            ps.setInt(3, Integer.parseInt(u.getDeptID()));
            ps.setString(4, u.getFirstName());
            ps.setString(5, u.getMiddleName());
            ps.setString(6, u.getLastName());
            ps.setString(7, u.getUserType());
            ps.setString(8, u.getUserPassword());

            ps.executeUpdate();

            ps.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static ArrayList<User> getListUsers() throws SQLException{
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.USERS_TABLE;
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        ArrayList<User> users = new ArrayList<User>();
        
        while(rs.next()){
            User u = new User();
            u.setId(rs.getString(Constants.USERS_ID));
            u.setCollegeID(rs.getString(Constants.USERS_COLLEGEID)); 
            u.setDeptID(rs.getString(Constants.USERS_DEPTID)); 
            u.setFirstName(rs.getString(Constants.USERS_FIRSTNAME)); 
            u.setMiddleName(rs.getString(Constants.USERS_MIDDLENAME)); 
            u.setLastName(rs.getString(Constants.USERS_LASTNAME)); 
            u.setUserType(rs.getString(Constants.USERS_TYPE)); 
            u.setUserPassword(rs.getString(Constants.USERS_PASSWORD)); 
            u.setCollege(CollegeDAO.getCollegeByID(rs.getString(Constants.USERS_COLLEGEID)));
            u.setDepartment(DepartmentDAO.getDepartmentByID(rs.getString(Constants.USERS_DEPTID)));

            users.add(u);
        }
        
        con.close();
        st.close();
        
        return users;
    }
    
    public static User getUserByID(String ID) throws SQLException{        
        Connection con = Connector.getConnector();
        String query = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_ID + " = '" + ID + "'";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        User u = new User();
        //System.out.println(query);
        while(rs.next()){
            
            u.setId(rs.getString(Constants.USERS_ID));
            u.setCollegeID(rs.getString(Constants.USERS_COLLEGEID)); 
            u.setDeptID(rs.getString(Constants.USERS_DEPTID)); 
            u.setFirstName(rs.getString(Constants.USERS_FIRSTNAME)); 
            u.setMiddleName(rs.getString(Constants.USERS_MIDDLENAME)); 
            u.setLastName(rs.getString(Constants.USERS_LASTNAME)); 
            u.setUserType(rs.getString(Constants.USERS_TYPE)); 
            u.setUserPassword(rs.getString(Constants.USERS_PASSWORD)); 
            u.setCollege(CollegeDAO.getCollegeByID(rs.getString(Constants.USERS_COLLEGEID)));
            u.setDepartment(DepartmentDAO.getDepartmentByID(rs.getString(Constants.USERS_DEPTID)));
        }
        
        con.close();
        st.close();
        
        return u;
    }
    
}
