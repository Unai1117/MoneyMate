/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.FileChooser;
import 

/**
 *
 * @author sovacu
 */
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class Utils {
    /**
     *  A password is considered valid if follows an accepted email syntax:
     *  name@domain.com
     * @param email String which contains the email to check  
     * @return  True if the email is valid. False otherwise.  
     */
    
    public static  Boolean checkEmail (String email)
    {   if(email == null){
          return false; 
        }
       // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
     /**
     * 
     * A password is considered valid if it is combination of 8 to 15 numbers or 
     * letters, without blank spaces.
     *
     * @param password String which contains the password to check  
     * @return  True if the password is valid. False otherwise.   
     */ 
    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        // Regex to check valid password. 
        String regex =  "^[A-Za-z0-9]{8,15}$"; 
  
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    
    }
    
    public File codeOpenFiles(Stage root){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.jpeg", "*.png", "*.gif");
        
        return fileChooser.showOpenDialog(root); 
    }
    
}
