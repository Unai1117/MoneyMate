/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

/**
 *
 * @author jorge
 */
public class ChargeItemController implements Initializable {

    @FXML
    private HBox chargeMain;
    @FXML
    private Label price;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label category;
    @FXML
    private Circle color;
    @FXML
    private MenuItem deleteChargeButton;
    @FXML
    private MenuItem viewImageButton;

    Charge charge;
    Runnable action;

    Image scannedImage;

    public void setData(Charge charge) {
        // Populate labels
        this.charge = charge;
        price.setText("-" + charge.getCost() * charge.getUnits() + " €");
        name.setText(charge.getName());
        LocalDate localDate = charge.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        date.setText(localDate.format(formatter) + " • " + charge.getUnits() + " units");
        category.setText(charge.getCategory().getName().split("\\|")[0]);
        color.setFill(Color.web(charge.getCategory().getName().split("\\|")[1]));
        scannedImage = charge.getImageScan();
        // Checks if image exists
        if (scannedImage == null || scannedImage.getHeight() == 0.0) {
            viewImageButton.setText("Add Image");
        }

        // Add a tooltip with the description
        Tooltip tooltip = new Tooltip(charge.getDescription().isEmpty() ? "No description." : charge.getDescription());
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(chargeMain, tooltip);

    }

    public void setOnRemove(Runnable action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add images to menu
        Image manageUserIcon = new Image(getClass().getResourceAsStream("../assets/icons/trash.png"));
        Utils.iconToMenuItem(manageUserIcon, deleteChargeButton);
        Image viewImageIcon = new Image(getClass().getResourceAsStream("../assets/icons/image-square.png"));
        Utils.iconToMenuItem(viewImageIcon, viewImageButton);
    }

    @FXML
    private void deleteCharge(ActionEvent event) {
        try {
            Acount.getInstance().removeCharge(charge);
            if (action != null) {
                action.run();
            }
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ChargeItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void viewImage(ActionEvent event) {
        if (scannedImage == null || scannedImage.getHeight() == 0.0) {
            scannedImage = Utils.codeOpenFiles();
            if (scannedImage != null) {
                charge.setImageScan(scannedImage);
                viewImageButton.setText("View Image");
            }

        } else {
            Stage imageStage = new Stage();
            Image image = scannedImage;
            ImageView imageView = new ImageView(image);

            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            StackPane stackPane = new StackPane(imageView);

            Scene scene = new Scene(stackPane, 600, 600);

            imageView.setFitWidth(scene.widthProperty().doubleValue());
            imageView.setFitHeight(scene.heightProperty().doubleValue());

            scene.widthProperty().addListener((observable, oldValue, newValue) -> {
                imageView.setFitWidth(newValue.doubleValue());
            });

            scene.heightProperty().addListener((observable, oldValue, newValue) -> {
                imageView.setFitHeight(newValue.doubleValue());
            });

            imageStage.setTitle("Image Viewer");
            imageStage.setScene(scene);
            imageStage.show();
        }

    }
}
