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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.EQUALS;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    
    
    
    @FXML
    private Circle circle;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private Label nickname;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField nname;
    @FXML
    private Label surnameLabel;
    @FXML
    private TextField ssurname;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField eemail1;
    @FXML
    private Label password1Label;
    @FXML
    private Label password2Label;
    @FXML
    private Button save;
    @FXML 
    private VBox regPane;
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
            Acount acount = Acount.getInstance();
            User user = acount.getLoggedUser();
            if (user.getImage() != null) {
                circle.setVisible(true);
                circle.setFill(new ImagePattern(user.getImage()));
            } else {
                circle.setVisible(true);
                circle.setFill(new ImagePattern(null));
            }
            if (user.getSurname() == null) {
                nickname.setText("Hello, " + user.getName() + "!");
            } else {
                nickname.setText("Hello, " + user.getName() + " " + user.getSurname() + "!");
            }
            eemail1.setText(user.getEmail());
        } catch (AcountDAOException | IOException ex) {
            java.util.logging.Logger.getLogger(manageUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stackPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
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

    @FXML
    private void goLogin(ActionEvent event) {
    }

    @FXML
    //obtain image
    private void openFiles(ActionEvent event) {
        scanedImage = Utils.codeOpenFiles();
        if (scanedImage != null) {
            circle.setVisible(true);
            circle.setFill(new ImagePattern(scanedImage));

        }
    }

    @FXML
    private void acceptRegister(MouseEvent event) {
    }
}
