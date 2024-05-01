/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button; 
import javafx.scene.layout.Pane; 
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author unai
 */
public class MainController implements Initializable {
    
    @FXML
    private Pane mainPane; 
    @FXML
    private Button addExpense;
    @FXML
    private PieChart chart;
    @FXML
    private StackPane chartStackPane;
    @FXML
    private Circle chartInnerCircle;
        
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> chartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Test1", 13),
                new PieChart.Data("Test2", 25),
                new PieChart.Data("Test3", 10),
                new PieChart.Data("Test4", 22),
                new PieChart.Data("Test5", 30));
        
        chart.setData(chartData);
        
        chartInnerCircle.radiusProperty().bind(chartStackPane.widthProperty().multiply(0.85).divide(2));
 
    }    

    @FXML
    private void openAddExpensePane(MouseEvent event) {
        try{
            Pane addExpensePane = FXMLLoader.load(getClass().getResource("/view/AddExpense.fxml")); 
            mainPane.getChildren().setAll(addExpensePane); 
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
}
