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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<Category> categoryMenu;
    
    private Acount acount; 
    @FXML
    private TextField unitsField;
    @FXML
    private Button selectImageExpense;
    @FXML
    private ImageView imageView;
    
    private Image scanedImage; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //remove the posibility of adding an expense in a previous date
            datePicker.setDayCellFactory((DatePicker picker) -> {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || date.compareTo(today) < 0 );
                    }
                };
            });
            //get the instance of acount that will give us the user
            acount = Acount.getInstance();
            
            //Obtain a list of type category with the categories of the user logged in
            categoryMenu = new ChoiceBox<Category>();
            List<Category> categorias = acount.getUserCategories(); 
            //add categories to the drop down menu to let the user select the desired category
            for(int i = 0; i < categorias.size(); i++){
                categoryMenu.getItems().add(categorias.get(i)); 
            }
        } catch (Exception e){
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
        Category category = categoryMenu.getValue();
        try{
            acount.registerCharge(name, description, cost, units, scanedImage, date, category); 
            Pane mainPane = FXMLLoader.load(getClass().getResource("/view/Main.fxml")); 
            addExpensePane.getChildren().setAll(mainPane); 
        } catch (IOException e){
            System.out.println(e);
        }
    }

    @FXML
    //obtain image
    private void openFiles(MouseEvent event) {
        scanedImage = Utils.codeOpenFiles();
        System.out.println(scanedImage.toString());
    }
   
    
}
