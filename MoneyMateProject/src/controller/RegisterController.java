/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    private BooleanProperty validName = new SimpleBooleanProperty(false);
    private BooleanProperty validSurname = new SimpleBooleanProperty(false);
    private BooleanProperty validNickname = new SimpleBooleanProperty(false);
    private BooleanProperty validEmail = new SimpleBooleanProperty(false);
    private BooleanProperty validPassword = new SimpleBooleanProperty(false);
    private BooleanProperty equalPasswords = new SimpleBooleanProperty(false);

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

        //BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords);
        //register.disableProperty().bind(Bindings.not(validFields));
        stackPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));

        nnickname.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                checknickname();
            }
        });

        nname.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                checkname();
            }
        });

        ssurname.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                checksurname();
            }
        });

        eemail.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                checkemail();
            }
        });

        password1.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                checkpasswords();
            }
        });

        password2.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                checkpasswords();
            }
        });

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
        if (!nname.textProperty().getValueSafe().isEmpty()) {
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
        if (!ssurname.textProperty().getValueSafe().isEmpty()) {
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
        if (User.checkNickName(nnickname.textProperty().getValueSafe())) {
            nicknameLabel.getStyleClass().remove("destructive-label");
            nnickname.getStyleClass().remove("destructive-input");
            nicknameLabel.setText("Nickname*");
            validNickname.setValue(Boolean.TRUE);
        } else {
            nicknameLabel.setText("Nickname* - Invalid Nickname!");
            nnickname.getStyleClass().add("destructive-input");
            nicknameLabel.getStyleClass().add("destructive-label");
        }
    }

    @FXML
    private void checkemail() {
        if (User.checkEmail(eemail.textProperty().getValueSafe())) {
            emailLabel.getStyleClass().remove("destructive-label");
            eemail.getStyleClass().remove("destructive-input");
            emailLabel.setText("Email*");
            validEmail.setValue(Boolean.TRUE);
        } else {
            emailLabel.setText("Email* - Invalid Email!");
            emailLabel.getStyleClass().add("destructive-label");
            eemail.getStyleClass().add("destructive-input");
        }
    }

    @FXML
    private void checkpasswords() {
        if (!User.checkPassword(password1.textProperty().getValueSafe())) {
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
            if (!password1.textProperty().getValueSafe().equals(password2.textProperty().getValueSafe())) {
                password2Label.setText("Confirm Password* - Passwords don't match!");
                password2Label.getStyleClass().add("destructive-label");
                password2.getStyleClass().add("destructive-input");
            } else {
                password2Label.setText("Confirm Password*");
                password1Label.getStyleClass().remove("destructive-label");
                password2Label.getStyleClass().remove("destructive-label");
                password1.getStyleClass().remove("destructive-input");
                password2.getStyleClass().remove("destructive-input");
                validPassword.setValue(Boolean.TRUE);

            }
        }
    }
}
