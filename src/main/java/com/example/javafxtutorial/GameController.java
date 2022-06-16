package com.example.javafxtutorial;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

import static com.example.javafxtutorial.PrepareController.mode;
import static com.example.javafxtutorial.PrepareController.playerBoard;

public class GameController implements Initializable {
    private Board myBoard = playerBoard;
    @FXML
    private AnchorPane gameRoot;
    public Board enemyBoard = new Board(true);
    private  boolean enemyTurn = false;
    private Random random = new Random();
    private Cell rootTarget;
    private Queue<Cell> nextTarget = new LinkedList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myBoard.setLayoutX(80);
        myBoard.setLayoutY(200);
        enemyBoard.setLayoutX(600);
        enemyBoard.setLayoutY(200);

        int type = 5;
        while(type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if(enemyBoard.placeShip(new Ship(type,Math.random() < 0.5),x,y)) {
                type--;
            }
        }

        gameRoot.getChildren().add(myBoard);
        gameRoot.getChildren().add(enemyBoard);

        startGame();
    }

    public void startGame() {
        enemyBoard.setOnMousePressed(event -> {
            Cell cell = (Cell) event.getTarget();
            if(cell.wasShot) return;

            cell.shoot(true);
            enemyTurn = true;

            if(enemyBoard.ships == 0) {
                System.out.println("You Win");
                System.exit(0);
            }

            enemyMove();
        });
    }

    private void enemyMove() {
        if(Objects.equals(mode, "Easy")) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            while(playerBoard.getCell(x,y).wasShot) {
                x = random.nextInt(10);
                y = random.nextInt(10);
            }

            Cell cell = playerBoard.getCell(x,y);
            cell.shoot(false);
            enemyTurn = false;

            if(playerBoard.ships == 0) {
                System.out.println("You lose");
                System.exit(0);
            }
        }

        else if(Objects.equals(mode, "Medium")){

            // VU ANH TUAN - Hard Mode
            if(nextTarget.isEmpty()){
                int x,y;
                do{
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                } while(playerBoard.getCell(x, y).wasShot);

                rootTarget = playerBoard.getCell(x,y);

                System.out.println(rootTarget.x);
                System.out.println(rootTarget.y);

                if(rootTarget.ship != null){
                    if(rootTarget.x == 0){
                        Cell neighbor = new Cell(rootTarget.x+1, rootTarget.y,playerBoard);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    else if (rootTarget.x == 9){
                        Cell neighbor = new Cell(rootTarget.x-1, rootTarget.y,playerBoard);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    else {
                        Cell[] neighbors = {
                                new Cell(rootTarget.x+1, rootTarget.y,playerBoard),
                                new Cell(rootTarget.x-1, rootTarget.y,playerBoard)
                        };
                        for(Cell neighbor : neighbors)
                            if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }
                }

                rootTarget.shoot(false);
                enemyTurn = false;
            }
            else {
                Cell cell;
                do{
                    cell = nextTarget.poll();
                } while (playerBoard.getCell(cell.x, cell.y).wasShot);
                Cell target = playerBoard.getCell(cell.x, cell.y);

                if (target.ship != null){
                    if((target.x < rootTarget.x) && (target.x>0)){
                        Cell neighbor = new Cell(target.x-1, target.y, playerBoard);
                        if(!neighbor.wasShot) nextTarget.offer(neighbor);
                    }
                    else if ((target.x > rootTarget.x) && (target.x <9)){
                        Cell neighbor = new Cell(target.x+1, target.y, playerBoard);
                        if(!neighbor.wasShot) nextTarget.offer(neighbor);
                    }
                }

                target.shoot(false);
                enemyTurn = false;
            }

            if(playerBoard.ships == 0) {
                System.out.println("You lose");
                System.exit(0);
            }

        }
        else if (Objects.equals(mode, "Hard")){
            if(nextTarget.isEmpty()){
                int x,y;
                do{
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                } while((playerBoard.getCell(x, y).wasShot) || (playerBoard.getCell(x, y).ship == null));

                rootTarget = playerBoard.getCell(x,y);

                System.out.println(rootTarget.x);
                System.out.println(rootTarget.y);

                if(rootTarget.ship != null){
                    if(rootTarget.x == 0){
                        Cell neighbor = new Cell(rootTarget.x+1, rootTarget.y,playerBoard);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    else if (rootTarget.x == 9){
                        Cell neighbor = new Cell(rootTarget.x-1, rootTarget.y,playerBoard);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    else {
                        Cell[] neighbors = {
                                new Cell(rootTarget.x+1, rootTarget.y,playerBoard),
                                new Cell(rootTarget.x-1, rootTarget.y,playerBoard)
                        };
                        for(Cell neighbor : neighbors)
                            if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }
                }

                rootTarget.shoot(false);
                enemyTurn = false;
            }
            else {
                Cell cell;
                do{
                    cell = nextTarget.poll();
                } while (playerBoard.getCell(cell.x, cell.y).wasShot);
                Cell target = playerBoard.getCell(cell.x, cell.y);

                if (target.ship != null){
                    if((target.x < rootTarget.x) && (target.x>0)){
                        Cell neighbor = new Cell(target.x-1, target.y, playerBoard);
                        if(!neighbor.wasShot) nextTarget.offer(neighbor);
                    }
                    else if ((target.x > rootTarget.x) && (target.x <9)){
                        Cell neighbor = new Cell(target.x+1, target.y, playerBoard);
                        if(!neighbor.wasShot) nextTarget.offer(neighbor);
                    }
                }

                target.shoot(false);
                enemyTurn = false;
            }

            if(playerBoard.ships == 0) {
                System.out.println("You lose");
                System.exit(0);
            }
        }
    }
}
