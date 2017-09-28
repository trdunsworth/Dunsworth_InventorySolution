/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.View_Controller;

import dunsworth_ims4.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import dunsworth_ims4.Model.*;
import java.util.Optional;

/**
 * FXML Controller class
 *
 * @author trdunsworth
 */
public class MainScreenController {
    
    private MainApp mainApp;
    
    @FXML private TableView<Product> tblProducts;
    @FXML private TableColumn<Product, Number> colProductsID;
    @FXML private TableColumn<Product, String> colProductsName;
    @FXML private TableColumn<Product, Number> colProductsInStock;
    @FXML private TableColumn<Product, Number> colProductsPrice;
    
    @FXML private TableView<Part> tblParts;
    @FXML private TableColumn<Part, Number> colPartsID;
    @FXML private TableColumn<Part, String> colPartsName;
    @FXML private TableColumn<Part, Number> colPartsInStock;
    @FXML private TableColumn<Part, Number> colPartsPrice;
    
    @FXML private TextField txtPartsSearch;
    @FXML private TextField txtProductsSearch;
    
    @FXML private Button btnSearchParts;
    @FXML private Button btnAddParts;
    @FXML private Button btnModifyParts;
    @FXML private Button btnDeleteParts;
    
    @FXML private Button btnSearchProducts;
    @FXML private Button btnAddProducts;
    @FXML private Button btnModifyProducts;
    @FXML private Button btnDeleteProducts;
    
    @FXML private Button btnExit;
    
    public Integer productIDData;
    public String productNameData;
    public Double productPriceData;
    public Integer productInStockData;
    public Integer productMinData;
    public Integer productMaxData;
    
    public Integer partIDData;
    public String partNameData;
    public Double partPriceData;
    public Integer partInStockData;
    public Integer partMinData;
    public Integer partMaxData;
    
    public Scene scene;
    public Stage mainScreenStage;
    
    public void setStage(Stage mainScreenStage) {
        this.mainScreenStage = mainScreenStage;
    }
    
    public MainScreenController() {
        
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        colProductsID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
        colProductsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colProductsInStock.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        colProductsPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        
        // Initialize the parts table with four columns
        colPartsID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        colPartsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colPartsInStock.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        colPartsPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        
        // clear Product Details
        showProductDetails(null);
        
        // clear Part Details
        showPartDetails(null);
        
        // Listen for selection changes and show product details when changed. 
        tblProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
        
        // listen for selection changes and show part details when changed. 
        tblParts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPartDetails(newValue));
        
        tblParts.setItems(Inventory.getAllParts());
        tblProducts.setItems(Inventory.getProducts());
    }
    
    /**
     * Fills all text fields to show details about the Product.
     * If the specified product is null, all fields are cleared.
     * 
     * @param product the product or null 
     */
    private void showProductDetails(Product product) {
        if (product != null) {
            // Fill the labels with the info from the product object.
            productIDData = product.getProductID();
            productNameData = product.getName();
            productPriceData = product.getPrice();
            productInStockData = product.getInStock();
            productMinData = product.getMin();
            productMaxData = product.getMax();
        } else {
            // Everything is set to null
            productIDData = null;
            productNameData = null;
            productPriceData = null;
            productInStockData = null;
            productMinData = null;
            productMaxData = null;
        }
    }
    
    /**
     * Fills all text fields to show details about the Part.
     * If the specified part is null, all fields are cleared.
     * 
     * @param part the part or null. 
     */
    private void showPartDetails(Part part) {
        if (part != null) {
            // Assign the values to the proper variables.
            partIDData = part.getPartID();
            partNameData = part.getName();
            partPriceData = part.getPrice();
            partInStockData = part.getInStock();
            partMinData = part.getMin();
            partMaxData = part.getMax();
        } else {
            // everything is cleared out
            partIDData = null;
            partNameData = null;
            partPriceData = null;
            partInStockData = null;
            partMinData = null;
            partMaxData = null;
        }
    }
    
    @FXML
    public void showAddPartScreen() {
        mainApp.showAddPartScreen();
    }
    
    @FXML
    public void showModifyPartScreen() {
        Part selectedPart = tblParts.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Part Selected.");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to modify.");
            
            alert.showAndWait();
        } else {
            mainApp.showModifyPartScreen(selectedPart);
        }
    }
    
    @FXML
    public void handleDeletePart() {
        int selectedIndex = tblParts.getSelectionModel().getSelectedIndex();
        
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Press OK to confirm deletion.");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> tblParts.getItems().remove(selectedIndex));
            
            tblParts.setItems(Inventory.getAllParts());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected.");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to delete.");
            
            alert.showAndWait();
        }
    }
    
    @FXML
    public void showAddProductScreen() {
        mainApp.showAddProductScreen();
    }
    
    @FXML
    public void showModifyProductScreen() {
        Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
        
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Product Selected.");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to modify.");
            
            alert.showAndWait();
        } else {
            mainApp.showModifyProductScreen(selectedProduct);
        }
    }
    
    @FXML 
    public void handleDeleteProduct() {
        int selectedIndex = tblProducts.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert firstAlert = new Alert(Alert.AlertType.CONFIRMATION);
                firstAlert.setTitle("Confirm Deletion");
                firstAlert.setHeaderText(null);
                firstAlert.setContentText("Press OK to confirm deletion.");
                
                Optional<ButtonType> result = firstAlert.showAndWait();
                
                if (result.get() == ButtonType.OK) {
                    Product tempProduct = tblProducts.getSelectionModel().getSelectedItem();
                    if (tempProduct.getAssociatedParts().size() > 0) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Parts still in Product");
                        alert.setHeaderText(null);
                        alert.setContentText("There are parts still attached to this product./nPlease remove all parts to delete the product.");
                        alert.showAndWait();
                    } else {
                        tblProducts.getItems().remove(selectedIndex);
                        tblProducts.setItems(Inventory.getProducts());
                    }
                }  
            } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("No Product Selected.");
            alert1.setHeaderText(null);
            alert1.setContentText("Please select a product to delete.");
        }
    }
    
    @FXML
    private void searchPart() {
        String searchItem = txtPartsSearch.getText();
        boolean found = false;
        try {
            int IDNumber = Integer.parseInt(searchItem);
            for (Part p : Inventory.getAllParts()) {
                if (p.getPartID() == IDNumber) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Found");
                    alert.setHeaderText(null);
                    if (p.isInhouse()) {
                        alert.setContentText("ID: " + p.getPartID() + "\nName: " + p.getName() + "\nPrice: " + p.getPrice() + "\nCurrent stock on hand: " + p.getInStock() + "\nMinimun stock level: " + p.getMin() + "\nMaximum stock level: " + p.getMax() + "\nMachine ID: " + ((Inhouse) p).getMachineID());
                    } else {
                        alert.setContentText("ID: " + p.getPartID() + "\nName: " + p.getName() + "\nPrice: " + p.getPrice() + "\nCurrent stock on hand: " + p.getInStock() + "\nMinimun stock level: " + p.getMin() + "\nMaximum stock level: " + p.getMax() + "\nSupplier Name: " + ((Outsourced) p).getCompanyName());
                    }
                    alert.showAndWait();
                    found = true;
                }
                
                if (found == false) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Part Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry. The part with ID Number " + searchItem + " was not found.");
                    alert.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            for (Part p : Inventory.getAllParts()) {
                if (p.getName().equals(searchItem)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Found");
                    alert.setHeaderText(null);
                    if (p.isInhouse()) {
                        alert.setContentText("ID: " + p.getPartID() + "\nName: " + p.getName() + "\nPrice: " + p.getPrice() + "\nCurrent stock on hand: " + p.getInStock() + "\nMinimun stock level: " + p.getMin() + "\nMaximum stock level: " + p.getMax() + "\nMachine ID: " + ((Inhouse) p).getMachineID());
                    } else {
                        alert.setContentText("ID: " + p.getPartID() + "\nName: " + p.getName() + "\nPrice: " + p.getPrice() + "\nCurrent stock on hand: " + p.getInStock() + "\nMinimun stock level: " + p.getMin() + "\nMaximum stock level: " + p.getMax() + "\nSupplier Name: " + ((Outsourced) p).getCompanyName());
                    }
                    alert.showAndWait();
                    found = true;
                }
                if (found == false) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Part Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry. The part with name " + searchItem + " was not found.");
                    alert.showAndWait();
                }
            }
        }
    }
    
    @FXML
    private void searchProduct() {
        String searchItem = txtProductsSearch.getText();
        boolean found = false;
        try {
            int IDNumber = Integer.parseInt(searchItem);
            for (Product p : Inventory.getProducts()) {
                if (p.getProductID() == IDNumber) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product Found");
                    alert.setHeaderText(null);
                    alert.setContentText("ID: " + p.getProductID() + "\nName: " + p.getName() + "\nPrice " + p.getPrice() + "\nCurrent stock on hand" + p.getInStock() + "\nMinimum stock level: " + p.getMin() + "\nMaximum stock level: " + p.getMax());
                    alert.showAndWait();
                    found = true;
                }
                if (found == false ) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Product Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry, the part with ID Number " + searchItem + " was not found.");
                    alert.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            for (Product p : Inventory.getProducts()) {
                if (p.getName().equals(searchItem)) {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product Found");
                    alert.setHeaderText(null);
                    alert.setContentText("ID: " + p.getProductID() + "\nName: " + p.getName() + "\nPrice " + p.getPrice() + "\nCurrent stock on hand" + p.getInStock() + "\nMinimum stock level: " + p.getMin() + "\nMaximum stock level: " + p.getMax());
                    alert.showAndWait();
                    found = true; 
                }
                if (found == false) {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Product Found");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry, the part with name " + searchItem + " was not found.");
                    alert.showAndWait(); 
                }
            }
        }
    }
    
        
    public void handleExit() {
        mainApp.appExit();
    }

    public void setMainApp(MainApp mainApp) {
       this.mainApp = mainApp;
    }
}
