package com.example.javafxtutorial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Battleship Game");
            Parent root = FXMLLoader.load(getClass().getResource("homescreen.fxml"));
            Scene scene = new Scene(root,400,500);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("application.css")));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
