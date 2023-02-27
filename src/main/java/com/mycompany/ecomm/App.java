package com.mycompany.ecomm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

/**
 * JavaFX App
 */
public class App extends Application {

    productList p = new productList();
    private static Scene scene;
    
    private static int width = 600, height = 400, headerLine = 50;
    private static Pane bodyPane;
    
    ObservableList<Product> cartItemList = FXCollections.observableArrayList();
    Button SignInButton = new Button("Sign In");
    Label welcomeLabel = new Label("Welcome Customer");
    Customer loggedInCustomer = null;
    Order order = new Order();
    
    
    
    public void addItemsToCart(Product product){
        if(cartItemList.contains(product)) return;
        cartItemList.add(product);
    }
    
    private GridPane headerBar(){
        GridPane header = new GridPane();
        header.setVgap(10);
        header.setHgap(10);
        TextField searchField = new TextField();
        searchField.setPromptText("enter text here");
        Button searchButton = new Button("search");
        Button cart = new Button("cart");
        Button orders = new Button("orders");
        
        
        searchButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                String str = searchField.getText();
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(p.getAllProducts(str));
            }
        });
        
        SignInButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPane());
            }
        });
        
        cart.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(p.getItemsInCart(cartItemList));
            }
        });
        
        orders.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(order.getOrders(loggedInCustomer));
            }
        });
        
        header.setHgap(10);
        
        header.setPadding(new Insets(10,10,10,10));
        
        header.add(searchField,0,0);
        header.add(searchButton,1,0);
        header.add(SignInButton,2,0);
        header.add(orders,3,0);
        header.add(cart,4,0);
        header.add(welcomeLabel,5,0);
        
        return header;
    }
    
    private GridPane loginPane(){
        GridPane loginPane = new GridPane();
        
        Label userName = new Label("email");
        TextField user = new TextField();
        user.setPromptText("Enter email address");
        Label password = new Label("password");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter your password");
        Button loginButton = new Button("Login");
        Label status = new Label("Status");
        
        loginButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                String use = user.getText();
                String pas = pass.getText();
                loggedInCustomer = Login.customerLogin(use, pas);
                if(loggedInCustomer!=null){
                    status.setText("Login Successfull!");
                    welcomeLabel.setText("Welcome "+loggedInCustomer.getName()+"!");
                }
                else{
                    status.setText("Login Failed!");
                }
            }
        });
        
        loginPane.setAlignment(Pos.CENTER);
                
        loginPane.setTranslateX(100);
        loginPane.setTranslateY(150);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.add(userName,0,1);
        loginPane.add(user,1,1);
        loginPane.add(password,0,2);
        loginPane.add(pass,1,2);
        loginPane.add(loginButton,0,3);
        loginPane.add(status,1,3);
        
        return loginPane;
        
    }
    
    private GridPane footerPane(){
        Button buyNow = new Button("Buy Now");
        Button addToCart = new Button("Add to Cart");
        Button placeOrder = new Button("Place Orders from cart");
        
        buyNow.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Product product = p.getSelectedProduct();
                boolean orderStatus = false;
                if(product!=null && loggedInCustomer!=null){
                    orderStatus = order.placeOrder(loggedInCustomer, product);                    
                }
                
                if(orderStatus){
                    showDialog("Successful!");
                }
                else{
                    showDialog("Failed!");
                }
            }
        });
        
        placeOrder.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                int orderCount = 0;
                if(!cartItemList.isEmpty() && loggedInCustomer!=null){
                    orderCount = order.placeMultipleOrders(cartItemList, loggedInCustomer);
                }
                
                if(orderCount>0){
                    showDialog(orderCount+"orders placed successfully!");
                }
                else{
                    showDialog("Failed!");
                }
            }
        });
        
        addToCart.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Product product = p.getSelectedProduct();
                addItemsToCart(product);
            }
        });
        GridPane footer = new GridPane();
        
        footer.setPadding(new Insets(10,10,10,10));
        footer.setHgap(10);
        
        footer.setTranslateY(height+headerLine);
        footer.add(buyNow, 0, 0);
        footer.add(addToCart,1,0);
        footer.add(placeOrder,2,0);
        return footer;
    }
    
    private void showDialog(String data) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Order Status");
    alert.setHeaderText(null);
    alert.setContentText("Your order status is: " + data);
    alert.showAndWait();
    }
    
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width, height+2*headerLine);
        
        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);
        
        bodyPane.getChildren().add(loginPane());
        
        root.getChildren().addAll(headerBar(), bodyPane, footerPane());
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(createContent());
        stage.setScene(scene);
        stage.setTitle("Ecommerce");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}