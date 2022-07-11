package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.AlertGenerator;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.util.Optional;

/**
 * The controller for the main menu of the application
 *
 * @author Tabish Abbasi
 */
public class MainFormController {
    private Stage stage;
    @FXML
    private TextField partSearchBar;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField productSearchBar;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private void initialize(){
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Helper method for searchForPart Method.
     * <p>
     * Attempts to find a part by it's id by comparing it to
     * the integer that is provided.
     * </p>
     * <p>
     * If the part is not found an alert is generated.
     * </p>
     *
     * @param id the id to search
     * @return the part with the matching id if it exists
     * otherwise returns the list of all parts
     */
    public ObservableList<Part> partSearchById(int id){
        if(!Inventory.getAllFilteredParts().isEmpty()){
            Inventory.getAllFilteredParts().clear();
        }

        for(Part currentPart : Inventory.getAllParts()){
            if(id == currentPart.getId()){
                Inventory.getAllFilteredParts().add(currentPart);
                break;
            }
        }

        if(Inventory.getAllFilteredParts().isEmpty()){
            AlertGenerator.noResultAlert();
            return Inventory.getAllParts();
        }

        return Inventory.getAllFilteredParts();
    }

    /**
     * Helper method for searchForPart.
     * <p>
     * Attempts to find all the parts which names have a matching
     * case with the input string.
     * </p>
     * <p>
     * If the parts are not found an alert is generated.
     * </p>
     *
     * @param name the name or piece of the name of the part to be found
     * @return the list of parts that contain the name or a piece of it any are found
     * if no parts are found, returns the list of all parts
     */
    public ObservableList<Part> partSearchByName(String name){
        if(!Inventory.getAllFilteredParts().isEmpty()){
            Inventory.getAllFilteredParts().clear();
        }

        for(Part currentPart : Inventory.getAllParts()){
            if(currentPart.getName().contains(name)){
                Inventory.getAllFilteredParts().add(currentPart);
            }
        }

        if(Inventory.getAllFilteredParts().isEmpty()){
            AlertGenerator.noResultAlert();
            return Inventory.getAllParts();
        }

        return Inventory.getAllFilteredParts();
    }

    /**
     * Attempts to search for any parts based on the value
     * entered in the search bar.
     */
    @FXML
    public void searchForPart(){
        if(partSearchBar.getText().isEmpty()){
            partTable.setItems(Inventory.getAllParts());
        }
        else {
            try {
                int id = Integer.parseInt(partSearchBar.getText());
                partTable.setItems(partSearchById(id));
            } catch (NumberFormatException e) {
                partTable.setItems(partSearchByName(partSearchBar.getText()));
            }
        }
    }

    /**
     * Changes the current scene by loading AddPartForm.fxml.
     *
     * @param event the Add button in the Part Table being clicked
     * @throws IOException if AddPartForm.fxml is not found
     */
    @FXML
    public void addPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AddPartForm.fxml"))), 550, 400));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Changes the current scene by loading ModifyPartForm.fxml
     * and populates the fields with the values contained in the
     * currently selected part.
     * <p>
     * If a part is not selected an alert is generated.
     * </p>
     *
     * RUNTIME ERROR was not populating the fields with any information.
     * I was loading the FXML file twice and had to remove a method I was
     * using to load all the fxml files.
     *
     * @param event the Modify button in the Part Table being clicked
     * @throws IOException if ModifyPartForm.fxml is not found
     */
    @FXML
    public void modifyPart(ActionEvent event) throws IOException {
        if(!partTable.getSelectionModel().isEmpty()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartController MPcontroller = loader.getController();
            MPcontroller.receivePart(partTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }else {
            AlertGenerator.partNotSelectedAlert();
        }
    }

    /**
     * Deletes the currently selected part in the Part Table
     * after obtaining confirmation.
     * <p>
     * If a part is not selected an alert is generated.
     * </p>
     */
    @FXML
    public void deletePart(){
        if(!partTable.getSelectionModel().isEmpty()){
            Part removePart = partTable.getSelectionModel().getSelectedItem();
            Optional<ButtonType> choice = AlertGenerator.deletePartAlert(removePart);

            if(choice.isPresent() && choice.get() == ButtonType.OK){
                Inventory.deletePart(removePart);
                partTable.setItems(Inventory.getAllParts());
            }
        }else {
            AlertGenerator.partNotSelectedAlert();
        }
    }

    /**
     * Helper method for searchForProduct.
     * <p>
     * Attempts to find a product by it's id by comparing it to
     * the integer that is provided.
     * </p>
     * <p>
     * If the product is not found an alert is generated.
     * </p>
     *
     * @param id the name or piece of the name of the product to be found
     * @return the product with the matching id if it exists
     * otherwise returns the list of all products
     */
    public ObservableList<Product> productSearchById(int id){
        if(!Inventory.getAllFilteredProducts().isEmpty()){
            Inventory.getAllFilteredProducts().clear();
        }

        for(Product currentProduct : Inventory.getAllProducts()){
            if(id == currentProduct.getId()){
                Inventory.getAllFilteredProducts().add(currentProduct);
                break;
            }
        }

        if(Inventory.getAllFilteredProducts().isEmpty()){
            AlertGenerator.noResultAlert();
            return Inventory.getAllProducts();
        }

        return Inventory.getAllFilteredProducts();
    }

    /**
     * Helper method for searchForProduct.
     * <p>
     * Attempts to find all the products which names have a matching
     * case with the input string.
     * </p>
     * <p>
     * If the products are not found an alert is generated.
     * </p>
     *
     * @param name the name or piece of the name of the product to be found
     * @return the list of products that contain the name or a piece of it any are found
     * if no products are found, returns the list of all products
     */
    public ObservableList<Product> productSearchByName(String name) {
        if (!Inventory.getAllFilteredProducts().isEmpty()) {
            Inventory.getAllFilteredProducts().clear();
        }

        for (Product currentProduct : Inventory.getAllProducts()) {
            if (currentProduct.getName().contains(name)) {
                Inventory.getAllFilteredProducts().add(currentProduct);
            }
        }

        if (Inventory.getAllFilteredProducts().isEmpty()) {
            AlertGenerator.noResultAlert();
            return Inventory.getAllProducts();
        }

        return Inventory.getAllFilteredProducts();
    }

    /**
     * Attempts to search for any parts based on the value
     * entered in the search bar.
     */
    @FXML
    public void searchForProduct(){
        if(productSearchBar.getText().isEmpty()){
            productTable.setItems(Inventory.getAllProducts());
        }
        else {
            try {
                int id = Integer.parseInt(productSearchBar.getText());
                productTable.setItems(productSearchById(id));
            } catch (NumberFormatException e) {
                productTable.setItems(productSearchByName(productSearchBar.getText()));
            }
        }
    }

    /**
     * Changes the current scene by loading AddProductForm.fxml.
     *
     * @param event the Add button in the Products Table being clicked
     * @throws IOException if AddProductForm.fxml is not found
     */
    @FXML
    public void addProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AddProductForm.fxml"))), 1100, 600));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Changes the current scene by loading ModifyProductForm.fxml
     * and populates the fields with the values contained in the
     * currently selected product.
     * <p>
     * If a product is not selected an alert is generated.
     * </p>
     *
     * @param event the Modify button in the Product Table being clicked
     * @throws IOException if ModifProductForm.fxml is not found
     */
    @FXML
    public void modifyProduct(ActionEvent event) throws IOException {
        if(!productTable.getSelectionModel().isEmpty()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductController MPcontroller = loader.getController();
            MPcontroller.receiveProduct(productTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }else {
            AlertGenerator.productNotSelectedAlert();
        }
    }

    /**
     * Deletes the currently selected product in the Product Table
     * after obtaining confirmation.
     * <p>
     * If a product is not selected or a product contains parts an
     * alert is generated.
     * </p>
     */
    @FXML
    public void deleteProduct(){
        if(!productTable.getSelectionModel().isEmpty()){
            if(productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
                Product removeProduct = productTable.getSelectionModel().getSelectedItem();
                Optional<ButtonType> choice = AlertGenerator.deleteProductAlert(removeProduct);
                if(choice.isPresent() && choice.get() == ButtonType.OK){
                    Inventory.deleteProduct(removeProduct);
                    productTable.setItems(Inventory.getAllProducts());
                }
            }else {
                AlertGenerator.productHasPartsAlert();
            }
        }else {
            AlertGenerator.productNotSelectedAlert();
        }
    }

    /**
     * Closes the main window and terminates the application.
     * @param event the exit button being clicked
     */
    @FXML
    public void exitPressed(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
