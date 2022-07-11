package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Generates all alerts used in the application.
 *
 * @author Tabish Abbasi
 */
public class AlertGenerator {
    /**
     * Produces an error alert containing text depending on
     * the reason for the error.
     *
     * @param reasonForError the message to display in the error
     */
    public static void generateErrorAlert(String reasonForError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(reasonForError);
        alert.showAndWait();
    }

    /**
     * Produces an error alert if values are
     * not found in a search bar.
     */
    public static void noResultAlert(){
        generateErrorAlert("No values found.");
    }

    /**
     * Produces an error alert if empty fields are found.
     */
    public static void emptyCellAlert(){
        generateErrorAlert("All fields must be filled!");
    }

    /**
     * Produces an error alert if any fields contain
     * invalid values.
     */
    public static void invalidCellAlert(){
        generateErrorAlert("Fields must contain valid values!");
    }

    /**
     * Produces an error alert if the max field is
     * smaller than the min field.
     */
    public static void minMaxAlert(){
        generateErrorAlert("Max value cannot be smaller than the min value.");
    }

    /**
     * Produces an error alert if the inventory value
     * entered does not reside between the min and max
     * values.
     */
    public static void invalidInvAlert(){
        generateErrorAlert("The inventory must be between the min and max values.");
    }

    /**
     * Produces an error alert if a part is not selected
     * in a part table and the modify or delete buttons are clicked.
     */
    public static void partNotSelectedAlert(){
        generateErrorAlert("No part selected");
    }

    /**
     * Obtains confirmation from the user when a delete
     * part action is performed.
     *
     * @param part the part to delete
     * @return the button selected by the user
     */
    public static Optional<ButtonType> deletePartAlert(Part part){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + part.getName() + "?");
        alert.setTitle("Are you Sure?");
        return alert.showAndWait();
    }

    /**
     * Produces an error alert if a product is not selected
     * in a product table and the modify and delete buttons
     * are clicked.
     */
    public static void productNotSelectedAlert(){
        generateErrorAlert("No product selected");
    }

    /**
     * Produces an error alert if the user attempts to delete
     * a product that contains parts.
     */
    public static void productHasPartsAlert(){
        generateErrorAlert("This product contains parts!");
    }

    /**
     * Obtains confirmation from the user when a remove
     * part action is performed.
     *
     * @param part the part to be removed
     * @return the button selected by the user
     */
    public static Optional<ButtonType> removePartAlert(Part part){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + part.getName() + "?");
        alert.setTitle("Are you Sure?");
        return alert.showAndWait();
    }

    /**
     * Obtains confirmation when a delete product operation
     * is performed.
     *
     * @param product the product to be deleted
     * @return the button selected by the user
     */
    public static Optional<ButtonType> deleteProductAlert(Product product){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + product.getName() + "?");
        alert.setTitle("Are you Sure?");
        return alert.showAndWait();
    }
}
