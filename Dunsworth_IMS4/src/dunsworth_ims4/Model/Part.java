/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.Model;

import dunsworth_ims4.MainApp;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tony.dunsworth
 */
public abstract class Part {
    private IntegerProperty partID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;
    
    private MainApp mainApp;
    
    public Part(int partID, String name, double price, int inStock, int min, int max) {
        this.partID = new SimpleIntegerProperty(partID);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inStock = new SimpleIntegerProperty(inStock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }
    
    public int getPartID() {
        return partID.get();
    }
    
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public IntegerProperty partIDProperty() {
        return partID;
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public double getPrice() {
        return price.get();
    }
    
    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public DoubleProperty priceProperty() {
        return price;
    }
    
    public int getInStock() {
        return inStock.get();
    }
    
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }
    
    public IntegerProperty inStockProperty() {
        return inStock;
    }
    
    public int getMin() {
        return min.get();
    }
    
    public void setMin(int min) {
        this.min.set(min);
    }
    
    public IntegerProperty minProperty() {
        return min;
    }
    
    public int getMax() {
        return max.get();
    }
    
    public void setMax(int max) {
        this.max.set(max);
    }
    
    public IntegerProperty maxProperty() {
        return max;
    }
    
    public static int getIDNumber() {
        int IDNumber = 1 + Inventory.getAllParts().size();
        return IDNumber;
    }
    
    public abstract boolean isInhouse();
}
