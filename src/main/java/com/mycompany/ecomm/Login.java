/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecomm;

import java.security.*;
import java.sql.*;
import java.nio.charset.*;
import java.math.*;
/**
 *
 * @author saubhagyadwitiya
 */
public class Login {
    
    private static byte[] getSha(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    private static String getEncryption(String password){
        try{
            BigInteger num = new BigInteger(1,getSha(password));
            StringBuilder hexaString = new StringBuilder(num.toString(16));
            return hexaString.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static Customer customerLogin(String email, String password){
        
        String encryptedPass = getEncryption(password);
        String query = "SELECT * FROM customers WHERE email='"+email+"' and password='"+encryptedPass+"'";
        DatabaseConnection dbcon = new DatabaseConnection();
        try{
            ResultSet rs = dbcon.getQueryTable(query);
            
            if(rs!=null && rs.next()){
                return new Customer(rs.getInt("cid"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
        
    }
    public static void main(String[] args) {
        //System.out.println(customerLogin("saubhagyagupta8@gmail.com","Saubhagya@123"));
    }
}
