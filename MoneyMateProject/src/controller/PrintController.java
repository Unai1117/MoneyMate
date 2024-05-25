/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jorge
 */
public class PrintController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Button printButton;

    public void setImage(WritableImage image) {
        this.image.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) image.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void print(ActionEvent event) {
        Stage stage = (Stage) image.getScene().getWindow();
        stage.close();

    }

}
