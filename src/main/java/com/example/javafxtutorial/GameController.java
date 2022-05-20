package com.example.javafxtutorial;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.javafxtutorial.PrepareController.playerBoard;

public class GameController implements Initializable {
    private Board myBoard = playerBoard;
    @FXML
    private AnchorPane gameRoot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myBoard.setLayoutX(160);
        myBoard.setLayoutY(200);
        gameRoot.getChildren().add(myBoard);
    }
}
