/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author unai
 */
public class AddExpenseController implements Initializable {

    @FXML
    private Pane addExpensePane;
    @FXML
    private Button doneButton;
    @FXML
    private TextField nameField;
    @FXML
    private Text descriptionLabel;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Text costLabel;
    @FXML
    private TextField costField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<String> categoryMenu;

    private Acount acount;
    @FXML
    private TextField unitsField;
    @FXML
    private Button selectImageExpense;

    private Image scanedImage;
    @FXML
    private CheckBox newCategoryBox;
    @FXML
    private TextField newCategoryField;
    @FXML
    private TextArea descriptionNewCategory;

    private Category category;

    private List<Category> categorias;
    @FXML
    private Button cancelButton;

    private Pane mainPane;
    @FXML
    private ColorPicker colorPick;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //bind disable property to newCategoryfield
            newCategoryField.disableProperty().bind(Bindings.not(newCategoryBox.selectedProperty()));
            descriptionNewCategory.disableProperty().bind(Bindings.not(newCategoryBox.selectedProperty()));
            colorPick.disableProperty().bind(Bindings.not(newCategoryBox.selectedProperty()));
            categoryMenu.disableProperty().bind(newCategoryBox.selectedProperty());
            //get the instance of acount that will give us the user
            acount = Acount.getInstance();
            //obtain List of categories of the user
            categorias = acount.getUserCategories();
            for (int i = 0; i < categorias.size(); i++) {
                String[] names = categorias.get(i).getName().split("\\|");
                categoryMenu.getItems().add(names[0]);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void doneButton(MouseEvent event) throws AcountDAOException {
        //obtain all the fields needed to add an expense
        double cost = Double.parseDouble(costField.getText());
        String name = nameField.getText();
        String description = descriptionField.getText();
        int units = Integer.parseInt(unitsField.getText());
        LocalDate date = datePicker.getValue();

        if (!newCategoryField.getText().isEmpty()) {
            if (descriptionNewCategory.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setContentText("Please fill the description field");
                alert.showAndWait();
                return;
            } else {
                Color selectedColor = colorPick.getValue();
                String colorString = selectedColor.toString();
                String categoryName = newCategoryField.getText() + "|" + colorString;
                System.out.println(colorString);
                acount.registerCategory(categoryName, descriptionNewCategory.getText());
                categorias = acount.getUserCategories();
                boolean flag = false;
                for (int i = 0; i < categorias.size() && !flag; i++) {
                    Category aux = categorias.get(i);
                    if (aux.getName().equals(categoryName)) {
                        flag = true;
                        category = aux;
                    }
                }
            }
        } else if (category == null) {
            String nameCat = categoryMenu.getValue();
            boolean flag = false;
            for (int i = 0; i < categorias.size() && !flag; i++) {
                String[] parts = categorias.get(i).getName().split("\\|");
                String aux = parts[0];
                if (aux.equals(nameCat)) {
                    flag = true;
                    category = categorias.get(i);
                }
            }
        }
        try {
            acount.registerCharge(name, description, cost, units, scanedImage, date, category);
            mainPane = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            doneButton.getScene().setRoot(mainPane);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    //obtain image
    private void openFiles(MouseEvent event) {
        scanedImage = Utils.codeOpenFiles();
    }

    @FXML
    private void cancelAction(MouseEvent event) {
        try {
            mainPane = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            cancelButton.getScene().setRoot(mainPane);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
