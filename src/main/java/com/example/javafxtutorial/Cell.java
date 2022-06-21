package com.example.javafxtutorial;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {
    public int x,y;
    public Ship ship = null;
    public boolean wasShot = false;
    private Board board;

    public Cell(int x,int y,Board board){
        super(40,40);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        setStrokeWidth(1);
    }

    public boolean shoot(boolean enemy) {
        wasShot = true;
        setFill(new ImagePattern(new Image(getClass().getResource("Image") + "close.png")));

        if(ship != null) {
            ship.hit();
            setFill(new ImagePattern(new Image(getClass().getResource("Image") + "hit.png")));
            if(!ship.isAlive()) {
                board.ships--;
            }
            return true;
        }

        return false;
    }

}
