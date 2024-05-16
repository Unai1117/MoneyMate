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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private VBox chargesList;
    @FXML
    private ScrollPane mainScroll;
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private MenuItem manageUserButton;
    @FXML
    private MenuItem manageCategoriesButton;
    @FXML
    private MenuItem logoutButton;
    
    private final int CHARGES_PANE_SIZE = 400;
    
    private List<Charge> userCharges;
    private List<Category> userCategories;
    private double totalMoney;
    
    private ObservableList<PieChart.Data> userCategoriesData = FXCollections.observableArrayList();
    @FXML
    private Button addExpense1;
    

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Generates a Category - Charges map
            userCharges = Acount.getInstance().getUserCharges();
            userCategories = Acount.getInstance().getUserCategories();
            List<String> chartColors = new ArrayList<>();
            
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

                // If the relative total is less than 1%, merge categories in "other"
                if (categoryTotal / totalMoney < 0.01) {
                    otherTotal += categoryTotal;
                } else {
                    chartColors.add(key.getName().split("\\|")[1]);
                    userCategoriesData.add(new PieChart.Data(key.getName().split("\\|")[0], categoryTotal));
                }
            }
            
            userCategoriesData.add(new PieChart.Data("Other", otherTotal));
            chart.setData(userCategoriesData);
            chartLabel.setText("-" + totalMoney + " €");

            // Set the appropriate colors to the chart pies
            int colorIndex = 0;
            for (PieChart.Data data : userCategoriesData) {
                if (colorIndex == userCategoriesData.size() - 1 && "Other".equals(data.getName())) {
                    data.getNode().setStyle("-fx-pie-color: #9c9c9c");
                    
                } else {
                    data.getNode().setStyle("-fx-pie-color: " + chartColors.get(colorIndex).replace("0x", "#"));
                    
                }
                colorIndex++;
            }
            
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Inner circle default value
        chartInnerCircle.radiusProperty().bind(chartStackPane.widthProperty().multiply(0.85).divide(2));
        
        chargesPane.minWidthProperty().set(CHARGES_PANE_SIZE);

        // same as mainPane
        mainStackPane.prefHeightProperty().bind(mainPane.heightProperty());
        mainStackPane.prefWidthProperty().bind(mainPane.widthProperty());

        //same as mainPane/stackPane
        mainScroll.prefHeightProperty().bind(mainStackPane.heightProperty());
        mainScroll.prefWidthProperty().bind(mainStackPane.widthProperty());

        // same as stackpane/scrollpane - 2 for the scrollpane (it needs 2 extra pixels to fit all the contents
        mainFlowPane.prefHeightProperty().bind(mainStackPane.heightProperty().subtract(2));
        mainFlowPane.prefWidthProperty().bind(mainStackPane.widthProperty().subtract(2));

        // change sizes based on window size
        mainPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Collapsed
            if (newVal.intValue() < 366 + 3 * 20 + CHARGES_PANE_SIZE) {
                // 2 * padding
                chargesPane.prefWidthProperty().bind(mainPane.widthProperty().subtract(2 * 20));
                chargesPane.prefHeightProperty().bind(chargesList.heightProperty().add(2));
                chargesPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                chargesList.prefWidthProperty().bind(chargesPane.widthProperty());
                mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            } // Full
            else {
                chargesPane.prefWidthProperty().bind(mainPane.widthProperty().subtract(366 + 3 * 20));
                chargesPane.prefHeightProperty().bind(mainPane.heightProperty().subtract(20 * 2 + 2));
                chargesPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                chargesList.prefWidthProperty().bind(chargesPane.widthProperty().subtract(20));
                mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        });

        // chart animations
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    chartLabel.setText(String.valueOf(data.getName() + "\n" + String.format("%.1f", data.getPieValue()) + " €"));
                    
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

        // add images to menu items
        Image manageUserIcon = new Image(getClass().getResourceAsStream("../assets/icons/user-gear.png"));
        Utils.iconToMenuItem(manageUserIcon, manageUserButton);
        Image manageCategoriesIcon = new Image(getClass().getResourceAsStream("../assets/icons/tag.png"));
        Utils.iconToMenuItem(manageCategoriesIcon, manageCategoriesButton);
        Image logoutIcon = new Image(getClass().getResourceAsStream("../assets/icons/sign-out.png"));
        Utils.iconToMenuItem(logoutIcon, logoutButton);

        // create charges
        for (Charge userCharge : userCharges) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/ChargeItem.fxml"));
            
            try {
                HBox item = fxmlLoader.load();
                ChargeItemController cic = fxmlLoader.getController();
                cic.setData(userCharge);
                cic.setOnRemove(() -> {
                    chargesList.getChildren().remove(item);
                    totalMoney -= userCharge.getCost() * userCharge.getUnits();
                    chartLabel.setText("-" + totalMoney + " €");
                    
                    for (PieChart.Data data : userCategoriesData) {
                        if (data.getName().equals(userCharge.getCategory().getName().split("\\|")[0])) {
                            data.setPieValue(data.getPieValue() - userCharge.getCost() * userCharge.getUnits());
                            break;
                        }
                    }
                    
                });
                chargesList.getChildren().add(item);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
                
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
    
    @FXML
    private void openCatMenu(ActionEvent event) {
        try {
            Pane manageCategoriesPane = FXMLLoader.load(getClass().getResource("/view/manageCate.fxml"));
            addExpense.getScene().setRoot(manageCategoriesPane);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @FXML
    private void openUserMod(ActionEvent event) {
        try {
            Pane manageUserPane = FXMLLoader.load(getClass().getResource("/view/manageUser.fxml"));
            addExpense.getScene().setRoot(manageUserPane);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @FXML
    private void logOut(ActionEvent event) throws AcountDAOException, IOException {
        Acount.getInstance().logOutUser();
        try {
            Pane logIn = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            addExpense.getScene().setRoot(logIn);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
