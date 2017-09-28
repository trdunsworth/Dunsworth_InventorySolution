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
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trdunsworth
 */
public class AddPartScreenController {
    
    private MainApp mainApp;
    
    @FXML private TextField txtAddPartMax;
    @FXML private TextField txtAddPartMin;
    @FXML private TextField txtAddPartID;
    @FXML private TextField txtAddPartName;
    @FXML private TextField txtAddPartInStock;
    @FXML private TextField txtAddPartPrice;
    @FXML private TextField txtInOrOut;
    @FXML private Label lblInOrOut;
    
    private boolean isInhouse = true;
    
    public Scene scene;
    public Stage addPartScreenStage;
    private boolean okClicked = false;
    
    public void setStage(Stage addPartScreenStage) {
        this.addPartScreenStage = addPartScreenStage;
    }
    
    @FXML
    private void handleInhouse() {
        isInhouse = true;
        lblInOrOut.setText("Machine ID");
    }
    
    @FXML
    private void handleOutsourced() {
        isInhouse = false;
        lblInOrOut.setText("Company Name");
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        
    }

    @FXML
    private void handleAddPart() {
        if (isValid()) {
            if (isInhouse) {
                Inhouse inhouse = new Inhouse();
                setCommonFields(inhouse);
                inhouse.setMachineID((Integer.parseInt(txtInOrOut.getText())));
                Inventory.addPart(inhouse);
                okClicked = true;
                addPartScreenStage.close();
            } else {
                Outsourced outsourced = new Outsourced();
                setCommonFields(outsourced);
                outsourced.setCompanyName(txtInOrOut.getText());
                Inventory.addPart(outsourced);
                okClicked = true;
                addPartScreenStage.close();
            }
        }
    }
    
    private void setCommonFields(Part part) {
        part.setPartID(Part.getIDNumber());
        part.setName(txtAddPartName.getText());
        part.setInStock(Integer.parseInt(txtAddPartInStock.getText()));
        part.setPrice(Double.parseDouble(txtAddPartPrice.getText()));
        part.setMin(Integer.parseInt(txtAddPartMin.getText()));
        part.setMax(Integer.parseInt(txtAddPartMax.getText()));
    }

    @FXML
    private void handleCancel() {
        addPartScreenStage.close();
    }

    public void setMainApp(MainApp mainApp) {
       this.mainApp = mainApp;
    }

    
    private boolean isValid() {
       String eMsg = "";
       
       if ((txtAddPartName.getText().length() == 0) || (txtAddPartName.getText() == null)) {
           eMsg += "Please supply a name.\n";
       }
       
       if ((txtAddPartInStock.getText().length() == 0) || (txtAddPartInStock.getText() == null)) {
           eMsg += "Please supply an opening inventory level.\n";
       } else {
           try {
               Integer.parseInt(txtAddPartInStock.getText());
           } catch (NumberFormatException e) {
               eMsg += "Invetory levels must be in integer form.\n";
           }
       }
       
       if ((txtAddPartPrice.getText().length() == 0) || (txtAddPartPrice.getText() == null)) {
           eMsg += "Please supply a price.\n";
       } else {
           try {
               Double.parseDouble(txtAddPartPrice.getText());
           } catch (NumberFormatException e) {
               eMsg += "Price must be entered in a decimal format.\n";
           }
       }
       
       if ((txtAddPartMin.getText().length() == 0) || (txtAddPartMin.getText() == null)) {
           eMsg += "Please supply a minimum inventory level.\n";
       } else {
           try {
               Integer.parseInt(txtAddPartMin.getText());
           } catch (NumberFormatException e) {
               eMsg += "Minimum stock level must be an integer.\n";
           }
       }
       
       if ((txtAddPartMax.getText().length() == 0 ) || (txtAddPartMax.getText() == null)) {
           eMsg += "Please supply a maximum inventory level.\n";
       } else {
           try {
               Integer.parseInt(txtAddPartMax.getText());
           } catch (NumberFormatException e) {
               eMsg += "Maximum stock level must be an integer.\n";
           }
       }
       
       if ((txtInOrOut.getText().length() == 0) || (txtInOrOut.getText() == null)) {
           if (isInhouse) {
               eMsg += "Please supply a machine ID.\n";
           } else {
               eMsg += "Please supply the supplier name.\n";
           }
       }
       
       if ((Integer.parseInt(txtAddPartMin.getText()) < 0) || (txtAddPartName.getText() == null)) {
           eMsg += "Part minimum cannot be below 0.\n";
       }
       
       if ((Integer.parseInt(txtAddPartMax.getText()) <= 0) || (txtAddPartName.getText() == null)) {
           eMsg += "Part maximum cannot be 0 or less.\n";
       }
       
       if (Integer.parseInt(txtAddPartMax.getText()) < Integer.parseInt(txtAddPartMin.getText())) {
           eMsg += "Max inventory level cannot be less than Min inventory level.\n";
       }
       
       if (Integer.parseInt(txtAddPartInStock.getText()) < Integer.parseInt(txtAddPartMin.getText())) {
           eMsg += "Current inventory level cannot be less than Min inventory level.\n";
       }
       
       if (Integer.parseInt(txtAddPartInStock.getText()) > Integer.parseInt(txtAddPartMax.getText())) {
           eMsg += "Current inventory level cannot be greater than Max Inventory level.\n";
       }
       
       if (isInhouse) {
           try {
               Integer.parseInt(txtInOrOut.getText());
           } catch (NumberFormatException e) {
               eMsg += "Machine ID must be an integer.\n";
           }
       }
       
       if (eMsg.length() > 0) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Invalid Part Specifications");
           alert.setHeaderText(null);
           alert.setContentText(eMsg);
           alert.showAndWait();
           eMsg = "";
           return false;
       } else {
           return true;
       }
    }

    public boolean isClicked() {
        return okClicked;
    }
}
