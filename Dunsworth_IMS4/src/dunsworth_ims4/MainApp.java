/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4;

import dunsworth_ims4.Model.*;
import static dunsworth_ims4.Model.Part.getIDNumber;
import dunsworth_ims4.View_Controller.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author tony.dunsworth
 */
public class MainApp extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public MainApp() {
        Inventory.addPart(new Inhouse(101, getIDNumber(), "Monitor", 125.00, 20, 2, 25));
        Inventory.addPart(new Outsourced("ASUS", getIDNumber(), "Motherboard", 395.00, 5, 2, 10));
        Inventory.addPart(new Inhouse(102, getIDNumber(), "Keyboard", 5.00, 100, 10, 1000));
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management System");
        
        initRootLayout();
        
        showMainScreen();
    }
    
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View_Controller/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View_Controller/MainScreen.fxml"));
            AnchorPane inventoryOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(inventoryOverview);
            MainScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showAddPartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View_Controller/AddPartScreen.fxml"));
            AnchorPane addPartPane = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Part");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addPartPane);
            dialogStage.setScene(scene);
            
            AddPartScreenController controller = loader.getController();
            controller.setStage(dialogStage);
            
            dialogStage.showAndWait();
            
            return controller.isClicked();
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showModifyPartScreen(Part part) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View_Controller/ModifyPartScreen.fxml"));
            AnchorPane modPartPane = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modify Selected Part");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(modPartPane);
            dialogStage.setScene(scene);
            
            ModifyPartScreenController controller = loader.getController();
            controller.setModifyPartStage(dialogStage);
            controller.setPartsInCommon(part);
                    
            dialogStage.showAndWait();
            
            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showAddProductScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View_Controller/AddProductScreen.fxml"));
            AnchorPane addProdPane = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addProdPane);
            dialogStage.setScene(scene);
            
            AddProductScreenController controller = loader.getController();
            controller.setStage(dialogStage);
            
            dialogStage.showAndWait();
            
            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showModifyProductScreen(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View_Controller/ModifyProductScreen.fxml"));
            AnchorPane modProdPane = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modify Product Information");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(modProdPane);
            dialogStage.setScene(scene);
            
            ModifyProductScreenController controller = loader.getController();
            controller.setModifyProductStage(dialogStage);
            controller.setProduct(product);
            
            dialogStage.showAndWait();
            
            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    public void appExit() {
        primaryStage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
