/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluemarveltunes;

//import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
//import java.time.Duration;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.*;

/**
 *Functionality
 * @author King K
 */
public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    //connects to GUI
    @FXML
    private MediaView mediaView;
    
    
    private String filePath;
   
    @FXML
    private Slider slider;
    
    @FXML
    private Slider timeSlider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //choose a file
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Choose a .mp4 file", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            //no pop up window
            File file = fileChooser.showOpenDialog(null);
            //MP4 file path
            filePath = file.toURI().toString();
            
            if(filePath != null){
            //actual audio
                Media media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                
                //play into media view
                mediaView.setMediaPlayer(mediaPlayer);
                //set widith and height of video player
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
               
                //set volumn
                slider.setValue(mediaPlayer.getVolume() * 100);
                //ensure volumn is within valid rangee
                slider.valueProperty().addListener(new InvalidationListener(){
                @Override
                public void invalidated(Observable observable){
                    mediaPlayer.setVolume(slider.getValue()/100);
                }

            });
                
                //duration time of video
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        //sets slider to time left
                        timeSlider.setValue(newValue.toSeconds());}
                    
                });
                
                timeSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                    }
                
            });

                //play media object
                mediaPlayer.play();
                
            }
    }
    //tag fxml action events
    @FXML
    private void pause(ActionEvent event){
        mediaPlayer.pause();
    }
    
    //tag fxml action events
    @FXML
    private void play(ActionEvent event){
        mediaPlayer.play();
        //resume normal speed
        mediaPlayer.setRate(1);
    }
    
    //tag fxml action events
    @FXML
    private void fastx(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    
    //tag fxml action events
    @FXML
    private void slow(ActionEvent event){
        mediaPlayer.setRate(.75);
    }
    //tag fxml action events
    @FXML
    private void slowx(ActionEvent event){
        mediaPlayer.setRate(.5);
    }
    //tag fxml action events
    @FXML
    private void exit(ActionEvent event){
        System.exit(0);
    }
    
    //tag fxml action events
    @FXML
    private void stop(ActionEvent event){
        mediaPlayer.stop();
    }
    
    //tag fxml action events
    @FXML
    private void fast(ActionEvent event){
        mediaPlayer.setRate(1.5);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
