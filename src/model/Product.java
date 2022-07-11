package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a product with a list of associated parts.
 *
 * @author Tabish Abbasi
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the minimum
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @param min the minimum to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the maximum
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max the maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part the part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart the part to delete
     * @return true if the part was deleted, false if the part was not found
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     *
     * @return the list of associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
