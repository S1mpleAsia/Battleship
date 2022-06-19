package com.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private Media media;
    private MediaPlayer mediaPlayer;
    @FXML
    private ImageView soundIcon;
    @FXML
    private AnchorPane highScore;

    private Image soundOn = new Image(getClass().getResource("Image") + "sound.png");
    private Image soundOff = new Image(getClass().getResource("Image") + "unsound.png");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        soundIcon.setImage(soundOn);

        songs = new ArrayList<File>();
        directory = new File("src/main/resources/com/example/javafxtutorial/Music");
        files = directory.listFiles();

        if(files != null) {
            songs.addAll(Arrays.asList(files));
        }

        media = new Media(songs.get(0).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    @FXML
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("preparationscreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void soundEffect(ActionEvent event) {
        Image sound = soundIcon.getImage();

        if(Objects.equals(sound.getUrl(), soundOn.getUrl())) {
            soundIcon.setImage(soundOff);
            mediaPlayer.pause();
        }
        else {
            soundIcon.setImage(soundOn);
            mediaPlayer.play();
        }
    }
    @FXML
    public void showHighScore(ActionEvent event) throws IOException {
        AnchorPane root = new AnchorPane();
        Stage stage = new Stage();
        Scene scene = new Scene(root,300,400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("High Score");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}
