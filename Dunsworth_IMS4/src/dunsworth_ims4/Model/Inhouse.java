/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author tony.dunsworth
 */
public class Inhouse extends Part {
    private final IntegerProperty machineID;
    
    public Inhouse() {
        this(0, 0, null, 0, 9999, 0, 0);
    }

    public Inhouse(int machineID, int partID, String name, double price, int inStock, int min, int max) {
        super(partID, name, price, inStock, min, max);
        this.machineID = new SimpleIntegerProperty(machineID);
    }
    
    public int getMachineID() {
        return machineID.get();
    }
    
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    
    public IntegerProperty machineIDProperty() {
        return machineID;
    }
    
    @Override
    public boolean isInhouse() {
        return true;
    }
}
