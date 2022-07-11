package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.*;

import java.io.IOException;

/**
 * The controller for the ModifyProductForm
 *
 * @author Tabish Abbasi
 */
public class ModifyPartController {
    @FXML
    private RadioButton houseRadioBtn;
    @FXML
    private RadioButton outRadioBtn;
    @FXML
    private TextField idTextBox;
    @FXML
    private TextField nameTextBox;
    @FXML
    private TextField invTextBox;
    @FXML
    private TextField priceTextBox;
    @FXML
    private TextField minTextBox;
    @FXML
    private TextField maxTextBox;
    @FXML
    private Text machineCompanyLabel;
    @FXML
    private TextField machineCompanyTextBox;

    /**
     * Retrieves the part that was sent from the main form
     * to be modified.
     *
     * @param part the part to be modified
     */
    public void receivePart(Part part){
        idTextBox.setText(String.valueOf(part.getId()));
        nameTextBox.setText(part.getName());
        invTextBox.setText(String.valueOf(part.getStock()));
        priceTextBox.setText(String.valueOf(part.getPrice()));
        minTextBox.setText(String.valueOf(part.getMin()));
        maxTextBox.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse){
            houseRadioBtn.setSelected(true);
            machineCompanyLabel.setText("Machine ID");
            machineCompanyTextBox.setText(String.valueOf(((InHouse) part).getMachineId()));
        }else {
            outRadioBtn.setSelected(true);
            machineCompanyLabel.setText("Company Name");
            machineCompanyTextBox.setText(((OutSourced) part).getCompanyName());
        }

    }

    /**
     * Disables the ID text box.
     */
    @FXML
    public void initialize(){
        idTextBox.setDisable(true);
    }

    /**
     * Changes the label to Machine ID if the In-House
     * radio button is clicked.
     */
    @FXML
    public void inHouseMenu(){
        machineCompanyLabel.setText("Machine ID");
    }

    /**
     * Changes the label to Company Name if the Outsource
     * radio button is clicked.
     */
    @FXML
    public void outSourceMenu(){
        machineCompanyLabel.setText("Company Name");
    }

    /**
     * Replaces the old part in the list of all parts
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
    public void onSave(ActionEvent event) throws  IOException{
        if(nameTextBox.getText().equals("") || invTextBox.getText().equals("") ||
                priceTextBox.getText().equals("") || minTextBox.getText().equals("") ||
                maxTextBox.getText().equals("") || machineCompanyTextBox.getText().equals("")){
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
                } else {
                    Part part;
                    if(houseRadioBtn.isSelected()){
                        int machineId = Integer.parseInt(machineCompanyTextBox.getText());
                        part = new InHouse(id, name, price, stock, min, max, machineId);
                    }
                    else{
                        String companyName = machineCompanyTextBox.getText();
                        part = new OutSourced(id, name, price, stock, min, max, companyName);
                    }
                    Inventory.updatePart(Inventory.getAllParts().indexOf(Inventory.lookupPart(id)), part);
                    backToMainForm(event);
                }
            } catch (NumberFormatException e){
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
    public void backToMainForm(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/MainForm.fxml"))), 1250, 470));
        stage.setResizable(false);
        stage.show();
    }
}
