/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser.ExtensionFilter;

public class Utils {

    /**
     * 
     *
     * @param email String which contains the email to check
     * @return True if the email is valid. False otherwise.
     */
    public static Boolean checkEmail(String email) {
        if (email == null) {
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
     * 
     *
     * @param password String which contains the password to check
     * @return True if the password is valid. False otherwise.
     */
    public static Boolean checkPassword(String password) {

        // If the password is empty , return false 
        if (password == null) {
            return false;
        }
        // Regex to check valid password. 
        String regex = "^[A-Za-z0-9]{8,15}$";

        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public static Image codeOpenFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image scanedImage = new Image(selectedFile.toURI().toString());
            return scanedImage;
        } else {
            return null;
        }
    }

    public static void iconToMenuItem(Image image, MenuItem menuItem) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        menuItem.setGraphic(imageView);
    }

}
