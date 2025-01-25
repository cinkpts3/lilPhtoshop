package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;


    @FXML
    private Button chooseAFileButton;

   @FXML
   public ImageView imageView;

   public void openAnImageAction(ActionEvent event){
       FileChooser fileChoose = new FileChooser();
       fileChoose.setTitle("open image file");

       fileChoose.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("Image Files", "*png", "*jpg", "*jpeg")

       );

       Stage stage = (Stage) chooseAFileButton.getScene().getWindow();
       File selectedFile = fileChoose.showOpenDialog(stage);

       if (selectedFile != null){
           Image image = new Image(selectedFile.toURI().toString());
           imageView.setImage(image);
       }
   }

}