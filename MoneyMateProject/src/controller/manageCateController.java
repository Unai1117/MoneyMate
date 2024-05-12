/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import model.*; 
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author unai
 */
public class manageCateController implements Initializable {

    @FXML
    private TextField nameTextField;
    
    @FXML
    private ListView<String> categoriesListView;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    
    private List<Category> categorias;
    
    private Acount acount; 
    @FXML
    private TextField descriptionTextField;
    @FXML
    private ColorPicker colorP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            acount = Acount.getInstance(); 
            categorias = acount.getUserCategories();
            for(int i = 0; i < categorias.size(); i++){
                String[] names = categorias.get(i).getName().split("\\|");
                categoriesListView.getItems().add(names[0]); 
            }
        } catch (Exception e){
            System.out.println(e); 
        }
 
        //nothing selected disable delete button 
        deleteButton.disableProperty().bind(Bindings.equal(-1, categoriesListView.getSelectionModel().selectedIndexProperty()));
    }    

    @FXML
    private void addAction(ActionEvent event) {
        try{
            if ((!nameTextField.getText().isEmpty())
                && (nameTextField.getText().trim().length() != 0)
                && (!descriptionTextField.getText().isEmpty())
                && (descriptionTextField.getText().trim().length() != 0)) {
            //============================================
            // add to the list
            categoriesListView.getItems().add(nameTextField.textProperty().getValue());
            //color for the category
            Color selColor = colorP.getValue();
            String colorString = selColor.toString();
            //add new category
            acount.registerCategory(nameTextField.textProperty().getValue() + "|" + colorString, descriptionTextField.textProperty().getValue());

            //============================================
            // empty text fields after adding to the list
            nameTextField.clear();
            descriptionTextField.clear();
             
            //change focus to inital textfield
            nameTextField.requestFocus();
            addButton.setDisable(true);
            }  
        } catch(Exception e){
            System.out.println(e); 
        }
        
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        
    }
    
}
