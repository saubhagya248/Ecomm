package com.mycompany.ecomm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
//
/**
 * JavaFX App
 */
public class App extends Application {

    productList p = new productList();
    private static Scene scene;
    
    private static final int width = 600;
    private static final int height = 400;
    private static final int headerLine = 50;
    private static Pane bodyPane;
    private static GridPane headerBar;
    private static GridPane footerBar;
    
    ObservableList<Product> cartItemList = FXCollections.observableArrayList();

    Label welcomeLabel = new Label("Welcome Customer");
    Customer loggedInCustomer = null;
    Order order = new Order();
    
    //headerBar buttons and fields
        Button SignInButton = new Button("Sign In");
        Button LogOutButton = new Button("LogOut");
        TextField searchField = new TextField();
        Button searchButton = new Button("search");
        Button cart = new Button("cart");
        Button orders = new Button("orders");


        
        
    //footerBar buttons
        Button buyNow = new Button("Buy Now");
        Button addToCart = new Button("Add to Cart");

        Button removeFromCart = new Button("Remove from Cart");
        Button placeOrder = new Button("Place Orders from cart");


    
    
    
    public void addItemsToCart(Product product){
        if(cartItemList.contains(product)) return;
        cartItemList.add(product);
    }

    private void initialState(){
        cart.setVisible(false);
        orders.setVisible(false);
        LogOutButton.setVisible(false);
        SignInButton.setVisible(true);
        searchField.setVisible(false);
        searchButton.setVisible(false);
        addToCart.setVisible(false);
        removeFromCart.setVisible(false);
        buyNow.setVisible(false);
        placeOrder.setVisible(false);
        welcomeLabel.setText("Welcome Customer!");
    }
    private GridPane headerBar(){
        GridPane header = new GridPane();
        header.setVgap(10);
        header.setHgap(10);
        searchField.setPromptText("Enter the product name");
        
        
        Label welcome = new Label("Welcome to Ecomm");
        //setting some nodes invisible as they will only be shown when customer will login
        initialState();
        
        searchButton.setOnAction(e -> {
            String str = searchField.getText();
            addToCart.setVisible(true);
            buyNow.setVisible(true);
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(p.getAllProducts(str));
        });
        
        SignInButton.setOnAction(e -> {
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(loginPane());
        });
        
        
        cart.setOnAction(e -> {
            bodyPane.getChildren().clear();
            placeOrder.setVisible(true);
            addToCart.setVisible(false);
            bodyPane.getChildren().add(p.getItemsInCart(cartItemList));
        });
        
        orders.setOnAction(e -> {
            bodyPane.getChildren().clear();
            addToCart.setVisible(false);
            buyNow.setVisible(false);
            bodyPane.getChildren().add(order.getOrders(loggedInCustomer));
        });
        
        LogOutButton.setOnAction(e -> {
            String name = loggedInCustomer.getName();
            System.out.println(name);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Status");
            alert.setContentText(name+" you are logged out successfully");
            initialState();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(loginPane());
            loggedInCustomer = null;
        });
        
        header.setHgap(10);
        header.setPadding(new Insets(10,10,10,10));
        
        header.add(searchField,0,0);
        header.add(searchButton,1,0);
        header.add(SignInButton,2,0);
        header.add(LogOutButton,2,0);
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
        //loginButton
        loginButton.setOnAction(e -> {
            String use = user.getText();
            String pas = pass.getText();
            loggedInCustomer = Login.customerLogin(use, pas);
            if(loggedInCustomer!=null){
                status.setText("Login Successfull!");
                SignInButton.setVisible(false);
                cart.setVisible(true);
                orders.setVisible(true);
                LogOutButton.setVisible(true);
                searchField.setVisible(true);
                buyNow.setVisible(true);
                addToCart.setVisible(true);
                removeFromCart.setVisible(true);
                searchButton.setVisible(true);
                welcomeLabel.setText("Welcome "+loggedInCustomer.getName()+"!");
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(p.getAllProducts(""));
            }
            else{
                status.setText("Login Failed!");
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
        
        buyNow.setVisible(false);
        placeOrder.setVisible(false);
        addToCart.setVisible(false);
        removeFromCart.setVisible(false);

        
        buyNow.setOnAction(e -> {
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
        });
        
        placeOrder.setOnAction(e -> {
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
        });
        
        addToCart.setOnAction(e -> {
            removeFromCart.setVisible(true);
            Product product = p.getSelectedProduct();
            addItemsToCart(product);
        });

        removeFromCart.setOnAction(e -> {
            Product sel = p.getSelectedProduct();
            cartItemList.remove(sel);
        });
        GridPane footer = new GridPane();
        
        footer.setPadding(new Insets(10,10,10,10));
        footer.setHgap(10);
        
        footer.setTranslateY(height+headerLine);
        footer.add(buyNow, 0, 0);
        footer.add(addToCart,1,0);
        footer.add(removeFromCart,2,0);
        footer.add(placeOrder,3,0);
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
        headerBar = new GridPane();
        headerBar.getChildren().add(headerBar());

        footerBar = new GridPane();
        footerBar.getChildren().add(footerPane());
        
        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);
        bodyPane.getChildren().add(loginPane());
        
        root.getChildren().addAll(headerBar, bodyPane, footerBar);
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