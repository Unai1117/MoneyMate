/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }    
    @FXML
    private void doneButton(MouseEvent event) {
        try{
            Pane mainPane = FXMLLoader.load(getClass().getResource("/view/Main.fxml")); 
            addExpensePane.getChildren().setAll(mainPane); 
        } catch (IOException e){
            System.out.println(e);
        }
    }
   
    
}
