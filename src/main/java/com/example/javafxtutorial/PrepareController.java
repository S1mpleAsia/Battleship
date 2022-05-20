package com.example.javafxtutorial;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class PrepareController implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shipArray.addAll(Arrays.asList(shipName));
        myShip.getItems().addAll(Ship);
        gameMode.getItems().addAll(Mode);
        playerBoard.setLayoutX(160);
        playerBoard.setLayoutY(250);
        btnRandom.setGraphic(new ImageView("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\random.png"));
        btnDelete.setGraphic(new ImageView("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\trash.png"));
        btnRotate.setGraphic(horizontal);


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
                            ShipsToBePlaced.getChildren().remove(source);
                            playerBoard.setOpacity(0.7);
                            System.out.println("Drag Entered");
                        }

                        dragEvent.consume();
                    }
                });

            playerBoard.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        ShipsToBePlaced.getChildren().add(source);
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

                        if(db.hasImage() && cell != null && cell.ship == null) {
                            Integer cIndex = cell.x;
                            Integer rIndex = cell.y;

                            int y = cIndex == null ? 0 : cIndex;
                            int x = rIndex == null ? 0 : rIndex;
                            System.out.println(x);
                            System.out.println(y);

                            if(source.getFitWidth() == 200){
                                playerBoard.placeShip(new Ship(5,false),x,y);
                                shipArray.remove(ShipType.CARRIER.name);
                            }
                            else if(source.getFitWidth() == 160){
                                playerBoard.placeShip(new Ship(4,false),x,y);
                                shipArray.remove(ShipType.BATTLESHIP.name);
                            }
                            else if(source.getFitWidth() == 120 && source.getImage() == image2){
                                Ship ship = new Ship(3,false);
                                playerBoard.placeShip(ship,x,y);
                                shipArray.remove(ShipType.CRUISER.name);
                                playerBoard.setShipImages(ship,x,y);
                            }

                            else if(source.getFitWidth() == 120 && source.getImage() == image3){
                                Ship ship = new Ship(3,false);
                                playerBoard.placeShip(ship,x,y);
                                shipArray.remove(ShipType.SUBMARINE.name);
                                playerBoard.setShipImages(ship,x,y);
                            }


                            else if(source.getFitWidth() == 80){
                                playerBoard.placeShip(new Ship(2,false),x,y);
                                shipArray.remove(ShipType.DESTROYER.name);
                            }

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
                            ShipsToBePlaced.getChildren().remove(source);
                            System.out.println("DragDone");
                        }
                        dragEvent.consume();
                    }
                });
        });

        ShipsToBePlaced.setLayoutX(750);
        ShipsToBePlaced.setLayoutY(330);
        ShipsToBePlaced.setVgap(30);
        ShipsToBePlaced.setHgap(30);
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

    public static Board playerBoard = new Board(false);
    private ImageView[] ships;
    private GridPane ShipsToBePlaced = new GridPane();
    private boolean vertical = false;

    @FXML
    private Button btnRandom;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRotate;

    private ArrayList<String> shipArray = new ArrayList<String>();
    private final String[] shipName = {"Carrier","Battleship","Cruiser","Submarine","Destroyer"};
    private final ImageView horizontal = new ImageView("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\horizontal.png");
    private final ImageView rotate = new ImageView("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\rotate.png");
    @FXML
    private void randomShip(ActionEvent event) {
        Random random = new Random();

        while(!shipArray.isEmpty()) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            System.out.println("x:" + x + " " + "y:" + y);

            if(playerBoard.getCell(x,y).ship != null) continue;
            for(String str : shipArray) {
                if(str.equals(ShipType.CARRIER.name)){
                    boolean check = playerBoard.placeShip(new Ship(ShipType.CARRIER.size,Math.random() < 0.5),x,y);
                    if(check) shipArray.remove(ShipType.CARRIER.name);
                    ShipsToBePlaced.getChildren().remove(ships[0]);
                    break;
                }
                else if(str.equals(ShipType.BATTLESHIP.name)){
                    boolean check = playerBoard.placeShip(new Ship(ShipType.BATTLESHIP.size,Math.random() < 0.5),x,y);
                    if(check) shipArray.remove(ShipType.BATTLESHIP.name);
                    ShipsToBePlaced.getChildren().remove(ships[1]);
                    break;
                }

                else if(str.equals(ShipType.CRUISER.name)){
                    boolean check = playerBoard.placeShip(new Ship(ShipType.CRUISER.size,Math.random() < 0.5),x,y);
                    if(check) shipArray.remove(ShipType.CRUISER.name);
                    ShipsToBePlaced.getChildren().remove(ships[2]);
                    break;
                }

                else if(str.equals(ShipType.SUBMARINE.name)){
                    boolean check = playerBoard.placeShip(new Ship(ShipType.SUBMARINE.size,Math.random() < 0.5),x,y);
                    if(check) shipArray.remove(ShipType.SUBMARINE.name);
                    ShipsToBePlaced.getChildren().remove(ships[3]);
                    break;
                }

                else if(str.equals(ShipType.DESTROYER.name)){
                    boolean check = playerBoard.placeShip(new Ship(ShipType.DESTROYER.size,Math.random() < 0.5),x,y);
                    if(check) shipArray.remove(ShipType.DESTROYER.name);
                    ShipsToBePlaced.getChildren().remove(ships[4]);
                    break;
                }
            }
        }
    }
    @FXML
    private void resetShip(ActionEvent event) {
        for(int i = 0;i < 10;i++){
            for(int j = 0;j < 10;j++){
                Cell cell = playerBoard.getCell(i,j);
                if(cell.ship != null){
                    cell.ship = null;
                    cell.setFill(Color.LIGHTGRAY);
                    cell.setStroke(Color.BLACK);
                }
            }
        }

        if(!shipArray.contains("Carrier")) shipArray.add("Carrier");
        if(!shipArray.contains("Battleship")) shipArray.add("Battleship");
        if(!shipArray.contains("Cruiser")) shipArray.add("Cruiser");
        if(!shipArray.contains("Submarine")) shipArray.add("Submarine");
        if(!shipArray.contains("Destroyer")) shipArray.add("Destroyer");

        ShipsToBePlaced.getChildren().removeAll(ships);
        for(int i = 0;i < 5;i++){
            ShipsToBePlaced.add(ships[i],0,i);
        }
    }

    @FXML
    private void rotateShip(ActionEvent event) throws IOException {
//        if(btnRotate.getGraphic() == horizontal) btnRotate.setGraphic(rotate);
//        else if(btnRotate.getGraphic() == rotate) btnRotate.setGraphic(horizontal);
//
//        if(!vertical){
//            ShipsToBePlaced.getChildren().removeAll(ships);
//            Image image0 = new Image(String.valueOf(this.getClass().getResource("com/example/javafxtutorial/ShipImage/carrier.png")));
//            Image image1 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\battleship.png");
//            Image image2 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\cruiser.png");
//            Image image3 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\submarine.png");
//            Image image4 = new Image("D:\\Java Project\\JavaFxTutorial\\src\\main\\resources\\com\\example\\javafxtutorial\\ShipImage\\destroyer.png");
//
//            ships[0] = new ImageView(image0);
//            ships[1] = new ImageView(image1);
//            ships[2] = new ImageView(image2);
//            ships[3] = new ImageView(image3);
//            ships[4] = new ImageView(image4);
//
//            for(int i = 0;i < 5;i++){
//                ships[i].setPreserveRatio(true);
//            }
//
//            ships[0].setFitHeight(200);
//            ships[1].setFitHeight(160);
//            ships[2].setFitHeight(120);
//            ships[3].setFitHeight(120);
//            ships[4].setFitHeight(80);
//
//            for(int i = 0;i < 5;i++){
//                    ShipsToBePlaced.add(ships[i],i,0);
//            }
//
//            vertical = true;
//        }
//
//        else if(vertical){
//            vertical = false;
//        }

        Parent root = FXMLLoader.load(getClass().getResource("gamescreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
