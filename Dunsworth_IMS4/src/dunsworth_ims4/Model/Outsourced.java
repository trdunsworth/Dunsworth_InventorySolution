/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tony.dunsworth
 */
public class Outsourced extends Part {
    private final StringProperty companyName;
    
    public Outsourced() {
        this(null, 0, null, 0, 9999, 0, 0);
    }

    public Outsourced(String companyName, int partID, String name, double price, int inStock, int min, int max) {
        super(partID, name, price, inStock, min, max);
        this.companyName = new SimpleStringProperty(companyName);
    }
    
    public String getCompanyName() {
        return companyName.get();
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    public StringProperty companyNameProperty() {
        return companyName;
    }
    
    @Override
    public boolean isInhouse() {
        return false;
    }
}
