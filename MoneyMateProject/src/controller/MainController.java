/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button; 
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.Pane; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author unai
 */
public class MainController implements Initializable {
    
    @FXML
    private Pane mainPane; 
    @FXML
    private Button AddExpense;
        
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void openAddExpensePane(MouseEvent event) {
        try{
            Pane addExpensePane = FXMLLoader.load(getClass().getResource("/view/AddExpense.fxml")); 
            mainPane.getChildren().setAll(addExpensePane); 
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
}
