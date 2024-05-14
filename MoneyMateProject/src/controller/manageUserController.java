/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.stage.*;
import java.awt.Desktop;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.EQUALS;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;

/**
 * FXML Controller class
 *
 * @author arnau
 */
public class manageUserController implements Initializable {

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
    @FXML 
    private TextField nname;
    @FXML 
    private TextField nnickname;
    @FXML
    private Image scanedImage;
    
    private Acount acount;
    
    
    
    //properties to control valid fields
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty equalPasswords;
    
    private final int EQUALS = 0; 
    
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //Initialise Account
        try {
            acount = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            java.util.logging.Logger.getLogger(manageUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Booleans for checking fields
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
        
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords);
        
        register.disableProperty().bind(Bindings.not(validFields));
    }  
    
    //Method that checks email correctness
    private void checkEditEmail(){
        if(!Utils.checkEmail(eemail.textProperty().getValueSafe()))
            manageError(lIncorrectEmail, eemail, validEmail);
        else 
            manageCorrect(lIncorrectEmail, eemail, validEmail);
    }
    
    //Method that checks password correctness
    private void checkPassword(){
        if(!Utils.checkPassword(password1.textProperty().getValueSafe()))
            manageError(lPassDifferent, password1, validPassword);
        else 
            manageCorrect(lPassDifferent, password1, validPassword);
    }
    
    //Go to login page  
    @FXML
    private void goLogin(MouseEvent event){
        try {
                Pane loginPane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                regPane.getScene().setRoot(loginPane);
            } catch (IOException e) {
                System.out.println(e);
            }
    }
    
    //Method thhat checks if both passwords are equals
    private void checkEquals(){
        if(password1.textProperty().getValueSafe().compareTo(password2.textProperty().getValueSafe()) != EQUALS){
            showErrorMessage(lPassDifferent,password2);
            equalPasswords.setValue(Boolean.FALSE);
            password2.textProperty().setValue("");
        }else
            manageCorrect(lPassDifferent,password2,equalPasswords);
    }
    
    @FXML
    //obtain image
    private void openFiles(MouseEvent event) {
        scanedImage = Utils.codeOpenFiles();
    }
    
    
    //Method that register the user
    @FXML
    private void acceptRegister(MouseEvent event) throws AcountDAOException{
        if ((Objects.equals(validEmail.getValue(), Boolean.TRUE)) && (Objects.equals(validPassword.getValue(), Boolean.TRUE)) && (Objects.equals(equalPasswords.getValue(), Boolean.TRUE))){
            String[] name = nname.textProperty().getValueSafe().split(" ");
            String retname = name[0];
            String surname = name[1];
            String email= eemail.textProperty().getValueSafe();
            String login = nnickname.textProperty().getValueSafe();
            String password = password2.textProperty().getValueSafe();
            Image image = scanedImage;
            LocalDate date = LocalDate.now();
            
            acount.registerUser(retname, surname, email, login, password, image, date);
            
            try {
                Pane loginPane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                regPane.getScene().setRoot(loginPane);
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            handleBAcceptOnAction();
        }
    }
    
    //Methods for checking fields
    private void handleBAcceptOnAction() {
        password2.textProperty().setValue("");
        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
    }

    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
    }
    
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
    }
    
    private void showErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-colo: #FCE5E0");
    }
    
    private void hideErrorMessage(Label errorLabel,TextField textField){
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }

    //registerUser();



}