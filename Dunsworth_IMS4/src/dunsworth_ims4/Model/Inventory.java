/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunsworth_ims4.Model;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author tony.dunsworth
 */
public class Inventory {
    private static ObservableList<Product> products = observableArrayList();
    private static ObservableList<Part> allParts = observableArrayList();
    
    public static void addProduct(Product product) {
        products.add(product);
    }
    
    public boolean removeProduct(int position) throws Exception {
        Product product = products.get(position);
        
        if (product.getAssociatedParts().size() > 0) {
            throw new Exception("This product contains parts, all parts much be removed to remove the product.");
        }
        
        return products.remove(lookupProduct(position));
    }
    
    public Product lookupProduct(int productID) {
        Product requestedProduct = null;
        
        for (Product p : products) {
            if (p.getProductID() == productID) {
                requestedProduct = new Product();
                
                requestedProduct.setProductID(p.getProductID());
                requestedProduct.setName(p.getName());
                requestedProduct.setPrice(p.getPrice());
                requestedProduct.setInStock(p.getInStock());
                requestedProduct.setMin(p.getMin());
                requestedProduct.setMax(p.getMax());
                requestedProduct.setAssociatedParts(p.getAssociatedParts());
            }
        }
        
        return requestedProduct;
    }
    
    public void updateProduct(int position, Product product) {
        products.set(position, product);
    }
    
    public static void addPart(Part part) {
        allParts.add(part);
    }
    
    public static void deletePart(Part part) {
        allParts.remove(part);
    }
    
    public static Part lookupPart(int partID) {
        Part requestedPart = null;
        
        for (Part p : allParts) {
            if (p.getPartID() == partID) {
                if (p.isInhouse()) {
                    requestedPart = new Inhouse();
                    ((Inhouse) requestedPart).setMachineID(((Inhouse) p).getMachineID());
                } else {
                    requestedPart = new Outsourced();
                    ((Outsourced) requestedPart).setCompanyName(((Outsourced) p).getCompanyName());
                }
                
                requestedPart.setPartID(p.getPartID());
                requestedPart.setName(p.getName());
                requestedPart.setPrice(p.getPrice());
                requestedPart.setInStock(p.getInStock());
                requestedPart.setMin(p.getMin());
                requestedPart.setMax(p.getMax());
            }
        }
        return requestedPart;
    }
    
    public void updatePart(int position, Part part) {
        allParts.set(position, part);
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getProducts() {
        return products;
    }

}
