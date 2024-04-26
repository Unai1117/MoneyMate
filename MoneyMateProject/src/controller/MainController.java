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
        
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void OpenNewTab(MouseEvent event) {
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/AddExpense.fxml"));
            Parent root = loader.load();
            AddExpenseController controller = loader.getController(); 
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Expense Window");
            stage.show();
            
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
}
