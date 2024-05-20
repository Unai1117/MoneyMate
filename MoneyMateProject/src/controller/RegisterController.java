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
public class RegisterController implements Initializable {

    @FXML
    private Button register;
    @FXML
    private VBox regPane;
    @FXML
    private TextField eemail;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label password1Label;
    @FXML
    private Label nicknameLabel;
    @FXML
    private Label password2Label;
    @FXML
    private Label surnameLabel;
    @FXML
    private TextField ssurname;
    @FXML
    private TextField nname;
    @FXML
    private TextField nnickname;
    @FXML
    private Image scanedImage;
    @FXML
    private StackPane stackPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Circle circle;

    private Acount acount;

    //properties to control valid fields
    private BooleanProperty validName;
    private BooleanProperty validSurname;
    private BooleanProperty validNickname;
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty equalPasswords;

    private final int EQUALS = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialise Account
        try {
            acount = Acount.getInstance();
        } catch (AcountDAOException | IOException ex) {
            java.util.logging.Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Booleans for checking fields
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

        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords);

        //register.disableProperty().bind(Bindings.not(validFields));
        stackPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        
    }
    
     //Method that register the user
    @FXML
    private void acceptRegister(MouseEvent event) throws AcountDAOException {
        checkname();
        checksurname();
        checknickname();
        checkemail();
        checkpasswords();
        if ((Objects.equals(validName.getValue(), Boolean.TRUE)) && (Objects.equals(validSurname.getValue(), Boolean.TRUE)) && (Objects.equals(validNickname.getValue(), Boolean.TRUE)) && (Objects.equals(validEmail.getValue(), Boolean.TRUE)) && (Objects.equals(validPassword.getValue(), Boolean.TRUE))) {
            String name = nname.textProperty().getValueSafe();
            String surname = ssurname.textProperty().getValueSafe();
            String email = eemail.textProperty().getValueSafe();
            String login = nnickname.textProperty().getValueSafe();
            String password = password2.textProperty().getValueSafe();
            Image image = scanedImage;
            LocalDate date = LocalDate.now();

            acount.registerUser(name, surname, email, login, password, image, date);

            try {
                Pane loginPane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                regPane.getScene().setRoot(loginPane);
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            //handleBAcceptOnAction();
        }
    }
    
    //Go to login page 
    @FXML
    private void goLogin(ActionEvent event) {
        try {
            Pane loginPane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            regPane.getScene().setRoot(loginPane);
        } catch (IOException e) {
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
    
    @FXML
    private void checkname() {
        if (!nname.textProperty().getValueSafe().isEmpty() ){
            nameLabel.getStyleClass().remove("destructive-label");
            nname.getStyleClass().remove("destructive-input");
            nameLabel.setText("Name*");
            validName.setValue(Boolean.TRUE);
        } else {
            nameLabel.setText("Name* - Invalid Name!");
            nameLabel.getStyleClass().add("destructive-label");
            nname.getStyleClass().add("destructive-input");
        }
    }
    
    @FXML
    private void checksurname() {
        if (!ssurname.textProperty().getValueSafe().isEmpty()){
            surnameLabel.getStyleClass().remove("destructive-label");
            ssurname.getStyleClass().remove("destructive-input");
            surnameLabel.setText("Surname*");
            validSurname.setValue(Boolean.TRUE);
        } else {
            surnameLabel.setText("Surname* - Invalid Surname!");
            surnameLabel.getStyleClass().add("destructive-label");
            ssurname.getStyleClass().add("destructive-input");
        }
    }
    
    @FXML
    private void checknickname() {
        if (!nnickname.textProperty().getValueSafe().isEmpty()){
            nicknameLabel.getStyleClass().remove("destructive-label");
            nnickname.getStyleClass().remove("destructive-input");
            nicknameLabel.setText("Nickname*");
            validName.setValue(Boolean.TRUE);
        } else {
            nicknameLabel.setText("Nickname* - Invalid Nickname!");
            nnickname.getStyleClass().add("destructive-input");
            nicknameLabel.getStyleClass().add("destructive-label");
        }
    } 
    
    @FXML
    private void checkemail() {
        if (User.checkEmail(eemail.textProperty().getValueSafe())){
            emailLabel.getStyleClass().remove("destructive-label");
            eemail.getStyleClass().remove("destructive-input");
            emailLabel.setText("Email*");
            validName.setValue(Boolean.TRUE);
        } else {
            emailLabel.setText("Email* - Invalid Email!");
            emailLabel.getStyleClass().add("destructive-label");
            eemail.getStyleClass().add("destructive-input");
        }
    } 
    
    @FXML
    private void checkpasswords() {
        if (password1.textProperty().getValueSafe().isEmpty()){
            password1Label.setText("Password* - Invalid Password!");
            password1Label.getStyleClass().add("destructive-label");
            password2Label.getStyleClass().add("destructive-label");
            password1.getStyleClass().add("destructive-input");
            password2.getStyleClass().add("destructive-input");
        } else {
                password1Label.setText("Password*");
                password1Label.getStyleClass().remove("destructive-label");
                password2Label.getStyleClass().remove("destructive-label");
                password1.getStyleClass().remove("destructive-input");
                password2.getStyleClass().remove("destructive-input");
            if (!password1.textProperty().getValueSafe().equals(password2.textProperty().getValueSafe())){
                password2Label.setText("Confirm Password* - Passwords don't match!");
                //password1Label.setStyle("-fx-text-fill: #ed0c2a");
                password2Label.getStyleClass().add("destructive-label");
                password2.getStyleClass().add("destructive-input");
            } else {
                password2Label.setText("Confirm Password*");
                password1Label.setStyle("-fx-text-fill: #eeeeee");
                password2Label.setStyle("-fx-text-fill: #eeeeee");password1Label.setText("Password* - Invalid Password!");
                password1Label.getStyleClass().remove("destructive-label");
                password2Label.getStyleClass().remove("destructive-label");
                password1.getStyleClass().remove("destructive-input");
                password2.getStyleClass().remove("destructive-input");
                validPassword.setValue(Boolean.TRUE);
                
            }
        }
    } 
}
