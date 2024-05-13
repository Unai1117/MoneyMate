/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Charge;

/**
 *
 * @author jorge
 */
public class ChargeItemController implements Initializable {
    
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
    
    public void setData(Charge charge) {
        price.setText("-" + charge.getCost() + " €");
        name.setText(charge.getName());
        LocalDate localDate = charge.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        date.setText(localDate.format(formatter));
        category.setText(charge.getCategory().getName().split("\\|")[0]);
        color.setFill(Color.web(charge.getCategory().getName().split("\\|")[1]));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
