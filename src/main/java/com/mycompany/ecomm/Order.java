/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecomm;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author saubhagyadwitiya
 */
public class Order {
    TableView<Product> orderTable;
    public Pane getOrders(Customer customer){
            String query = "select orders.oid, products.name, products.price from orders inner join products on orders.product_id=products.pid where customer_id="+customer.getId();
            DatabaseConnection dbConn = new DatabaseConnection();
            ResultSet rs = dbConn.getQueryTable(query);
            ObservableList<Product> res = FXCollections.observableArrayList();
        try{
            while(rs.next()){
                res.add(new Product(rs.getInt("oid"),rs.getString("name"),rs.getDouble("price")));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tableFromList(res);
    }
    
    public  Pane tableFromList(ObservableList<Product> productList){
        
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        orderTable = new TableView();
        orderTable.setItems(productList);
        orderTable.getColumns().addAll(id,name,price);
        
        Pane TablePane = new Pane();
        TablePane.getChildren().add(orderTable);
        
        return TablePane;
    }
    
    public  boolean placeOrder(Customer cust, Product prod){
        
        try{
            String query = "insert into orders(customer_id,product_id,status) values("+cust.getId()+","+prod.getId()+",'Ordered')";
            
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public  int placeMultipleOrders(ObservableList<Product> list, Customer customer){
        int count = 0;
        
        for(Product product: list){
            placeOrder(customer,product);
            count++;
        }
        
        return count;
    }
    
    
}
