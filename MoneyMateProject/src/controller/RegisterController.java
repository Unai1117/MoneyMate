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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.EQUALS;
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
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private Label lIncorrectEmail;
    @FXML 
    private Label lPassDifferent;
    
    
    //properties to control valid fields
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty equalPasswords;
    
    private final int EQUALS = 0; 
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.FALSE);
        
        validPassword = new SimpleBooleanProperty();
        validPassword.setValue(Boolean.FALSE);
        
        equalPasswords = new SimpleBooleanProperty();
        equalPasswords.setValue(Boolean.FALSE);
        
        eemail.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                checkEditEmail();
            }
        });
        password1.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                checkPassword();
            }
        });
        password2.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                checkEquals();
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
    
    private void checkPassword(){
    
    }
    
        
    private void checkEquals(){
        if(password1.textProperty().getValueSafe().compareTo(password2.textProperty().getValueSafe()) != EQUALS){
            showErrorMessage(lPassDifferent,password2);
            equalPasswords.setValue(Boolean.FALSE);
            password2.textProperty().setValue("");
            password2.requestFocus();
        }else
            manageCorrect(lPassDifferent,password2,equalPasswords);
    }
    
    @FXML
    private void handleBAcceptOnAction(ActionEvent event) {
        password2.textProperty().setValue("");

        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
    }

    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
    }
    
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        openLoginPage();
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-colo: #FCE5E0");
    }
    
    private void hideErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
}
