/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ecomm;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections.*;
import javafx.collections.FXCollections;
/**
 *
 * @author saubhagyadwitiya
 */
public class productList {
    public TableView<Product> productTable;
    
    public Pane getAllProducts(String str){
        ObservableList<Product> data = Product.getAllProducts(str);
        return tableFromList(data);
    }
    
    public Pane tableFromList(ObservableList<Product> productList){
        
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTable = new TableView();
        productTable.setItems(productList);
        productTable.getColumns().addAll(id,name,price);
        
        Pane TablePane = new Pane();
        TablePane.getChildren().add(productTable);
        
        return TablePane;
    }
    
    public Pane getItemsInCart(ObservableList<Product> List){
        return tableFromList(List);
    }
    
    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }
}
