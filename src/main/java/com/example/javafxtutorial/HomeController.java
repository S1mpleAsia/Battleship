package com.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;



public class HomeController implements Initializable {
    // Sử dụng cho Âm thanh
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private Media media;
    private static MediaPlayer mediaPlayer;

    // Sử dụng cho hình ảnh
    @FXML
    private ImageView soundIcon;

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
    /**
     * Thoát chương trình
     */
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    /**
     * Chuyển sang Sence 2
     */
    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("preparationscreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    /**
     * Thiết lập Bật/Tắt âm thanh
     */
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
    public static void stopPlayingSound() {
        mediaPlayer.stop();
    }
    @FXML
    public void showHighScore(ActionEvent event) {
        AnchorPane root = new AnchorPane();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("application.css")));

        Image img = new Image(getClass().getResource("Image") + "leaderboard.png");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0,1.0,true,true,true,true));
        Background bGround = new Background(bImg);
        root.setBackground(bGround);

        File file = new File("src/main/resources/com/example/javafxtutorial/highscore.txt");
        Label[] score = new Label[5];
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(56);
        gridPane.setLayoutY(61);

        try {
            Scanner scanner = new Scanner(file);
            for(int i = 0;i < 5;i++) {
                score[i] = new Label();
                if(scanner.hasNextInt()) {
                    score[i].setText("" + scanner.nextInt());
                    score[i].setId("text");
                    gridPane.add(score[i],3,i+1);
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        root.getChildren().add(gridPane);

        Button reset = new Button();
        reset.setText("Reset Point");
        reset.setId("reset_btn");
        reset.setLayoutX(120);
        reset.setLayoutY(520);
        root.getChildren().add(reset);
        reset.setOnAction(actionEvent -> {
            int[] A = new int[5];
            for(int i = 0;i < 5;i++){
                score[i].setText("0");
                A[i] = 0;
            }
            String s = "" + A[4] + " " + A[3] + " " + A[2] + " " + A[1] + " " + A[0];

            try {
                FileWriter fw = new FileWriter("src/main/resources/com/example/javafxtutorial/highscore.txt");
                fw.write(s);
                fw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        stage.setScene(scene);
        Image icon = new Image(getClass().getResource("Image") + "score-icon.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("High Score");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
