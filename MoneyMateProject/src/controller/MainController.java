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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author jorge
 */
public class MainController implements Initializable {
    
    @FXML
    private FlowPane mainPane;
    @FXML
    private Button addExpense;
    @FXML
    private PieChart chart;
    @FXML
    private StackPane chartStackPane;
    @FXML
    private Circle chartInnerCircle;
    @FXML
    private Label chartLabel;
    @FXML
    private VBox chargesPane;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> chartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Restaurants", 13),
                        new PieChart.Data("Fuel", 25),
                        new PieChart.Data("Taxes", 10),
                        new PieChart.Data("Events", 22),
                        new PieChart.Data("Other", 30));
        
        chart.setData(chartData);
        chartInnerCircle.radiusProperty().bind(chartStackPane.widthProperty().multiply(0.85).divide(2));
        
        System.out.println(mainPane.widthProperty());
        chargesPane.prefWidthProperty().bind(mainPane.widthProperty().subtract(360 + 2* 20));
        chargesPane.minWidthProperty().set(400);
        
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    chartLabel.setText(String.valueOf(data.getName() + "\n" + data.getPieValue() + "%"));
                    
                    Font font = new Font(36);
                    chartLabel.setFont(font);
                    
                    ScaleTransition grow = new ScaleTransition(Duration.millis(200));
                    grow.setNode(data.getNode());
                    grow.setToX(1.1);
                    grow.setToY(1.1);
                    grow.playFromStart();

                }
            });
            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    ScaleTransition shrink = new ScaleTransition(Duration.millis(200));
                    shrink.setNode(data.getNode());
                    shrink.setToX(1);
                    shrink.setToY(1);
                    shrink.playFromStart();
                }
            });
        }
    }
    
    @FXML
    private void openAddExpensePane(MouseEvent event) {
        try {
            Pane addExpensePane = FXMLLoader.load(getClass().getResource("/view/AddExpense.fxml"));
            mainPane.getChildren().setAll(addExpensePane);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @FXML
    private void shrinkCircle(MouseEvent event) {
        ScaleTransition shrink = new ScaleTransition(Duration.millis(200));
        shrink.setNode(chartInnerCircle);
        shrink.setToX(0.85);
        shrink.setToY(0.85);
        shrink.playFromStart();
    }
    
    @FXML
    private void restoreCircle(MouseEvent event) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(200));
        grow.setNode(chartInnerCircle);
        grow.setToX(1);
        grow.setToY(1);
        grow.playFromStart();
        chartLabel.setText("-1536 €");
        Font font = new Font(46);
        chartLabel.setFont(font);
    }
    
}
