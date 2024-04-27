/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
