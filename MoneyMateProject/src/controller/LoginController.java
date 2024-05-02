/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author arnau
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button cancelLog;
    @FXML
    private Button loginButton;
    @FXML
    private BorderPane logPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openRegisterPane(MouseEvent event) {
        try {
            Pane regPage = FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
            logPane.getChildren().setAll(regPage);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void openMainPane(MouseEvent event) {
        try {
            Pane mainPage = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            logPane.getChildren().setAll(mainPage);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
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
    
}
