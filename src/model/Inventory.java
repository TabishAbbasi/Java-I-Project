package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates the Inventory that contains the list of
 * all parts and products.
 *
 * @author Tabish Abbasi
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart the part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     *
     * @param partId the partId to lookup
     * @return the part if it's found, null if it is not
     */
    public static Part lookupPart(int partId){
        Part matchingPart = null;
        if(!allParts.isEmpty()){
            for(Part currentPart : allParts){
                if(currentPart.getId() == partId){
                    matchingPart = currentPart;
                    break;
                }
            }
        }

        return matchingPart;
    }

    /**
     *
     * @param productId the productId to lookup
     * @return the product if it is found, null if it is not
     */
    public static Product lookupProduct(int productId){
        Product matchingProduct = null;
        if(!allProducts.isEmpty()){
            for(Product currentProduct : allProducts){
                if(currentProduct.getId() == productId){
                    matchingProduct = currentProduct;
                    break;
                }
            }
        }

        return matchingProduct;
    }

    /**
     *
     * @param partName the name of the part(s) to lookup
     * @return the list of parts that matched the search string
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> SearchedParts = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                SearchedParts.add(part);
            }
        }
        return SearchedParts;
    }


    /**
     *
     * @param productName the name of the product(s) to lookup
     * @return the list of products that matched the search string
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> SearchedProducts = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                SearchedProducts.add(product);
            }
        }
        return SearchedProducts;
    }

    /**
     *
     * @param index the index of the part
     * @param updatedPart the updated part
     */
    public static void updatePart(int index, Part updatedPart){
        allParts.set(index, updatedPart);
    }

    /**
     *
     * @param index the index of the product
     * @param updatedProduct the updated product
     */
    public static void updateProduct(int index, Product updatedProduct){
        allProducts.set(index, updatedProduct);
    }

    /**
     *
     * @param selectedPart the part to delete
     * @return true if the part was deleted, false if the part was not found
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     *
     * @param selectedProduct the product to delete
     * @return true if the part was deleted, false if the product was not found
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     *
     * @return the list of allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return the list of filteredParts
     */
    public static ObservableList<Part> getAllFilteredParts(){
        return filteredParts;
    }

    /**
     *
     * @return the list of allProducts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     *
     * @return the list of filteredProducts
     */
    public static ObservableList<Product> getAllFilteredProducts(){
        return filteredProducts;
    }
}
