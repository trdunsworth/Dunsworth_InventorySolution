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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trdunsworth
 */
public class ModifyProductScreenController {
    
    private MainApp mainApp;
    
    @FXML private TextField txtModProdMax;
    @FXML private TextField txtModProdMin;
    @FXML private TextField txtModProdID;
    @FXML private TextField txtModProdName;
    @FXML private TextField txtModProdInStock;
    @FXML private TextField txtModProdPrice;
    @FXML private TextField txtModProdSearch;

    @FXML private TableView<Part> tblModProdAddPart;
    @FXML private TableColumn<Part, Number> colModProdAPPartID;
    @FXML private TableColumn<Part, String> colModProdAPName;
    @FXML private TableColumn<Part, Number> colModProdAPInStock;
    @FXML private TableColumn<Part, Number> colModProdAPPrice;

    @FXML private TableView<Part> tblModProdDeletePart;
    @FXML private TableColumn<Part, Number> colModProdDPPartID;
    @FXML private TableColumn<Part, String> colModProdDPName;
    @FXML private TableColumn<Part, Number> colModProdDPInStock;
    @FXML private TableColumn<Part, Number> colModProdDPPrice;
    
    private ObservableList<Part> addedParts = FXCollections.observableArrayList();
    private boolean okClicked = false;
    public Scene scene;
    public Stage modifyProductScreenStage;
    private Product product;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        colModProdAPPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        colModProdAPName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colModProdAPInStock.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        colModProdAPPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        
        colModProdDPPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        colModProdDPName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colModProdDPInStock.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        colModProdDPPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        
        tblModProdAddPart.setItems(Inventory.getAllParts());
    }
    
    @FXML
    private void handleSearchParts() {
        String searchItem = txtModProdSearch.getText();
       boolean found = false;
       if ((searchItem.length() == 0) || (searchItem == null)) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("No Part Given");
           alert.setHeaderText(null);
           alert.setContentText("Please supply a parameter for part search.");
           alert.showAndWait();
        } else {
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
    }
    
    @FXML
    private void handleSave() {
        if (isValid()) {
            product.setName(txtModProdName.getText());
            product.setInStock(Integer.parseInt(txtModProdInStock.getText()));
            product.setPrice(Double.parseDouble(txtModProdPrice.getText()));
            product.setMin(Integer.parseInt(txtModProdMin.getText()));
            product.setMax(Integer.parseInt(txtModProdMax.getText()));
            product.setAssociatedParts(addedParts);
            
            modifyProductScreenStage.close();
            
            okClicked = true;
        }
    }
    
    @FXML
    private void handleAddPart() {
        Part partToAdd = tblModProdAddPart.getSelectionModel().getSelectedItem();
        
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
        Part partToRemove = tblModProdDeletePart.getSelectionModel().getSelectedItem();
        
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
        modifyProductScreenStage.close();
    }

    public void setMainApp(MainApp mainApp) {
       this.mainApp = mainApp;
    }

    public boolean isOKClicked() {
        return okClicked;
    }

    public void setModifyProductStage(Stage modifyProductScreenStage) {
        this.modifyProductScreenStage = modifyProductScreenStage;
    }
    
    public void setPartsList(Inventory inventory) {
        tblModProdAddPart.setItems(Inventory.getAllParts());
    }
    
    public void showAssociatedParts() {
        tblModProdDeletePart.setItems(addedParts);
    }

    public void setProduct(Product product) {
        this.product = product;
        addedParts = product.getAssociatedParts();
        txtModProdID.setText(Integer.toString(product.getProductID()));
        txtModProdName.setText(product.getName());
        txtModProdPrice.setText(Double.toString(product.getPrice()));
        txtModProdInStock.setText(Integer.toString(product.getInStock()));
        txtModProdMin.setText(Integer.toString(product.getMin()));
        txtModProdMax.setText(Integer.toString(product.getMax()));
        showAssociatedParts();
    }
    
    private boolean isValid() {
        String eMsg = "";
        
        if ((txtModProdName.getText().length() == 0) || (txtModProdName.getText() == null)) {
            eMsg += "Please supply a name.\n";
        }
        
        if ((txtModProdInStock.getText().length() == 0) || (txtModProdInStock.getText() == null)) {
            eMsg += "Please supply a current inventory level.\n";
        } else {
            try {
                Integer.parseInt(txtModProdInStock.getText());
            } catch (NumberFormatException e) {
                eMsg += "Current stock levels must be an integer.\n";
            }
        }
        
        if ((txtModProdPrice.getText().length() == 0) || (txtModProdPrice.getText() == null)) {
            eMsg += "Please supply a sale price.\n";
        } else {
            try {
                Double.parseDouble(txtModProdPrice.getText());
            } catch (NumberFormatException e) {
                eMsg += "Product price must be in decimal format.\n";
            }
        }
        
        if ((txtModProdMin.getText().length() == 0) || (txtModProdMin.getText() == null)) {
            eMsg += "Please supply a minimum inventory level.\n";
        } else {
            try {
                Integer.parseInt(txtModProdMin.getText());
            } catch (NumberFormatException e) {
                eMsg += "Minimum stock level must be an integer.\n";
            }
        }
        
        if ((txtModProdMax.getText().length() == 0) || (txtModProdMax.getText() == null)) {
            eMsg += "Please supply a maximum inventory level.\n";
        } else {
            try {
                Integer.parseInt(txtModProdMax.getText());
            } catch (NumberFormatException e) {
                eMsg += "Maximum stock level must be an integer.\n";
            }
        }
        
        if (addedParts.isEmpty()) {
            eMsg += "Please add constituent parts to the product.\n";
        } else {
        }
        
        double totalCostofParts = 0.0;
        for (Part part : addedParts) {
            totalCostofParts += part.getPrice();
        }
        
        if (Double.parseDouble(txtModProdPrice.getText()) < totalCostofParts) {
            eMsg += "Price of product must be greater than sum of constituent parts.\n";
        }
        
        if (Integer.parseInt(txtModProdMin.getText()) < 0) {
            eMsg += "Minimum stock level cannot be less than 0.\n";
        }
        
        if (Integer.parseInt(txtModProdMax.getText()) <= 0) {
            eMsg += "Maxmimum stock level cannot be 0 or less.\n";
        }
        
        if (Integer.parseInt(txtModProdMax.getText()) < Integer.parseInt(txtModProdMin.getText())) {
            eMsg += "Maximum stock level cannot be less than Minimum stock level.\n";
        }
        
        if (Integer.parseInt(txtModProdInStock.getText()) < Integer.parseInt(txtModProdMin.getText())) {
            eMsg += "Current stock level cannot be below Minimum stock level.\n";
        }
        
        if (Integer.parseInt(txtModProdInStock.getText()) > Integer.parseInt(txtModProdMax.getText())) {
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
    
}
