/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author arnau
 */
public class RegisterController implements Initializable {

    @FXML
    private Button register;
    @FXML
    private BorderPane regPane;
    @FXML
    private TextField eemail;
    
    //properties to control valid fields
    private BooleanProperty validEmail;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.FALSE);
        
        eemail.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                checkEditEmail();
            }
        });
    }  
    
    @FXML
    private void openLoginPane(MouseEvent event) {
        try {
            Pane logPage = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            regPane.getChildren().setAll(logPage);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void checkEditEmail(){
        if(!Utils.checkEmail(eemail.textProperty().getValueSafe()))
            manageError(lIncorrectEmail, eemail, validEmail);
        else 
            manageCorrect(lIncorrectEmail, eemail, validEmail);
    }

    
}
