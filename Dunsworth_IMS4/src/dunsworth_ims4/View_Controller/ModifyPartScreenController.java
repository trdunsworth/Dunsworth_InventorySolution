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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trdunsworth
 */
public class ModifyPartScreenController {
    
    private MainApp mainApp;
    
    @FXML private Label lblModPartTitle;
    @FXML private Label lblModPartMax;
    @FXML private Label lblModPartMin;
    @FXML private Label lblModPartID;
    @FXML private Label lblModPartName;
    @FXML private Label lblModPartInStock;
    @FXML private Label lblModPartPrice;
    @FXML private Label lblInOrOut;

    @FXML private TextField txtModPartMax;
    @FXML private TextField txtModPartMin;
    @FXML private TextField txtModPartID;
    @FXML private TextField txtModPartName;
    @FXML private TextField txtModPartInStock;
    @FXML private TextField txtModPartPrice;
    @FXML private TextField txtInOrOut;

    @FXML private RadioButton rdoInhouse;
    @FXML private RadioButton rdoOutsourced;

    @FXML private Button btnModPartCancel;
    @FXML private Button btnModPartSave;
    
    public boolean isInhouse;
    
    private boolean okClicked = false;
    
    public Stage setModifyPartStage;
    public Scene modPartScreenScene;
    private Part part;

    
    @FXML
    private void handleInhouse() {
        isInhouse = true;
        lblInOrOut.setText("Machine ID");
        rdoInhouse.setSelected(true);
        rdoOutsourced.setSelected(false);
    }
    
    @FXML
    private void handleOutsourced() {
        isInhouse = false;
        lblInOrOut.setText("Company Name");
        rdoInhouse.setSelected(false);
        rdoOutsourced.setSelected(true);
    }
    
    @FXML
    private void savePart() {
        if (isValid()) {
            if (part instanceof Inhouse) {
                part.setPartID(Integer.parseInt(txtModPartID.getText()));
                part.setName(txtModPartName.getText());
                part.setInStock(Integer.parseInt(txtModPartInStock.getText()));
                part.setPrice(Double.parseDouble(txtModPartPrice.getText()));
                part.setMin(Integer.parseInt(txtModPartMin.getText()));
                part.setMax(Integer.parseInt(txtModPartMax.getText()));
                (((Inhouse) part)).setMachineID(Integer.parseInt(txtInOrOut.getText()));
            } else {
                part.setPartID(Integer.parseInt(txtModPartID.getText()));
                part.setName(txtModPartName.getText());
                part.setInStock(Integer.parseInt(txtModPartInStock.getText()));
                part.setPrice(Double.parseDouble(txtModPartPrice.getText()));
                part.setMin(Integer.parseInt(txtModPartMin.getText()));
                part.setMax(Integer.parseInt(txtModPartMax.getText()));
                (((Outsourced) part)).setCompanyName(txtInOrOut.getText());
            }           
            setModifyPartStage.close();
            
            okClicked = true;
        }
    }
    
    @FXML
    private void handleCancel() {
        setModifyPartStage.close();
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        // TODO
    }    

   public void setMainApp(MainApp mainApp) {
       this.mainApp = mainApp;
    }

    public boolean isOKClicked() {
        return okClicked;
    }

    public void setModifyPartStage(Stage setModifyPartStage) {
        this.setModifyPartStage = setModifyPartStage;
    }
    
    public void setPart(Inhouse part) {
        handleInhouse();
        txtInOrOut.setText(Integer.toString(part.getMachineID()));
    }
    
    public void setPart(Outsourced part) {
        handleOutsourced();
        txtInOrOut.setText(part.getCompanyName());
    }
    
    public void setPartsInCommon(Part part) {
        this.part = part;
        
        txtModPartID.setText(Integer.toString(part.getPartID()));
        txtModPartName.setText(part.getName());
        txtModPartInStock.setText(Integer.toString(part.getInStock()));
        txtModPartPrice.setText(Double.toString(part.getPrice()));
        txtModPartMin.setText(Integer.toString(part.getMin()));
        txtModPartMax.setText(Integer.toString(part.getMax()));
        
        if (part instanceof Inhouse) {
            setPart(((Inhouse) part));
        } else {
            setPart(((Outsourced) part));
        }
    }
    
    private boolean isValid() {
       String eMsg = "";
       
       if ((txtModPartName.getText().length() == 0) || (txtModPartName.getText() == null)) {
           eMsg += "Please supply a name.\n";
       }
       
       if ((txtModPartInStock.getText().length() == 0) || (txtModPartInStock.getText() == null)) {
           eMsg += "Please supply an opening inventory level.\n";
       } else {
           try {
               Integer.parseInt(txtModPartInStock.getText());
           } catch (NumberFormatException e) {
               eMsg += "Current stock on hand must be an integer.\n";
           }
       }
       
       if ((txtModPartPrice.getText().length() == 0) || (txtModPartPrice.getText() == null)) {
           eMsg += "Please supply a price.\n";
       } else {
           try {
               Double.parseDouble(txtModPartPrice.getText());
           } catch (NumberFormatException e) {
               eMsg += "Part price must be in a decimal format.\n";
           }
       }
       
       if ((txtModPartMin.getText().length() == 0) || (txtModPartMin.getText() == null)) {
           eMsg += "Please supply a minimum inventory level.\n";
       } else {
           try {
               Integer.parseInt(txtModPartMin.getText());
           } catch (NumberFormatException e) {
               eMsg += "Minimum stock level must be an integer.\n";
           }
       }
       
       if ((txtModPartMax.getText().length() == 0) || (txtModPartMax.getText() == null)) {
           eMsg += "Please supply a maximum inventory level.\n";
       } else {
           try {
               Integer.parseInt(txtModPartMax.getText());
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
       
       if (Integer.parseInt(txtModPartMin.getText()) < 0) {
           eMsg += "Part minimum cannot be below 0.\n";
       }
       
       if (Integer.parseInt(txtModPartMax.getText()) <= 0) {
           eMsg += "Part maximum cannot be 0 or less.\n";
       }
       
       if (Integer.parseInt(txtModPartMax.getText()) < Integer.parseInt(txtModPartMin.getText())) {
           eMsg += "Max inventory level cannot be less than Min inventory level.\n";
       }
       
       if (Integer.parseInt(txtModPartInStock.getText()) < Integer.parseInt(txtModPartMin.getText())) {
           eMsg += "Current inventory level cannot be less than Min inventory level.\n";
       }
       
       if (Integer.parseInt(txtModPartInStock.getText()) > Integer.parseInt(txtModPartMax.getText())) {
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
    
}
