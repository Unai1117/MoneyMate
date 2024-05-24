/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
                Map<String, Double> auxMap = new HashMap();

                for (Charge charge : chargeList) {
                    series.getData().add(new XYChart.Data(charge.getDate().getMonth().toString() + charge.getDate().getYear(), charge.getCost() * charge.getUnits()));
                    
                }
                
                lineChart.getData().add(series);
            }

            /*List<String> chartColors = new ArrayList<>();

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
