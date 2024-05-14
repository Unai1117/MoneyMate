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
    
    private Acount acount;
    

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
    }
}
