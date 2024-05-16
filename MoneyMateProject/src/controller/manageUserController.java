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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;

/**
 * FXML Controller class
 *
 * @author arnau
 */
public class manageUserController implements Initializable {
    
    private Acount acount;
    private User user;
    
    
    @FXML
    private Circle circle;
    @FXML
    private Button register;
    @FXML
    private Button cancel;
    @FXML
    private BorderPane regPane;
    @FXML
    private TextField eemail1;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private Label lIncorrectEmail;
    @FXML 
    private Label lPassDifferent;
    @FXML 
    private TextField nname1;
    @FXML 
    private Label nickname;
    @FXML
    private Image scanedImage;
    

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
        
        user = acount.getLoggedUser();
        
        circle.setFill(new ImagePattern(user.getImage()));
        nickname.setText(user.getNickName());
        nname1.setText(user.getName() + user.getSurname());
        eemail1.setText(user.getEmail());
    }
    
    @FXML
    private void openMainPane(ActionEvent event) {
        try {
            Pane mainPane = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            regPane.getScene().setRoot(mainPane);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void putImage(){
        
    }
    
    /*@FXML 
    private void toSecondaryColour(MouseEvent event){
        cancel.setStyle("button-secondary");
    }*/
}
