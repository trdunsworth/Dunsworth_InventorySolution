/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.View_Controller;

import dunsworth_ims4.MainApp;
import dunsworth_ims4.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trdunsworth
 */
public class AddProductScreenController {
    
    private MainApp mainApp;
    
    @FXML private TextField txtAddProdMax;
    @FXML private TextField txtAddProdMin;
    @FXML private TextField txtAddProdID;
    @FXML private TextField txtAddProdName;
    @FXML private TextField txtAddProdInStock;
    @FXML private TextField txtAddProdPrice;
    @FXML private TextField txtAddProdSearch;
    
    @FXML private Button btnAddProdSave;
    @FXML private Button btnAddProdCancel;
    @FXML private Button btnAddProdSearch;
    @FXML private Button btnAddProdDelete;
    @FXML private Button btnAddProdAddPart;
    
    @FXML private TableView<Part> tblAddProdAddPart;
    @FXML private TableColumn<Part, Number> colAddProdAPPartID;
    @FXML private TableColumn<Part, String> colAddProdAPName;
    @FXML private TableColumn<Part, Number> colAddProdAPInStock;
    @FXML private TableColumn<Part, Number> colAddProdAPPrice;

    @FXML private TableView<Part> tblAddProdDelPart;
    @FXML private TableColumn<Part, Number> colAddProdDPPartID;
    @FXML private TableColumn<Part, String> colAddProdDPName;
    @FXML private TableColumn<Part, Number> colAddProdDPInStock;
    @FXML private TableColumn<Part, Number> colAddProdDPPrice;
  
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();
    private boolean okClicked = false;
    public Scene scene;
    public Stage addProductScreenStage;
    
    public void setStage(Stage addProductScreenStage) {
        this.addProductScreenStage = addProductScreenStage;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        colAddProdAPPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        colAddProdAPName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colAddProdAPInStock.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        colAddProdAPPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        
        colAddProdDPPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        colAddProdDPName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colAddProdDPInStock.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        colAddProdDPPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        
        tblAddProdAddPart.setItems(Inventory.getAllParts());
    }
    
    @FXML
    private void handleSearchPart() {
       String searchItem = txtAddProdSearch.getText();
       boolean found = false;
       if ((searchItem.length() == 0) || (searchItem == null)) {
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle("No Part Given");
           alert.setHeaderText(null);
           alert.setContentText("Please supply a parameter for part search.");
           alert.showAndWait();
        } else {
           try {
                int IDNumber = Integer.parseInt(searchItem);
                for (Part p : Inventory.getAllParts()) {
                    if (p.getPartID() == IDNumber) {
                        Alert alert = new Alert(AlertType.INFORMATION);
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
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("No Part Found");
                        alert.setHeaderText(null);
                        alert.setContentText("Sorry. The part with ID Number " + searchItem + " was not found.");
                        alert.showAndWait();
                    }
                }    
            } catch (NumberFormatException e) {
                for (Part p : Inventory.getAllParts()) {
                    if (p.getName().equals(searchItem)) {
                        Alert alert = new Alert(AlertType.INFORMATION);
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
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("No Part Found");
                        alert.setHeaderText(null);
                        alert.setContentText("Sorry. The part with name " + searchItem + " was not found.");
                        alert.showAndWait();
                    }
                }
            }
        }
    }
    
    @FXML
    private void handleAddProduct() {
        if (isValid()) {
            Product product = new Product();
            
            product.setProductID(Product.getIDNumber());
            product.setName(txtAddProdName.getText());
            product.setInStock(Integer.parseInt(txtAddProdInStock.getText()));
            product.setPrice(Double.parseDouble(txtAddProdPrice.getText()));
            product.setMin(Integer.parseInt(txtAddProdMin.getText()));
            product.setMax(Integer.parseInt(txtAddProdMax.getText()));
            product.setAssociatedParts(addedParts);
            
            Inventory.addProduct(product);
            
            okClicked = true;
            
            addProductScreenStage.close();
        }
    }
    
    @FXML
    private void handleAddPart() {
        Part partToAdd = tblAddProdAddPart.getSelectionModel().getSelectedItem();
        
        if (partToAdd == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to add to the product.");
            
            alert.showAndWait();
        } else {
            addedParts.add(partToAdd);
            showAssociatedParts();
        }
    }
    
    @FXML
    private void handleDeletePart() {
        Part partToRemove = tblAddProdDelPart.getSelectionModel().getSelectedItem();
        
        if (partToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to remove from the product");
            
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Press OK to confirm deletion.");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> addedParts.remove(partToRemove));
            showAssociatedParts();
        }
    }

    @FXML
    private void handleCancel() {
        addProductScreenStage.close();
    }
    
    public void setPartsList(Inventory inventory) {
        tblAddProdAddPart.setItems(Inventory.getAllParts());
    }
    
    public void showAssociatedParts() {
        tblAddProdDelPart.setItems(addedParts);
    }
    
    public boolean isValid() {
        String eMsg = "";
        
        if ((txtAddProdName.getText().length() == 0) || (txtAddProdName.getText() == null)) {
            eMsg += "Please supply a name for the product.\n";
        } 
        
        if ((txtAddProdInStock.getText().length() == 0) || (txtAddProdInStock.getText() == null)) {
            eMsg += "Please supply a current inventory level.\n";
        } else {
            try {
                Integer.parseInt(txtAddProdInStock.getText());
            } catch (NumberFormatException e) {
                eMsg += "Current inventory level must be an integer.\n";
            }
        }
        
        if ((txtAddProdPrice.getText().length() == 0) || (txtAddProdPrice.getText() == null)) {
            eMsg += "Please supply a sale price.\n";
        } try {
            Double.parseDouble(txtAddProdPrice.getText());
        } catch (NumberFormatException e) {
            eMsg += "Product price must be in decimal format.\n";
        }
        
        if ((txtAddProdMin.getText().length() == 0) || (txtAddProdMin.getText() == null)) {
            eMsg += "Please supply a minimum inventory level.\n";
        } else {
            try {
                Integer.parseInt(txtAddProdMin.getText());
            } catch (NumberFormatException e) {
                eMsg += "Minimum stock level must be an integer.\n";
            }
        }
        
        if ((txtAddProdMax.getText().length() == 0) || (txtAddProdMax.getText() == null)) {
            eMsg += "Please supply a maximum inventory level.\n";
        } else {
            try {
                Integer.parseInt(txtAddProdMax.getText());
            } catch (NumberFormatException e) {
                eMsg += "Maximum stock level must be an integer.\n";
            }
        }
        
        if (addedParts.isEmpty()) {
            eMsg += "Please add constituent parts to the product.\n";
        }
        
        double totalCostofParts = 0.0;
        for (Part part : addedParts) {
            totalCostofParts += part.getPrice();
        }
        
        if (Double.parseDouble(txtAddProdPrice.getText()) < totalCostofParts) {
            eMsg += "Price of product must be greater than sum of constituent parts.\n";
        }
        
        if (Integer.parseInt(txtAddProdMin.getText()) < 0) {
            eMsg += "Minimum stock level cannot be less than 0.\n";
        }
        
        if (Integer.parseInt(txtAddProdMax.getText()) <= 0) {
            eMsg += "Maxmimum stock level cannot be 0 or less.\n";
        }
        
        if (Integer.parseInt(txtAddProdMax.getText()) < Integer.parseInt(txtAddProdMin.getText())) {
            eMsg += "Maximum stock level cannot be less than Minimum stock level.\n";
        }
        
        if (Integer.parseInt(txtAddProdInStock.getText()) < Integer.parseInt(txtAddProdMin.getText())) {
            eMsg += "Current stock level cannot be below Minimum stock level.\n";
        }
        
        if (Integer.parseInt(txtAddProdInStock.getText()) > Integer.parseInt(txtAddProdMax.getText())) {
            eMsg += "Current stock level cannot be above Maximum stock level.\n";
        }
        
        if (eMsg.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Product Specifications");
            alert.setHeaderText(null);
            alert.setContentText(eMsg);
            alert.showAndWait();
            eMsg = "";
            return false;
        } else {
            return true;
        }
    }

    public void setMainApp(MainApp mainApp) {
       this.mainApp = mainApp;
    }

    public boolean isOKClicked() {
        return okClicked;
    }
    
}
