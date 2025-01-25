package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloController {

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
           imageDetailsAction(selectedFile, image);
       }
       isGray = false;
   }

   @FXML
    public Button makeImageGray;

   private boolean isGray = true;

   public void makeImageGrayAction (ActionEvent event){
       if (imageView.getImage() != null){
           ColorAdjust color = new ColorAdjust();
           if (!isGray){
               color.setSaturation(-1);
               isGray = true;
               makeImageGray.setText("UNDO GRAY");

           }else{
               color.setSaturation(0);
               isGray = false;
           }
           imageView.setEffect(color);
       }
   }

   @FXML
    public Text imagetext;

   public void imageDetailsAction(File file, Image image){
       if(imageView.getImage() != null){
           String filePath = file.getAbsolutePath();
           double fileSizeInKB = (file.length())/1024.0;
           String details = (
                   "path:" + filePath +
                   "; resolution" + fileSizeInKB
           );
           imagetext.setText(details);
       }
   }
   @FXML
    public Button showChannelsButton;

   public void showChannelAction(ActionEvent event){
       if(imageView.getImage() != null){
           Stage colorChannels = new Stage();
           colorChannels.setTitle("Colour Channels");

           TabPane tabPane = new TabPane();

           Tab redTab = new Tab("red", createImageViewForChannel("red"));
           Tab greenTab = new Tab("green", createImageViewForChannel("green"));
           Tab blueTab = new Tab("blue", createImageViewForChannel("blue"));

           tabPane.getTabs().addAll(redTab, greenTab, blueTab);

           VBox layout = new VBox(tabPane);
           Scene scene = new Scene(layout, 600, 400);
           colorChannels.setScene(scene);
           colorChannels.show();
       }
   }

   public ImageView createImageViewForChannel(String channel) {
       int width = (int) imageView.getImage().getWidth();
       int height = (int) imageView.getImage().getHeight();

       javafx.scene.image.PixelReader pixelReader = imageView.getImage().getPixelReader(); //reads the color data of each pixel in the og img
       javafx.scene.image.WritableImage writableImage = new javafx.scene.image.WritableImage(width, height); //an empty img with the same dimensions
       javafx.scene.image.PixelWriter pixelWriter = writableImage.getPixelWriter(); //modifies the empty image

       //tryna loop through each pixel
        for(int y = 0; y < height; y++ ){
            for (int x = 0; x < width; x++){
                javafx.scene.paint.Color colour = pixelReader.getColor(x, y);
                //get only the specific color channels and set the others to 0
                double red = (channel.equals("red")) ? colour.getRed(): 0;
                double green = (channel.equals("green")) ? colour.getGreen() : 0;
                double blue = (channel.equals("blue")) ? colour.getBlue(): 0;
                pixelWriter.setColor(x, y, new javafx.scene.paint.Color(red, green, blue, colour.getOpacity())); //write the new pixel color to the writable img
            }
        }
        return new ImageView(writableImage); //create an image view for the modified writable image
   }
}