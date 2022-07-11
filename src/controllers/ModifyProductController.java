package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
 * The controller for the ModifyProductForm.
 *
 * @author Tabish Abbasi
 */
public class ModifyProductController {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private TextField idTextBox;
    @FXML
    private TextField nameTextBox;
    @FXML
    private TextField invTextBox;
    @FXML
    private TextField priceTextBox;
    @FXML
    private TextField maxTextBox;
    @FXML
    private TextField minTextBox;
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
    private TableView<Part> asPartsTable;
    @FXML
    private TableColumn<Part, Integer> asPartsIdCol;
    @FXML
    private TableColumn<Part, String> asPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> asPartsInvCol;
    @FXML
    private TableColumn<Part, Double> asPartsPriceCol;

    /**
     * Retrieves the product that was sent from the main form
     * to be modified and populates all the tables.
     *
     * @param product the product to be modified
     */
    public void receiveProduct(Product product){
        idTextBox.setText(String.valueOf(product.getId()));
        nameTextBox.setText(product.getName());
        invTextBox.setText(String.valueOf(product.getStock()));
        priceTextBox.setText(String.valueOf(product.getPrice()));
        minTextBox.setText(String.valueOf(product.getMin()));
        maxTextBox.setText(String.valueOf(product.getMax()));

        if(!product.getAllAssociatedParts().isEmpty()){
            for(Part currentPart : product.getAllAssociatedParts()){
                associatedParts.add(currentPart);
            }
        }

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        asPartsTable.setItems(associatedParts);
        asPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Disables the ID text box.
     */
    @FXML
    public void initialize(){
        idTextBox.setDisable(true);
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
    public void searchForPart() {
        if (partSearchBar.getText().isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
        } else {
            try {
                int id = Integer.parseInt(partSearchBar.getText());
                partTable.setItems(partSearchById(id));
            } catch (NumberFormatException e) {
                partTable.setItems(partSearchByName(partSearchBar.getText()));
            }
        }
    }

    /**
     * Adds a part from the table containing all parts to the
     * table containing the associated parts for the product.
     * <p>
     * Generates and alert if no part is selected.
     * </p>
     */
    @FXML
    public void addAssociatedPart(){
        if(!partTable.getSelectionModel().isEmpty()){
            Part partToAdd = partTable.getSelectionModel().getSelectedItem();
            associatedParts.add(partToAdd);
            asPartsTable.setItems(associatedParts);
        }else {
            AlertGenerator.partNotSelectedAlert();
        }
    }

    /**
     * Removes a part from the associated parts table after obtaining
     * confirmation from the user.
     * <p>
     * Generates an error if no part is selected.
     * </p>
     */
    @FXML
    public void removeAssociatedPart(){
        if(!asPartsTable.getSelectionModel().isEmpty()){
            Part removePart = asPartsTable.getSelectionModel().getSelectedItem();
            Optional<ButtonType> choice = AlertGenerator.removePartAlert(removePart);

            if(choice.isPresent() && choice.get() == ButtonType.OK){
                associatedParts.remove(removePart);
                asPartsTable.setItems(associatedParts);
            }
        }else {
            AlertGenerator.partNotSelectedAlert();
        }
    }

    /**
     * Replaces the old product in the list of all products
     * in the inventory.
     * <p>
     * Generates an alert if,
     * <ul>
     *     <li>Any of the fields are empty.</li>
     *     <li>Any fo the fields contain invalid values.</li>
     *     <li>If the min value is greater than the max value.</li>
     * </ul>
     * </p>
     * @param event the save button being clicked
     */
    @FXML
    public void onSave(ActionEvent event){
        if(nameTextBox.getText().equals("") || invTextBox.getText().equals("") ||
                priceTextBox.getText().equals("") || minTextBox.getText().equals("") ||
                maxTextBox.getText().equals("")){
            AlertGenerator.emptyCellAlert();
        } else{
            try {
                int id = Integer.parseInt(idTextBox.getText());
                String name = nameTextBox.getText();
                double price = Double.parseDouble(priceTextBox.getText());
                int stock = Integer.parseInt(invTextBox.getText());
                int min = Integer.parseInt(minTextBox.getText());
                int max = Integer.parseInt(maxTextBox.getText());

                if(min > max){
                    AlertGenerator.minMaxAlert();
                }else if (stock > max || stock < min) {
                    AlertGenerator.invalidInvAlert();
                }else {
                    Product product = new Product(id, name, price, stock, min, max);
                    if(!associatedParts.isEmpty()){
                        for(Part currentPart : associatedParts){
                            product.addAssociatedPart(currentPart);
                        }
                    }

                    Inventory.updateProduct(Inventory.getAllProducts().indexOf(Inventory.lookupProduct(id)), product);
                    backToMainForm(event);
                }
            } catch (NumberFormatException | IOException e){
                AlertGenerator.invalidCellAlert();
            }
        }
    }

    /**
     * Changes the current scene by loading MainForm.fxml.
     *
     * @param event the cancel button being clicked
     * @throws IOException if MainForm.fxml is not found
     */
    @FXML
    public void backToMainForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/MainForm.fxml"))), 1250, 470));
        stage.setResizable(false);
        stage.show();
    }
}

