/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author arnau
 */
public class LoginController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private StackPane logPane;
    @FXML
    private TextField nnickname;
    @FXML
    private PasswordField password1;
    @FXML
    private Label incorrectLabel;

    private Acount acount;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {
            acount = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            java.util.logging.Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        password1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                openMainPane(null);
            }
        });
    }

    @FXML
    private void openMainPane(MouseEvent event) {
        if ((nnickname.getText() != null || !nnickname.getText().trim().isEmpty()) && (password1.getText() != null || !password1.getText().trim().isEmpty())) {
            try {
                String nickname = nnickname.textProperty().getValueSafe();
                String password = password1.textProperty().getValueSafe();
                if (acount.logInUserByCredentials(nickname, password)) {
                    try {
                        Pane mainPane = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                        logPane.getScene().setRoot(mainPane);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                } else {
                    incorrectLabel.visibleProperty().set(true);
                }
            } catch (AcountDAOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void openRegisterPane(ActionEvent event) {
        try {
            Pane regPage = FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
            logPane.getScene().setRoot(regPage);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void manageError(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, textField);
        textField.requestFocus();
    }

    private void manageCorrect(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, textField);
    }

    private void showErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.visibleProperty().set(true);
    }

    private void hideErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }

}
