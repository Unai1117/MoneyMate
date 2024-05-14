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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    Charge charge;
    Runnable action;

    public void setData(Charge charge) {
        this.charge = charge;
        price.setText("-" + charge.getCost() + " €");
        name.setText(charge.getName());
        LocalDate localDate = charge.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        date.setText(localDate.format(formatter));
        category.setText(charge.getCategory().getName().split("\\|")[0]);
        color.setFill(Color.web(charge.getCategory().getName().split("\\|")[1]));
    }
    
    public void setOnRemove(Runnable action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image manageUserIcon = new Image(getClass().getResourceAsStream("../assets/icons/trash.png"));
        Utils.iconToMenuItem(manageUserIcon, deleteChargeButton);
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
}
