/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecomm;

/**
 *
 * @author saubhagyadwitiya
 */
public class Customer {
    int id;
    String name;
    String email;
    String address;
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setAddress(String address){
        this.address = address;
    }

    // Getter
    public String getAddress() {
        return this.address;
    }
    
    Customer(int id, String name, String email, String address){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
    
}
