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
    
    private BooleanProperty validName;
    private BooleanProperty validSurname;
    private BooleanProperty validNickname;
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty equalPasswords;
    
    private User user;
    private AcountDAO acountDAO;
    

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
            user = acount.getLoggedUser();
            if (user.getImage() != null) {
                circle.setVisible(true);
                circle.setFill(new ImagePattern(user.getImage()));
            } else {
                circle.setVisible(true);
                circle.setFill(new ImagePattern(null));
            }
            nickname.setText("Hello, " + user.getName() + " " + user.getSurname() + "!");
            eemail1.setPromptText(user.getEmail());
            nname.setPromptText(user.getName());
            ssurname.setPromptText(user.getSurname());
            
        } catch (AcountDAOException | IOException ex) {
            java.util.logging.Logger.getLogger(manageUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stackPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        
        validName = new SimpleBooleanProperty();
        validName.setValue(Boolean.FALSE);
        
        validSurname = new SimpleBooleanProperty();
        validSurname.setValue(Boolean.FALSE);
        
        validNickname = new SimpleBooleanProperty();
        validNickname.setValue(Boolean.FALSE);
        
        validEmail = new SimpleBooleanProperty();
        validEmail.setValue(Boolean.FALSE);

        validPassword = new SimpleBooleanProperty();
        validPassword.setValue(Boolean.FALSE);

        equalPasswords = new SimpleBooleanProperty();
        equalPasswords.setValue(Boolean.FALSE);
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

    @FXML
    //obtain image
    private void openFiles(ActionEvent event) {
        scanedImage = Utils.codeOpenFiles();
        if (scanedImage != null) {
            circle.setVisible(true);
            circle.setFill(new ImagePattern(scanedImage));

        }
    }
    
    /*
    @FXML
    private void saveChanges(MouseEvent event) {
        if (!nname.textProperty().getValueSafe().isEmpty()){
            checkname();
            if ((Objects.equals(validName.getValue(), Boolean.TRUE))){
                AcountDAO.updateNameUser(user, nname.textProperty().getValueSafe());
            }
        }
        if (!ssurname.textProperty().getValueSafe().isEmpty()){
            checkname();
            if ((Objects.equals(validSurname.getValue(), Boolean.TRUE))){
                AcountDAO.updateSurnameUser(user, ssurname.textProperty().getValueSafe());
            }
        }
        if (!eemail1.textProperty().getValueSafe().isEmpty()){
            checkname();
            if ((Objects.equals(validEmail.getValue(), Boolean.TRUE))){
                AcountDsAO.updateEmailUser(user, eemail1.textProperty().getValueSafe());
            }
        }
        if (!password1.textProperty().getValueSafe().isEmpty() && !password2.textProperty().getValueSafe().isEmpty()){
            checkpasswords();
            if ((Objects.equals(validPassords.getValue(), Boolean.TRUE))){
                AcountDAO.updatePasswordUser(user, password2.textProperty().getValueSafe());
            }
        }
        if (scanedImage != user.getImage()){
            AcountDAO.updateImageUser(user, scanedImage);
        }
    } */
    
    @FXML
    private void checkname() {
        if (!nname.textProperty().getValueSafe().isEmpty() ){
            nameLabel.getStyleClass().remove("destructive-label");
            nname.getStyleClass().remove("destructive-input");
            nameLabel.setText("Name");
            validName.setValue(Boolean.TRUE);
        } else {
            nameLabel.setText("Name - Invalid Name!");
            nameLabel.getStyleClass().add("destructive-label");
            nname.getStyleClass().add("destructive-input");
        }
    }
    
    @FXML
    private void checksurname() {
        if (!ssurname.textProperty().getValueSafe().isEmpty()){
            surnameLabel.getStyleClass().remove("destructive-label");
            ssurname.getStyleClass().remove("destructive-input");
            surnameLabel.setText("Surname");
            validSurname.setValue(Boolean.TRUE);
        } else {
            surnameLabel.setText("Surname - Invalid Surname!");
            surnameLabel.getStyleClass().add("destructive-label");
            ssurname.getStyleClass().add("destructive-input");
        }
    }
    
    
    @FXML
    private void checkemail() {
        if (User.checkEmail(eemail1.textProperty().getValueSafe())){
            emailLabel.getStyleClass().remove("destructive-label");
            eemail1.getStyleClass().remove("destructive-input");
            emailLabel.setText("Email");
            validName.setValue(Boolean.TRUE);
        } else {
            emailLabel.setText("Email - Invalid Email!");
            emailLabel.getStyleClass().add("destructive-label");
            eemail1.getStyleClass().add("destructive-input");
        }
    } 
    
    @FXML
    private void checkpasswords() {
        if (password1.textProperty().getValueSafe().isEmpty()){
            password1Label.setText("New Password - Invalid Password!");
            password1Label.getStyleClass().add("destructive-label");
            password2Label.getStyleClass().add("destructive-label");
            password1.getStyleClass().add("destructive-input");
            password2.getStyleClass().add("destructive-input");
        } else {
                password1Label.setText("New Password");
                password1Label.getStyleClass().remove("destructive-label");
                password2Label.getStyleClass().remove("destructive-label");
                password1.getStyleClass().remove("destructive-input");
                password2.getStyleClass().remove("destructive-input");
            if (!password1.textProperty().getValueSafe().equals(password2.textProperty().getValueSafe())){
                password2Label.setText("Confirm New Password - Passwords don't match!");
                password2Label.getStyleClass().add("destructive-label");
                password2.getStyleClass().add("destructive-input");
            } else {
                password2Label.setText("Confirm New Password");
                password1Label.setStyle("-fx-text-fill: #eeeeee");
                password2Label.setStyle("-fx-text-fill: #eeeeee");
                password1Label.getStyleClass().remove("destructive-label");
                password2Label.getStyleClass().remove("destructive-label");
                password1.getStyleClass().remove("destructive-input");
                password2.getStyleClass().remove("destructive-input");
                validPassword.setValue(Boolean.TRUE);
                
            }
        }
    }
}
