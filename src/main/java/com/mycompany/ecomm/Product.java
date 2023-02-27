/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecomm;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

/**
 *
 * @author saubhagyadwitiya
 */
public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }
    
    public Product(int Id,String Name,Double Price){
        this.id = new SimpleIntegerProperty(Id);
        this.name = new SimpleStringProperty(Name);
        this.price = new SimpleDoubleProperty(Price);
        
    }
    
    public static ObservableList<Product> getAllProducts(){
        String query = "select * from products";
        return getProducts(query);
    }
    
    public static ObservableList<Product> getAllProducts(String search){
        String query = "select * from products where name like '%"+search+"%'";
        return getProducts(query);
    }
    
    public static ObservableList<Product> getProducts(String query){
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Product> res = FXCollections.observableArrayList();
        try{
            while(rs.next()){
                res.add(new Product(rs.getInt("pid"), rs.getString("name"), rs.getDouble("price"))
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        
        return res;
    }
    
}
