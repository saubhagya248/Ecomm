/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecomm;

import java.sql.*;
/**
 *
 * @author saubhagyadwitiya
 */
public class DatabaseConnection {
    
    String dbURL = "jdbc:mysql://localhost:3306/ecomm?autoReconnect=true&useSSL=false";
    
    String userName = "root";
    String password = "Saubhagya@123";
    
    public Statement getStatement(){
        try{
            Connection conn = DriverManager.getConnection(dbURL,userName,password);
            return conn.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public ResultSet getQueryTable(String query){
        Statement statement  = getStatement();
        try{
            return statement.executeQuery(query);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean insertUpdate(String query){
        Statement statement = getStatement();
        try{
            statement.executeUpdate(query);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        String query = "select * from products";
        DatabaseConnection dbconn = new DatabaseConnection();
        ResultSet rs = dbconn.getQueryTable(query);
        if(rs!=null){
            System.out.print("connected to databse");
        }
    }
}
