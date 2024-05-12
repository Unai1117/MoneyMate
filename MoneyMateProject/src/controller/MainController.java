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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.*;

/**
 * FXML Controller class
 *
 * @author jorge
 */
public class MainController implements Initializable {

    @FXML
    public Pane mainPane;
    @FXML
    public StackPane mainStackPane;
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
    private ScrollPane chargesPane;
    @FXML
    private AnchorPane charges;
    @FXML
    private ScrollPane mainScroll;
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private MenuItem manageUserButton;
    @FXML
    private MenuItem manageCategoriesButton;

    private final int CHARGES_PANE_SIZE = 400;

    private List<Charge> userCharges;
    private List<Category> userCategories;
    private double totalMoney;

    private ObservableList<PieChart.Data> userCategoriesData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userCharges = Acount.getInstance().getUserCharges();
            userCategories = Acount.getInstance().getUserCategories();

            Map<Category, List<Charge>> map = new HashMap();
            for (int i = 0; i < userCharges.size(); i++) {
                Charge charge = userCharges.get(i);
                totalMoney += charge.getCost() * charge.getUnits();
                Category category = charge.getCategory();
                if (map.containsKey(category)) {
                    List<Charge> aux = map.remove(category);
                    aux.add(charge);
                    map.put(category, aux);
                } else {
                    List<Charge> aux = new ArrayList();
                    aux.add(charge);
                    map.put(category, aux);
                }
            }

            double otherTotal = 0;
            for (Category key : map.keySet()) {
                double categoryTotal = 0;
                List<Charge> charges = map.get(key);
                for (int i = 0; i < charges.size(); i++) {
                    categoryTotal += charges.get(i).getCost() * charges.get(i).getUnits();
                }
                
                if (categoryTotal / totalMoney < 0.01) otherTotal += categoryTotal;
                else userCategoriesData.add(new PieChart.Data(key.getName(), categoryTotal / totalMoney * 100));
                System.out.println(key.getName() + " " + categoryTotal / totalMoney);
            }

            userCategoriesData.add(new PieChart.Data("Other", otherTotal / totalMoney * 100));
            System.out.println(userCategoriesData);
            chart.setData(userCategoriesData);
            chartLabel.setText("-" + totalMoney + " €");

        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*List<Category> userCategories;
        try {
            userCategories = model.Acount.getInstance().getUserCategories();
            for (int i = 0; i < userCategories.size(); i++) {
                Category userCategory = userCategories.get(i);
                String catName = userCategory.getName().split("|")[0];
                String catColor = userCategory.getName().split("|")[1];
                // categories.add(new PieChart.Data(""));
            }
            chart.setData(categories);
        } catch (AcountDAOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        chartInnerCircle.radiusProperty().bind(chartStackPane.widthProperty().multiply(0.85).divide(2));

        chargesPane.prefWidthProperty().bind(mainPane.widthProperty().subtract(364 + 3 * 20));
        chargesPane.prefHeightProperty().bind(mainPane.heightProperty().subtract(20 * 2 + 2));
        chargesPane.minWidthProperty().set(CHARGES_PANE_SIZE);

        mainStackPane.prefHeightProperty().bind(mainPane.heightProperty());
        mainStackPane.prefWidthProperty().bind(mainPane.widthProperty());

        mainScroll.prefHeightProperty().bind(mainStackPane.heightProperty());
        mainScroll.prefWidthProperty().bind(mainStackPane.widthProperty());

        mainFlowPane.prefHeightProperty().bind(mainStackPane.heightProperty().subtract(2));
        mainFlowPane.prefWidthProperty().bind(mainStackPane.widthProperty().subtract(2));

        mainPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Collapsed
            if (newVal.intValue() < 366 + 3 * 20 + CHARGES_PANE_SIZE) {
                chargesPane.prefWidthProperty().bind(mainPane.widthProperty().subtract(2 * 20));
                chargesPane.prefHeightProperty().bind(charges.heightProperty().add(2));
                chargesPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            } // Full
            else {
                chargesPane.prefWidthProperty().bind(mainPane.widthProperty().subtract(366 + 3 * 20));
                chargesPane.prefHeightProperty().bind(mainPane.heightProperty().subtract(20 * 2 + 2));
                chargesPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        });

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    chartLabel.setText(String.valueOf(data.getName() + "\n" + String.format("%.1f", data.getPieValue()) + "%"));

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

        Image manageUserIcon = new Image(getClass().getResourceAsStream("../assets/icons/user-gear.png"));
        Utils.iconToMenuItem(manageUserIcon, manageUserButton);
        Image manageCategoriesIcon = new Image(getClass().getResourceAsStream("../assets/icons/tag.png"));
        Utils.iconToMenuItem(manageCategoriesIcon, manageCategoriesButton);

    }

    @FXML
    private void openAddExpensePane(MouseEvent event) {
        try {
            Pane addExpensePane = FXMLLoader.load(getClass().getResource("/view/AddExpense.fxml"));
            addExpense.getScene().setRoot(addExpensePane);
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
        chartLabel.setText("-" + totalMoney + " €");
        Font font = new Font(46);
        chartLabel.setFont(font);
    }

}
