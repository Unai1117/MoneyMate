/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;


public class StatisticsController implements Initializable {

    @FXML
    private VBox statisticsMain;
    @FXML
    private LineChart lineChart;
    @FXML
    private FlowPane flowPane;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label totalExpense;
    @FXML
    private Label monthlyExpense;
    @FXML
    private Label yearlyExpense;

    @FXML
    private Button printButton;

    @FXML
    private ChoiceBox<String> mode;

    private List<Charge> userCharges;
    private List<Category> userCategories;
    private double totalMoney;
    private double monthlyTotal;
    private double yearlyTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mode.getItems().addAll("All", "Year");
        mode.getSelectionModel().selectFirst();
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));

        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        computeChart("All");

        mode.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                computeChart(newVal);
            }
        });
    }

    private void computeChart(String mode) {
        totalMoney = 0;
        yearlyTotal = 0;
        monthlyTotal = 0;
        LocalDate currentDate = LocalDate.now();
        lineChart.getData().clear();
        String style = "";
        int styleIndex = 1;
        try {

            userCharges = Acount.getInstance().getUserCharges();
            userCategories = Acount.getInstance().getUserCategories();

            Map<Category, List<Charge>> map = new HashMap();

            Set<Integer> years = new HashSet();

            for (int i = 0; i < userCharges.size(); i++) {
                Charge charge = userCharges.get(i);
                totalMoney += charge.getCost() * charge.getUnits();
                years.add(charge.getDate().getYear());
                if (charge.getDate().getYear() == currentDate.getYear()) {
                    yearlyTotal += charge.getCost() * charge.getUnits();

                    if (charge.getDate().getMonthValue() == currentDate.getMonthValue()) {
                        monthlyTotal += charge.getCost() * charge.getUnits();
                    }
                }

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

            totalExpense.setText("Total expense: " + totalMoney + " €");
            yearlyExpense.setText("This year: " + yearlyTotal + " €");
            monthlyExpense.setText("This month: " + monthlyTotal + " €");

            for (Category key : map.keySet()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(key.getName().split("\\|")[0]);
                List<Charge> chargeList = map.get(key);

                Map<String, Double> monthMap = new HashMap();

                for (Charge categoryCharge : chargeList) {
                    LocalDate date = categoryCharge.getDate();

                    if (mode.equals("All")) {
                        if (monthMap.containsKey(date.getYear() + " - " + date.getMonth().toString())) {
                            monthMap.put(date.getYear() + " - " + date.getMonth().toString(), monthMap.remove(date.getMonth().toString()) + categoryCharge.getCost() * categoryCharge.getUnits());
                        } else {
                            monthMap.put(date.getYear() + " - " + date.getMonth().toString(), categoryCharge.getCost() * categoryCharge.getUnits());
                        }
                    } else if (mode.equals("Year") && date.getYear() == currentDate.getYear()) {
                        if (monthMap.containsKey(date.getMonth().toString())) {
                            monthMap.put(date.getMonth().toString(), monthMap.remove(date.getMonth().toString()) + categoryCharge.getCost() * categoryCharge.getUnits());
                        } else {
                            monthMap.put(date.getMonth().toString(), categoryCharge.getCost() * categoryCharge.getUnits());
                        }
                    }

                }

                List<Integer> sortedYears = new ArrayList<>(years);
                Collections.sort(sortedYears);

                if (mode.equals("Year")) {
                    for (Month month : Month.values()) {
                        if (month.getValue() > currentDate.getMonthValue()) {
                            break;
                        }
                        String monthString = month.toString();
                        double money = monthMap.containsKey(monthString) ? monthMap.get(monthString) : 0.0;
                        series.getData().add(new XYChart.Data(monthString, money));
                    }
                } else {
                    for (int year : sortedYears) {
                        for (Month month : Month.values()) {
                            if (year == currentDate.getYear() && month.getValue() > currentDate.getMonthValue()) {
                                break;
                            }
                            String monthString = month.toString();
                            double money = monthMap.containsKey(year + " - " + monthString) ? monthMap.get(year + " - " + monthString) : 0.0;
                            series.getData().add(new XYChart.Data(year + " - " + monthString, money));
                        }
                    }
                }

                lineChart.getData().add(series);
                style = style + "CHART_COLOR_" + styleIndex + ": " + key.getName().split("\\|")[1].replace("0x", "#") + ";";
                styleIndex++;

            }

            lineChart.setStyle(style);
            
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void openStatisticsPane(ActionEvent event) {
        try {
            Pane addExpensePane = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            statisticsMain.getScene().setRoot(addExpensePane);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void print(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/Print.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            Stage stage = new Stage();
            stage.setTitle("MoneyMate - Print");
            stage.setMinHeight(600);
            stage.setMinWidth(600);
            stage.setScene(scene);
            flowPane.prefWidthProperty().bind(scrollPane.widthProperty().add(400));
            printButton.setVisible(false);
            mode.setVisible(false);
            WritableImage image = flowPane.snapshot(new SnapshotParameters(), null);
            flowPane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
            printButton.setVisible(true);
            mode.setVisible(true);

            PrintController pc = fxmlLoader.getController();
            pc.setImage(image);
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
