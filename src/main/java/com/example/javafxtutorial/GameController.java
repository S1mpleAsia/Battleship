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
    private LinkedList<Cell> nextTarget = new LinkedList<>();


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

            // VU ANH TUAN - Medium Mode
            if(nextTarget.isEmpty()){
                int x,y;
                do{
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                } while(playerBoard.getCell(x, y).wasShot);

                rootTarget = playerBoard.getCell(x,y);

                System.out.println(rootTarget.x);
                System.out.println(rootTarget.y);

                rootTarget.shoot(false);

                if(rootTarget.ship != null){
                    if((rootTarget.x+1) <= 9){
                        Cell neighbor = playerBoard.getCell(rootTarget.x+1, rootTarget.y);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    if ((rootTarget.y+1) <= 9){
                        Cell neighbor = playerBoard.getCell(rootTarget.x, rootTarget.y+1);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    if ((rootTarget.x-1) >= 0){
                        Cell neighbor = playerBoard.getCell(rootTarget.x-1, rootTarget.y);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }


                    if ((rootTarget.y-1) >= 0){
                        Cell neighbor = playerBoard.getCell(rootTarget.x, rootTarget.y-1);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }
                }
            }
            else {
                Cell cell = nextTarget.poll();
                Cell target = playerBoard.getCell(cell.x, cell.y);
                System.out.println(target.x);
                System.out.println(target.y);

                if ((target.ship != null)){

//                    if (target.y == rootTarget.y) {
//                        while (true){
//                            Cell c = nextTarget.getLast();
//                            if (c.x == rootTarget.x) nextTarget.removeLast();
//                            else break;
//                        }
//                    }
//                    else {
//                        while (true){
//                            Cell c = nextTarget.getFirst();
//                            if (c.y == rootTarget.y) nextTarget.removeFirst();
//                            else break;
//                        }
//                    }

                    if(target.x < rootTarget.x){
                        if (target.x>0) {
                            Cell neighbor = new Cell(target.x-1, target.y, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.x == rootTarget.x);
                    }
                    else if(target.x > rootTarget.x){
                        if (target.x<9) {
                            Cell neighbor = new Cell(target.x+1, target.y, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.x == rootTarget.x);
                    }
                    else if(target.y < rootTarget.y){
                        if (target.y>0) {
                            Cell neighbor = new Cell(target.x, target.y-1, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.y == rootTarget.y);
                    }
                    else if(target.y > rootTarget.y){
                        if (target.y<9) {
                            Cell neighbor = new Cell(target.x, target.y+1, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.y == rootTarget.y);
                    }
                }

                target.shoot(false);
            }
            enemyTurn = false;

            if(playerBoard.ships == 0) {
                System.out.println("You lose");
                System.exit(0);
            }

        }
        else if (Objects.equals(mode, "Hard")){

            // VU ANH TUAN - Hard Mode
            if(nextTarget.isEmpty()){
                int x,y;
                do{
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                } while((playerBoard.getCell(x, y).wasShot) || (playerBoard.getCell(x, y).ship == null));

                rootTarget = playerBoard.getCell(x,y);

                System.out.println(rootTarget.x);
                System.out.println(rootTarget.y);

                rootTarget.shoot(false);

                if(rootTarget.ship != null){
                    if((rootTarget.x+1) <= 9){
                        Cell neighbor = playerBoard.getCell(rootTarget.x+1, rootTarget.y);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    if ((rootTarget.y+1) <= 9){
                        Cell neighbor = playerBoard.getCell(rootTarget.x, rootTarget.y+1);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }

                    if ((rootTarget.x-1) >= 0){
                        Cell neighbor = playerBoard.getCell(rootTarget.x-1, rootTarget.y);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }


                    if ((rootTarget.y-1) >= 0){
                        Cell neighbor = playerBoard.getCell(rootTarget.x, rootTarget.y-1);
                        if((!neighbor.wasShot)) nextTarget.offer(neighbor);
                    }
                }
            }
            else {
                Cell cell = nextTarget.poll();
                Cell target = playerBoard.getCell(cell.x, cell.y);
                System.out.println(target.x);
                System.out.println(target.y);

                if ((target.ship != null)){

//                    if (target.y == rootTarget.y) {
//                        while (true){
//                            Cell c = nextTarget.getLast();
//                            if (c.x == rootTarget.x) nextTarget.removeLast();
//                            else break;
//                        }
//                    }
//                    else {
//                        while (true){
//                            Cell c = nextTarget.getFirst();
//                            if (c.y == rootTarget.y) nextTarget.removeFirst();
//                            else break;
//                        }
//                    }

                    if(target.x < rootTarget.x){
                        if (target.x>0) {
                            Cell neighbor = new Cell(target.x-1, target.y, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.x == rootTarget.x);
                    }
                    else if(target.x > rootTarget.x){
                        if (target.x<9) {
                            Cell neighbor = new Cell(target.x+1, target.y, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.x == rootTarget.x);
                    }
                    else if(target.y < rootTarget.y){
                        if (target.y>0) {
                            Cell neighbor = new Cell(target.x, target.y-1, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.y == rootTarget.y);
                    }
                    else if(target.y > rootTarget.y){
                        if (target.y<9) {
                            Cell neighbor = new Cell(target.x, target.y+1, playerBoard);
                            if(!neighbor.wasShot) nextTarget.offer(neighbor);
                        }
                        nextTarget.removeIf(c -> c.y == rootTarget.y);
                    }
                }

                target.shoot(false);
            }
            enemyTurn = false;

            if(playerBoard.ships == 0) {
                System.out.println("You lose");
                System.exit(0);
            }
        }
    }
}
