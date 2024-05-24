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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author jorge
 */
public class StatisticsController implements Initializable {

    @FXML
    private VBox statisticsMain;
    @FXML
    private LineChart lineChart;

    private List<Charge> userCharges;
    private List<Category> userCategories;
    private double totalMoney;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDate currentDate = LocalDate.now();
        lineChart.setCreateSymbols(false);
        String style = "";
        int styleIndex = 1;
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

            for (Category key : map.keySet()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(key.getName().split("\\|")[0]);
                List<Charge> chargeList = map.get(key);

                Map<String, Double> monthMap = new HashMap();

                for (Charge categoryCharge : chargeList) {
                    LocalDate date = categoryCharge.getDate();

                    if (date.getYear() == currentDate.getYear()) {
                        if (monthMap.containsKey(date.getMonth().toString())) {
                            monthMap.put(date.getMonth().toString(), monthMap.remove(date.getMonth().toString()) + categoryCharge.getCost() * categoryCharge.getUnits());
                        } else {
                            monthMap.put(date.getMonth().toString(), categoryCharge.getCost() * categoryCharge.getUnits());
                        }
                    }
                }

                for (Month month : Month.values()) {
                    if (month.getValue() > currentDate.getMonthValue()) {
                        break;
                    }
                    String monthString = month.toString();
                    double money = monthMap.containsKey(monthString) ? monthMap.get(monthString) : 0.0;
                    series.getData().add(new XYChart.Data(monthString, money));
                }

                lineChart.getData().add(series);
                style = style + "CHART_COLOR_" + styleIndex + ": " + key.getName().split("\\|")[1].replace("0x", "#") + ";";
            }

            lineChart.setStyle(style);

            /*for (Category key : map.keySet()) {

                XYChart.Series series = new XYChart.Series();
                series.setName(key.getName().split("\\|")[0]);

                List<Charge> chargeList = map.get(key);
                Map<String, Double> auxMap = new HashMap();

                TreeMap<LocalDate, List<Charge>> sortedCharges = new TreeMap<>();

                for (Charge userCharge : chargeList) {
                    sortedCharges.computeIfAbsent(userCharge.getDate(), k -> new ArrayList<>()).add(userCharge);
                }
                

                for (Map.Entry<LocalDate, List<Charge>> entry : sortedCharges.entrySet()) {
                    List<Charge> chargesOnDate = entry.getValue();
                    // We add the sorted charges to the layout
                    for (Charge sortedCharge : chargesOnDate) {
                        series.getData().add(new XYChart.Data(sortedCharge.getDate().getYear() + sortedCharge.getDate().getMonth().toString(), sortedCharge.getCost() * sortedCharge.getUnits()));

                    }
                }

                lineChart.getData().add(series);
            }

            List<String> chartColors = new ArrayList<>();

            Map<String, List<Charge>> map = new HashMap();
            for (int i = 0; i < userCharges.size(); i++) {
                Charge charge = userCharges.get(i);
                totalMoney += charge.getCost() * charge.getUnits();
                Category category = charge.getCategory();
                String key = category.getName() + "|" + charge.getDate().getYear() + charge.getDate().getMonth();
                if (map.containsKey(key)) {
                    List<Charge> aux = map.remove(key);
                    aux.add(charge);
                    map.put(key, aux);
                } else {
                    List<Charge> aux = new ArrayList();
                    aux.add(charge);
                    map.put(key, aux);
                }
            }

            for (String key : map.keySet()) {
                double categoryTotal = 0;
                List<Charge> charges = map.get(key);
                for (int i = 0; i < charges.size(); i++) {
                    categoryTotal += charges.get(i).getCost() * charges.get(i).getUnits();
                }

                chartColors.add(key.split("\\|")[1]);
                userCategoriesData.add(new LineChart.Data(key.split("\\|")[0], categoryTotal, key.split("\\|")[2]));

            }
            
            System.out.println(userCategoriesData);

            lineChart.setData(userCategoriesData); */
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

}
