package com.example.javafxtutorial;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PrepareController implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myShip.getItems().addAll(Ship);
        gameMode.getItems().addAll(Mode);
        playerBoard.setLayoutX(160);
        playerBoard.setLayoutY(250);

        Image image0 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\carrier-horizontal.png",245,57,false,false);
        Image image1 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\battleship-horizontal.png");
        Image image2 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\cruiser-horizontal.png");
        Image image3 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\submarine-horizontal.png");
        Image image4 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\destroyer-horizontal.png");

        ships = new ImageView[5];
        ships[0] = new ImageView(image0);
        ships[1] = new ImageView(image1);
        ships[2] = new ImageView(image2);
        ships[3] = new ImageView(image3);
        ships[4] = new ImageView(image4);
        for(int i = 0;i < 5;i++){
            ships[i].setPreserveRatio(true);
        }

        ships[0].setFitWidth(200);
        ships[1].setFitWidth(160);
        ships[2].setFitWidth(120);
        ships[3].setFitWidth(120);
        ships[4].setFitWidth(80);

        for(int i = 0;i < 5;i++){
            ShipsToBePlaced.add(ships[i],0,i);
        }

        ShipsToBePlaced.setOnMousePressed(mouseEvent -> {
            ImageView source = (ImageView) mouseEvent.getTarget();
            System.out.println(GridPane.getRowIndex(source));

            source.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("DragDetected");
                    Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                    ClipboardContent cbContent = new ClipboardContent();
                    cbContent.putImage(source.getImage());

                    db.setContent(cbContent);
                    // source.setVisible(false);
                    mouseEvent.consume();
                }
            });

            playerBoard.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if(dragEvent.getGestureSource() != playerBoard && dragEvent.getDragboard().hasImage()) {
                            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            System.out.println("DragOver");
                        }

                        dragEvent.consume();
                    }
                });

            playerBoard.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if(dragEvent.getGestureSource() != playerBoard && dragEvent.getDragboard().hasImage()) {
                            source.setVisible(false);
                            playerBoard.setOpacity(0.7);
                            System.out.println("Drag Entered");
                        }

                        dragEvent.consume();
                    }
                });

            playerBoard.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        source.setVisible(true);
                        playerBoard.setOpacity(1);
                        dragEvent.consume();
                    }
                });

            playerBoard.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        Dragboard db = dragEvent.getDragboard();
                        boolean success = false;
                        Cell cell = (Cell)dragEvent.getPickResult().getIntersectedNode();

                        if(db.hasImage() && cell != null ) {
                            Integer cIndex = cell.x;
                            Integer rIndex = cell.y;

                            int y = cIndex == null ? 0 : cIndex;
                            int x = rIndex == null ? 0 : rIndex;
                            System.out.println(x);
                            System.out.println(y);
                            ImageView image = new ImageView(db.getImage());

                            if(source.getFitWidth() == 200) playerBoard.placeShip(new Ship(5,false),x,y);
                            else if(source.getFitWidth() == 160) playerBoard.placeShip(new Ship(4,false),x,y);
                            else if(source.getFitWidth() == 120) playerBoard.placeShip(new Ship(3,false),x,y);
                            else if(source.getFitWidth() == 80) playerBoard.placeShip(new Ship(2,false),x,y);

                            success = true;
                        }
                        dragEvent.setDropCompleted(success);
                        dragEvent.consume();
                    }
                });

            source.setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if(dragEvent.getTransferMode() == TransferMode.MOVE){
                            source.setVisible(false);
                            System.out.println("DragDone");
                        }
                        dragEvent.consume();
                    }
                });
        });



        ShipsToBePlaced.setLayoutX(750);
        ShipsToBePlaced.setLayoutY(330);
        ShipsToBePlaced.setVgap(30);

        root.getChildren().add(playerBoard);
        root.getChildren().add(ShipsToBePlaced);
    }

    @FXML
    private ChoiceBox<String> myShip;
    private String[] Ship = {"Destroyer", "Cruiser", "Marine"};

    @FXML
    private ChoiceBox<String> gameMode;
    private String[] Mode = {"Easy","Hard"};

    @FXML
    private AnchorPane root;

    Board playerBoard = new Board(false);
    public ImageView[] ships;
    public GridPane ShipsToBePlaced = new GridPane();

}
