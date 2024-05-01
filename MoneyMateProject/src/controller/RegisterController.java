package controller;

import java.io.IOException;
import javafx.scene.image.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;

import model.Persona;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/*
class PersonListCell extends ListCell<Persona>{
    @Override
    protected void updateItem(Persona item, boolean empty){
        super.updateItem(item, empty);
        if(item == null || empty) setText(null); 
        else {Image avatar; 
            //avatar = new Image("images/lloroso.png", 20,20,false,true);
            //setGraphic(new ImageView(avatar));
            setText(item.getApellidos() + "," + item.getNombre());
        }
    }
}
*/

public class RegisterController implements Initializable {
    //=========================================================
    @FXML
    private Button register;
    @FXML
    private Pane regPane;
    //=========================================================
    // ListView and observable data type MUST be the same.
    //private ObservableList<Persona> myObservablePersonaList = null; // Collection linked to table.


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
  
        
    }
    
    //void addAction(ActionEvent event) {
    //   
    //}
    //void deleteAction(ActionEvent event) {
        //================================================
        // delete from the list
        
        //================================================
    //}
    
    @FXML
    private void openMainPane(MouseEvent event) {
        try {
            Pane mainPage = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            regPane.getChildren().setAll(mainPage);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}
