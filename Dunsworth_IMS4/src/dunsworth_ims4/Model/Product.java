/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
/**
 *
 * @author tony.dunsworth
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private IntegerProperty productID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;
    
    public Product() {
        this(null, 0, null, 0.0, 9999, 0, 0);
    }
    
    public Product(ObservableList<Part> associatedParts, int productID, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productID = new SimpleIntegerProperty(productID);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inStock = new SimpleIntegerProperty(inStock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }
    
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    public int getProductID() {
        return productID.get();
    }
    
    public void setProductID(int productID) {
        this.productID.set(productID);
    }
    
    public IntegerProperty productIDProperty() {
        return productID;
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
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partID) {
        return associatedParts.remove(lookupAssociatedPart(partID));
    }
    
    public Part lookupAssociatedPart(int partID) {
        for (Part associatedPart : associatedParts) {
            if (associatedPart.getPartID() == partID) {
                return associatedPart;
            }
        }
        return null;
    }
    
    public static int getIDNumber() {
        int idNumber = 1 + Inventory.getProducts().size();
        return idNumber;
    }
}
