/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.*;


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
    @FXML
    private Label changedSaved;

    private BooleanProperty validEmail = new SimpleBooleanProperty(true);
    private BooleanProperty validPassword = new SimpleBooleanProperty(true);

    private User user;
    private AcountDAO acountDAO;

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
            Acount acount = Acount.getInstance();
            user = acount.getLoggedUser();
            if (user.getImage() != null) {
                circle.setVisible(true);
                circle.setFill(new ImagePattern(user.getImage()));
            } else {
                circle.setVisible(true);
                circle.setFill(new ImagePattern(null));
            }
            nickname.setText("Hello, " + user.getNickName() + "!");
            eemail1.setPromptText(user.getEmail());
            nname.setPromptText(user.getName());
            ssurname.setPromptText(user.getSurname());

        } catch (AcountDAOException | IOException ex) {
            java.util.logging.Logger.getLogger(manageUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stackPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));

        eemail1.focusedProperty().addListener((obs, oldVal, newVal) -> {
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

    @FXML
    private void saveChanges(ActionEvent event) {
        changedSaved.setVisible(false);

        if (validEmail.getValue() && validPassword.getValue()) {
            if (!nname.textProperty().getValueSafe().isEmpty()) {
                user.setName(nname.textProperty().getValueSafe());
                nname.setPromptText(nname.textProperty().getValueSafe());
                nname.setText("");

            }
            if (!ssurname.textProperty().getValueSafe().isEmpty()) {
                user.setSurname(ssurname.textProperty().getValueSafe());
                ssurname.setPromptText(ssurname.textProperty().getValueSafe());
                ssurname.setText("");

            }
            if (!eemail1.textProperty().getValueSafe().isEmpty()) {
                checkemail();
                if (validEmail.getValue()) {
                    user.setEmail(eemail1.textProperty().getValueSafe());
                    eemail1.setPromptText(eemail1.textProperty().getValueSafe());
                    eemail1.setText("");
                }
            }
            if (!password1.textProperty().getValueSafe().isEmpty() && !password2.textProperty().getValueSafe().isEmpty()) {
                checkpasswords();
                if (validPassword.getValue()) {
                    user.setPassword(password2.textProperty().getValueSafe());
                    password1.setText("");
                    password2.setText("");
                }
            }
            if (scanedImage != user.getImage() && scanedImage != null) {
                user.setImage(scanedImage);
            }
            changedSaved.setVisible(true);
        }

    }

    @FXML
    private void checkemail() {
        if (User.checkEmail(eemail1.textProperty().getValueSafe()) || eemail1.textProperty().getValueSafe().isEmpty()) {
            emailLabel.setStyle("-fx-text-fill: #eeeeee");
            eemail1.setStyle("");
            emailLabel.setText("Email*");
            validEmail.setValue(true);
        } else {
            emailLabel.setText("Email* - Invalid Email!");
            emailLabel.setStyle("-fx-text-fill: #ed0c2a");
            eemail1.setStyle("-fx-border-color: -fx-destructive !important;\n"
                    + "    -fx-border-width: 1px !important;");
            validEmail.setValue(false);

        }
    }

    @FXML
    private void checkpasswords() {
        if (!User.checkPassword(password1.textProperty().getValueSafe()) && !password1.textProperty().getValueSafe().isEmpty()) {
            password1Label.setText("Password* - Invalid Password!");
            password1Label.setStyle("-fx-text-fill: #ed0c2a");
            password2Label.setStyle("-fx-text-fill: #ed0c2a");
            password1.setStyle("-fx-border-color: -fx-destructive !important;\n"
                    + "    -fx-border-width: 1px !important;");
            password2.setStyle("-fx-border-color: -fx-destructive !important;\n"
                    + "    -fx-border-width: 1px !important;");
            validPassword.setValue(false);

        } else {
            password1Label.setText("Password*");
            if (!password1.textProperty().getValueSafe().equals(password2.textProperty().getValueSafe())) {
                password2Label.setText("Confirm Password* - Passwords don't match!");
                password2Label.setStyle("-fx-text-fill: #ed0c2a");
                password2.setStyle("-fx-border-color: -fx-destructive !important;\n"
                        + "    -fx-border-width: 1px !important;");
                validPassword.setValue(false);

            } else {
                password2Label.setText("Confirm Password*");
                password1Label.setStyle("-fx-text-fill: #eeeeee");
                password2Label.setStyle("-fx-text-fill: #eeeeee");
                password1.setStyle("");
                password2.setStyle("");
                validPassword.setValue(true);

            }
        }
    }
}
