/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dlsu.arrowsmith.repositories;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author asus
 */
public class Connector {
    
    private String host = "jdbc:mysql://localhost:3306/arrowsmithvampire";
    private String username = "root";
    private String password = "root";
    public Connection con;
    
    public Connector(){
    }
    
    public static Connection getConnector(){
    	Connector dbcon = new Connector();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection(dbcon.getHost(), dbcon.getUsername(), dbcon.getPassword());
            
            return con;
        }catch(Exception e){
            System.out.println("Unable to connect to DB");
        }
        
        return null;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
        
}
