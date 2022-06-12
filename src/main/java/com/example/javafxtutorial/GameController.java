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
    private Queue<Cell> target = new LinkedList<>();


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

        else if(Objects.equals(mode, "Hard")){

            if(target.isEmpty()) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);

                while(playerBoard.getCell(x,y).wasShot) {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                }

                Cell cell = playerBoard.getCell(x,y);
                cell.shoot(false);
                if(cell.ship != null) {
                    if(x == 0) {
                        Cell neighbor = new Cell(x+1,y,playerBoard);
                        target.offer(neighbor);
                    }

                    else if(x == 9) {
                        Cell neighbor = new Cell(x-1,y,playerBoard);
                        target.offer(neighbor);
                    }

                    else {
                        Cell[] neighbor = {
                                new Cell(x-1,y,playerBoard),
                                new Cell(x+1,y,playerBoard),
                        };

                        for(Cell c : neighbor) target.offer(c);
                    }
                }
                enemyTurn = false;
            }

            else {
                Cell cell = target.poll();

                while(playerBoard.getCell(cell.x,cell.y).wasShot) {
                    cell = target.poll();
                    if(cell == null) break;
                }

                Cell[] neighbor = playerBoard.getNeighbors(cell.x,cell.y);
                for(Cell c : neighbor) target.offer(c);

                enemyTurn = cell.shoot(false);

            }
            if(playerBoard.ships == 0) {
                System.out.println("You lose");
                System.exit(0);
            }
        }
    }
}
